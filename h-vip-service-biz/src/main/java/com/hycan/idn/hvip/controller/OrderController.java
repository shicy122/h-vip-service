package com.hycan.idn.hvip.controller;

import com.hycan.idn.hvip.aop.HttpLog;
import com.hycan.idn.hvip.constants.OrderConstants;
import com.hycan.idn.hvip.enums.AvpMqttTopicEnum;
import com.hycan.idn.hvip.facade.ICacheFacade;
import com.hycan.idn.hvip.facade.IMqttMessageFacade;
import com.hycan.idn.hvip.pojo.avp.mqtt.up.AiParkInUpDTO;
import com.hycan.idn.hvip.pojo.avp.mqtt.up.AiParkOutUpDTO;
import com.hycan.idn.hvip.pojo.avp.mqtt.up.CancelAiParkInUpDTO;
import com.hycan.idn.hvip.pojo.avp.mqtt.up.SetVehicleSchedulePermissionUpDTO;
import com.hycan.idn.hvip.pojo.mp.http.PageVO;
import com.hycan.idn.hvip.pojo.mp.http.req.ListOrderInfoReqVO;
import com.hycan.idn.hvip.pojo.mp.http.req.SaveOrderInfoReqVO;
import com.hycan.idn.hvip.pojo.mp.http.rsp.OrderDetailRspVO;
import com.hycan.idn.hvip.pojo.mp.http.rsp.OrderInfoRspVO;
import com.hycan.idn.hvip.pojo.mp.http.rsp.OrderItemRspVO;
import com.hycan.idn.hvip.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 工单相关接口
 *
 * @author shichongying
 * @datetime 2023-10-19 20:07
 */

@Validated
@Api(tags = "工单管理")
@RestController
@RequestMapping(value = "/vip-svc/v1/orders")
public class OrderController {

    @Resource
    private IOrderService orderService;

    @Resource
    private IMqttMessageFacade messageFacade;

    @Resource
    private ICacheFacade cacheFacade;

    /**
     * 创建工单（不涉及工单项目，工单项目由AVP推送的车位状态触发生成）
     *
     * @param req 创建工单信息请求对象
     * @return 工单信息响应对象
     */
    @HttpLog
    @PostMapping
    @ApiOperation(value = "创建工单")
    public ResponseEntity<OrderInfoRspVO> saveOrderInfo(@Valid SaveOrderInfoReqVO req) {
        // 1.保存工单
        OrderInfoRspVO orderInfo = orderService.saveOrderInfo(req);

        // 2.保存工单成功，则向AVP推送泊车服务列表和车辆调度权限MQTT消息
        if (Objects.nonNull(orderInfo)) {
            String avpStationCode = cacheFacade.getAvpStationCodeByOrderCodeFromCache(orderInfo.getOrderCode());
            messageFacade.sendMqttMessage(AvpMqttTopicEnum.AVP_UP_TOPIC_AI_PARK_IN,
                    AiParkInUpDTO.of(avpStationCode, orderInfo.getVin(), Arrays.asList(orderInfo.getItemList())));
            messageFacade.sendMqttMessage(AvpMqttTopicEnum.AVP_UP_TOPIC_SET_VEHICLE_SCHEDULE_PERMISSION,
                    SetVehicleSchedulePermissionUpDTO.of(avpStationCode, orderInfo.getVin()));
        }

        return ResponseEntity.ok(orderInfo);
    }

    /**
     * 分页查询工单列表
     *
     * @param req 分页查询工单列表请求对象
     * @return 分页查询工单列表响应对象（进行中的工单，服务结束时间为空）
     */
    @HttpLog
    @GetMapping
    @ApiOperation(value = "分页查询工单列表")
    public ResponseEntity<PageVO<OrderInfoRspVO>> listOrderInfo(@Valid ListOrderInfoReqVO req) {
        return ResponseEntity.ok(orderService.listOrderInfo(req));
    }

    /**
     * 查询工单详情
     *
     * @param orderCode 工单编号
     * @return 工单详情响应对象（含工单信息和工单项目的状态、费用等）
     */
    @HttpLog
    @GetMapping(value = "/{order_code}/detail")
    @ApiOperation(value = "查询工单详情")
    public ResponseEntity<OrderDetailRspVO> showOrderDetail(@PathVariable("order_code") String orderCode) {
        return ResponseEntity.ok(orderService.showOrderDetail(orderCode));
    }

    /**
     * 结束工单（一键取车场景使用，）
     *
     * @param orderCode 工单编号
     * @return 工单信息响应对象
     */
    @HttpLog
    @PutMapping(value = "/{order_code}/stop")
    @ApiOperation(value = "结束工单")
    public ResponseEntity<OrderInfoRspVO> stopOrder(@PathVariable("order_code") String orderCode) {
        // 1.结束工单
        // 如果当前没有进行中的工单，工单状态会返回取车中
        // 如果当前有进行中的工单，工单状态会返回等待取车
        OrderInfoRspVO orderInfo = orderService.stopOrder(orderCode);

        // 2.向AVP发送取车MQTT消息
        if (OrderConstants.ORDER_STATUS_PICKING_UP.equals(orderInfo.getOrderStatus())) {
            String avpStationCode = cacheFacade.getAvpStationCodeByOrderCodeFromCache(orderCode);
            messageFacade.sendMqttMessage(AvpMqttTopicEnum.AVP_UP_TOPIC_AI_PARK_OUT,
                    AiParkOutUpDTO.of(avpStationCode, orderInfo.getVin()));
        }

        return ResponseEntity.ok(orderInfo);
    }

    /**
     * 取消工单项目
     *
     * @param orderCode 工单编号
     * @param itemCode  工单项目
     * @return 工单项目响应对象
     */
    @HttpLog
    @PutMapping(value = "/{order_code}/items/{item_code}/cancel")
    @ApiOperation(value = "取消工单项目")
    public ResponseEntity<OrderItemRspVO> cancelOrderItem(
            @PathVariable("order_code") String orderCode,
            @Min(value = 0, message = "服务状态值不能小于0") @Max(value = 3, message = "服务状态值不能大于3") @PathVariable("item_code") Integer itemCode) {

        // 1.取消工单项目
        // 如果当前没有进行中的工单项目，工单项目状态会返回已取消
        // 如果当前有进行中的工单项目，工单项目状态会返回取消中
        OrderItemRspVO orderItem = orderService.cancelOrderItem(orderCode, itemCode);

        // 2.向AVP发送取消泊车服务MQTT消息
        if (OrderConstants.ORDER_ITEM_STATUS_CANCELED.equals(orderItem.getItemStatus())) {
            String avpStationCode = cacheFacade.getAvpStationCodeByOrderCodeFromCache(orderCode);
            String vin = cacheFacade.getVinByOrderCodeFromCache(orderCode);
            messageFacade.sendMqttMessage(AvpMqttTopicEnum.AVP_UP_TOPIC_CANCEL_AI_PARK_IN,
                    CancelAiParkInUpDTO.of(avpStationCode, vin, Collections.singletonList(itemCode)));
        }

        return ResponseEntity.ok(orderItem);
    }
}