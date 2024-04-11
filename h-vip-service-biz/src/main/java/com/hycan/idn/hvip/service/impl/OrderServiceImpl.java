package com.hycan.idn.hvip.service.impl;

import com.hycan.idn.hvip.constants.OrderConstants;
import com.hycan.idn.hvip.entity.OrderBasicInfoEntity;
import com.hycan.idn.hvip.entity.OrderItemInfoEntity;
import com.hycan.idn.hvip.entity.StationBasicInfoEntity;
import com.hycan.idn.hvip.execption.CommonBizException;
import com.hycan.idn.hvip.execption.ExceptionEnums;
import com.hycan.idn.hvip.facade.IStationFeignFacade;
import com.hycan.idn.hvip.pojo.mp.http.PageVO;
import com.hycan.idn.hvip.pojo.mp.http.req.ListOrderInfoReqVO;
import com.hycan.idn.hvip.pojo.mp.http.req.SaveOrderInfoReqVO;
import com.hycan.idn.hvip.pojo.mp.http.rsp.OrderDetailRspVO;
import com.hycan.idn.hvip.pojo.mp.http.rsp.OrderInfoRspVO;
import com.hycan.idn.hvip.pojo.mp.http.rsp.OrderItemRspVO;
import com.hycan.idn.hvip.repository.OrderBasicInfoRepository;
import com.hycan.idn.hvip.repository.OrderItemInfoRepository;
import com.hycan.idn.hvip.repository.StationBasicInfoRepository;
import com.hycan.idn.hvip.repository.VehicleBasicInfoRepository;
import com.hycan.idn.hvip.service.IOrderService;
import com.hycan.idn.hvip.util.LoginUserUtils;
import com.hycan.idn.station.enums.FeeTypeEnum;
import com.hycan.idn.station.pojo.resp.OrderFeeInfoRspVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 工单相关业务Service实现
 *
 * @author shichongying
 * @datetime 2023-10-26 16:42
 */
@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {

    @Resource
    private OrderBasicInfoRepository orderBasicInfoRepository;

    @Resource
    private OrderItemInfoRepository orderItemInfoRepository;

    @Resource
    private StationBasicInfoRepository stationBasicInfoRepository;

    @Resource
    private VehicleBasicInfoRepository vehicleBasicInfoRepository;

    @Resource
    private IStationFeignFacade stationFeignFacade;

    @Override
    public OrderInfoRspVO saveOrderInfo(SaveOrderInfoReqVO req) {
        StationBasicInfoEntity stationBasicInfoEntity = stationBasicInfoRepository.findByStationCode(req.getStationCode());
        if (Objects.isNull(stationBasicInfoEntity)) {
            log.warn("场站不存在! 场站编号=[{}]", req.getStationCode());
            throw new CommonBizException(ExceptionEnums.STATION_NOT_EXIST);
        }

        if (Objects.isNull(vehicleBasicInfoRepository.findByVin(req.getVin()))) {
            log.warn("车辆不存在! VIN码=[{}]", req.getVin());
            throw new CommonBizException(ExceptionEnums.VIN_NOT_EXIST);
        }

        // 判断车辆当前是否有未完成的工单服务
        if (Objects.isNull(orderBasicInfoRepository.selectUnfinishedOrder(req.getStationCode(), req.getVin()))) {
            log.warn("车辆存在未完成工单! 入参=[{}]", req);
            throw new CommonBizException(ExceptionEnums.ORDER_UNFINISHED);
        }

        OrderBasicInfoEntity orderBasicInfoEntity = OrderBasicInfoEntity.of(req);
        orderBasicInfoRepository.save(orderBasicInfoEntity);

        return OrderInfoRspVO.of(orderBasicInfoEntity);
    }

    @Override
    public PageVO<OrderInfoRspVO> listOrderInfo(ListOrderInfoReqVO req) {
        Pageable pageable = PageRequest.of(req.getCurrent(), req.getSize());
        Page<OrderInfoRspVO> page = orderBasicInfoRepository.findPage(pageable, req, LoginUserUtils.getLoginUserId());

        List<String> hvipOrderCodeList = page.toList().stream().map(OrderInfoRspVO::getOrderCode).collect(Collectors.toList());
        List<OrderItemInfoEntity> entityList = orderItemInfoRepository.findByOrderCodeInAndStationOrderCodeIsNotNull(hvipOrderCodeList);
        // orderCodeMap工单号关系, key:场站服务工单号 value:H-VIP服务工单号
        Map<String, String> orderCodeMap = entityList.stream().collect(
                Collectors.toMap(OrderItemInfoEntity::getStationOrderCode, OrderItemInfoEntity::getOrderCode));

        // 批量查询每一个订单的总费用
        Map<String, BigDecimal> orderFeeMap = new HashMap<>();
        List<OrderFeeInfoRspVO> orderFeeRspList = stationFeignFacade.selectListFee(orderCodeMap.keySet());
        for (OrderFeeInfoRspVO orderFeeInfo : orderFeeRspList) {
            String orderCode = orderCodeMap.get(orderFeeInfo.getOrderCode());
            BigDecimal orderFee = Objects.isNull(orderFeeMap.get(orderCode)) ? BigDecimal.ZERO : orderFeeMap.get(orderCode);
            orderFee = orderFee.add(orderFeeInfo.getFeeMoney());
            orderFeeMap.put(orderCode, orderFee);
        }

        return PageVO.of(page);
    }

    @Override
    public OrderDetailRspVO showOrderDetail(String orderCode) {
        OrderDetailRspVO orderDetailRspVO = new OrderDetailRspVO();
        // 1.查询工单基础信息
        OrderBasicInfoEntity entity = orderBasicInfoRepository.findByOrderCode(orderCode);
        OrderInfoRspVO orderInfoRspVO = OrderInfoRspVO.of(entity);

        // 2.查询工单项明细(状态、开始时间、结束时间)
        Set<String> stationOrderCodeSet = new HashSet<>();
        List<OrderItemInfoEntity> orderItemList = orderItemInfoRepository.findAllByOrderCode(orderCode);
        for (OrderItemInfoEntity orderItemInfo : orderItemList) {
            // 2.1.将场站服务返回的工单号，添加到集合中，下一步查询单项费用明细时需要
            if (StringUtils.isNotBlank(orderItemInfo.getStationOrderCode())) {
                stationOrderCodeSet.add(orderItemInfo.getStationOrderCode());
            }

            // 2.2.TODO 调ECP接口查询充电电量和充电设备名称，补充以下null值
            int itemCode = orderItemInfo.getItemCode();
            if (OrderConstants.ORDER_ITEM_CODE_CHARGING.equals(itemCode)) {
                orderDetailRspVO.setChargeOrderItem(null, null);
            }

            // 2.3.将工单明细赋值到OrderDetailRsp对象
            orderDetailRspVO.setOrderItem(itemCode, orderItemInfo.getItemStatus(),
                    orderItemInfo.getStartTime(), orderItemInfo.getEndTime());
        }

        // 3.根据场站服务的工单号集合，查询单项费用明细
        List<OrderFeeInfoRspVO> orderFeeRspList = stationFeignFacade.selectListFee(stationOrderCodeSet);
        for (OrderFeeInfoRspVO orderFeeInfo : orderFeeRspList) {
            // 3.1.将单项费用赋值到OrderDetailRsp对象
            FeeTypeEnum feeType = orderFeeInfo.getFeeType();
            BigDecimal itemFee = orderFeeInfo.getFeeMoney();
            orderDetailRspVO.setFeeDetail(feeType, itemFee);

            // 3.2.将单项费用添加到总费用中
            BigDecimal totalFee = orderInfoRspVO.getTotalFee().add(itemFee);
            orderInfoRspVO.setTotalFee(totalFee);
        }

        // 4.将工单基础信息赋值到OrderDetailRsp对象
        orderDetailRspVO.setOrderInfo(orderInfoRspVO);

        return orderDetailRspVO;
    }

    @Override
    public OrderItemRspVO cancelOrderItem(String orderCode, Integer itemCode) {
        OrderItemInfoEntity entity = orderItemInfoRepository.findByOrderCodeAndItemCode(orderCode, itemCode);
        if (Objects.isNull(entity)) {
            log.warn("操作失败, 服务项目不存在! 订单号=[{}], 服务项目=[{}]", orderCode, itemCode);
            throw new CommonBizException(ExceptionEnums.ORDER_ITEM_NOT_EXIST);
        }

        Integer itemStatus = entity.getItemStatus();
        if (OrderConstants.ORDER_ITEM_STATUS_CANCELED.equals(itemStatus)) {
            log.warn("操作失败, 服务项目已取消! 订单号=[{}], 服务项目=[{}]", orderCode, itemCode);
            throw new CommonBizException(ExceptionEnums.ORDER_ITEM_IS_CANCELED);
        } else if (OrderConstants.ORDER_ITEM_STATUS_FINISH.equals(itemStatus)) {
            log.warn("操作失败, 服务项目已完成! 订单号=[{}], 服务项目=[{}]", orderCode, itemCode);
            throw new CommonBizException(ExceptionEnums.ORDER_ITEM_IS_FINISH);
        } else if (OrderConstants.ORDER_ITEM_STATUS_CANCELLING.equals(itemStatus)) {
            log.warn("操作失败, 服务项目取消中! 订单号=[{}], 服务项目=[{}]", orderCode, itemCode);
            throw new CommonBizException(ExceptionEnums.ORDER_ITEM_IS_CANCELLING);
        } else if (OrderConstants.ORDER_ITEM_STATUS_NOT_START.equals(itemStatus)) {
            entity.setItemStatus(OrderConstants.ORDER_ITEM_STATUS_CANCELED);
            entity.setEndTime(LocalDateTime.now());
        } else if (OrderConstants.ORDER_ITEM_STATUS_ONGOING.equals(itemStatus)) {
            entity.setItemStatus(OrderConstants.ORDER_ITEM_STATUS_CANCELLING);

            // 调用场站服务接口，取消工单
            stationFeignFacade.stopOrderItem(entity.getOrderCode(), entity.getItemCode());
        }

        orderItemInfoRepository.save(entity);
        return OrderItemRspVO.of(entity);
    }

    @Override
    public OrderInfoRspVO stopOrder(String orderCode) {
        OrderBasicInfoEntity entity = orderBasicInfoRepository.findByOrderCode(orderCode);
        if (Objects.isNull(entity)) {
            log.warn("服务工单不存在! 订单号=[{}]", orderCode);
            throw new CommonBizException(ExceptionEnums.ORDER_NOT_EXIST);
        }

        Integer orderStatus = entity.getOrderStatus();
        // TODO 改成抛业务异常
        Assert.isTrue(!OrderConstants.ORDER_STATUS_FINISH.equals(orderStatus), "服务工单为已结束, 无法操作!");
        Assert.isTrue(!OrderConstants.ORDER_STATUS_PICKING_UP.equals(orderStatus), "正在取车中, 请勿重复操作!");
        Assert.isTrue(!OrderConstants.ORDER_STATUS_PICKED_UP.equals(orderStatus), "车辆已驶入取车区, 请前往取车!");
        Assert.isTrue(!OrderConstants.ORDER_STATUS_WAIT_PICKUP.equals(orderStatus), "等待取车中, 请勿重复操作!");

        if (OrderConstants.ORDER_STATUS_NOT_START.equals(orderStatus)) {
            entity.setOrderStatus(OrderConstants.ORDER_STATUS_PICKING_UP);
            entity.setEndTime(LocalDateTime.now());
        } else if (OrderConstants.ORDER_STATUS_ONGOING.equals(orderStatus)) {
            entity.setOrderStatus(OrderConstants.ORDER_STATUS_WAIT_PICKUP);

            // 调用场站服务接口，取消工单
            stationFeignFacade.stopOrder(entity.getOrderCode());
        }

        orderBasicInfoRepository.save(entity);
        return OrderInfoRspVO.of(entity);
    }

    @Override
    public void updateOrderItem(OrderItemInfoEntity entity, Integer avpStatus, Integer itemStatus, String stationOrderCode) {
        if (OrderConstants.DISPATCH_STATUS_FINISH.equals(avpStatus)) {
            entity.setAvpStatus(OrderConstants.DISPATCH_STATUS_FINISH);
            entity.setEndTime(LocalDateTime.now());
        } else if (OrderConstants.DISPATCH_STATUS_PARKING.equals(avpStatus)) {
            entity.setAvpStatus(OrderConstants.DISPATCH_STATUS_PARKING);
            entity.setItemStatus(OrderConstants.ORDER_ITEM_STATUS_ONGOING);
            entity.setStartTime(LocalDateTime.now());
        } else if (OrderConstants.DISPATCH_STATUS_WAITING.equals(avpStatus)) {
            entity.setAvpStatus(OrderConstants.DISPATCH_STATUS_WAITING);
        }

        entity.setItemStatus(itemStatus);
        entity.setStationOrderCode(stationOrderCode);
        orderItemInfoRepository.save(entity);
    }
}