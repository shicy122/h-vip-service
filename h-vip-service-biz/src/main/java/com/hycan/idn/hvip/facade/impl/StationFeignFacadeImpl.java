package com.hycan.idn.hvip.facade.impl;

import com.hycan.idn.hvip.constants.OrderConstants;
import com.hycan.idn.hvip.entity.OrderItemInfoEntity;
import com.hycan.idn.hvip.facade.ICacheFacade;
import com.hycan.idn.hvip.facade.IStationFeignFacade;
import com.hycan.idn.hvip.repository.OrderItemInfoRepository;
import com.hycan.idn.hvip.repository.VehicleParkRecordRepository;
import com.hycan.idn.station.enums.OrderRecordStatusEnum;
import com.hycan.idn.station.enums.OrderTypeEnum;
import com.hycan.idn.station.enums.SourceAppEnum;
import com.hycan.idn.station.feign.OrderFeignApi;
import com.hycan.idn.station.pojo.req.OrderFeeInfoReqVO;
import com.hycan.idn.station.pojo.req.OrderRecordReqVO;
import com.hycan.idn.station.pojo.req.OrderRecordStatusReqVO;
import com.hycan.idn.station.pojo.resp.OrderFeeInfoRspVO;
import com.hycan.idn.station.pojo.resp.OrderRecordRspVO;
import com.hycan.idn.tsp.common.core.constant.CommonConstants;
import com.hycan.idn.tsp.common.core.constant.SecurityConstants;
import com.hycan.idn.tsp.common.core.util.ExceptionUtil;
import com.hycan.idn.tsp.common.core.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 场站服务Feign调用包装实现类
 *
 * @author shichongying
 * @datetime 2023-10-20 17:58
 */
@Slf4j
@Component
public class StationFeignFacadeImpl implements IStationFeignFacade {

    @Resource
    private OrderFeignApi orderFeignApi;

    @Resource
    private ICacheFacade cacheFacade;

    @Resource
    private OrderItemInfoRepository orderItemInfoRepository;

    @Resource
    private VehicleParkRecordRepository vehicleParkRecordRepository;

    @Override
    public String startOrderItem(String stationCode, String vin, Integer itemCode) {
        OrderRecordReqVO orderRecordReqVO = buildOrderRecordReqVO(stationCode, vin, itemCode);
        if (Objects.isNull(orderRecordReqVO)){
            return null;
        }

        try {
            R<OrderRecordRspVO> result = orderFeignApi.serviceStart(orderRecordReqVO, SecurityConstants.FROM_IN);
            if (!CommonConstants.SUCCESS.equals(result.getCode()) || Objects.isNull(result.getData())) {
                return null;
            }
            return result.getData().getOrderCode();
        } catch (Exception e) {
            log.error("启动工单异常, 详情=[{}]", ExceptionUtil.getBriefStackTrace(e));
            return null;
        }
    }

    @Override
    public void stopOrderItem(String orderCode, Integer itemCode) {
        OrderItemInfoEntity entity = orderItemInfoRepository.findByOrderCodeAndItemCode(orderCode, itemCode);
        try {
            orderFeignApi.serviceStop(buildOrderRecordStatusReqVO(entity.getStationOrderCode()), SecurityConstants.FROM_IN);
        } catch (Exception e) {
            log.error("停止工单项目异常, 详情=[{}]", ExceptionUtil.getBriefStackTrace(e));
        }
    }


    @Override
    public void stopOrder(String orderCode) {
        List<OrderItemInfoEntity> entities = orderItemInfoRepository.findAllByOrderCodeAndItemStatus(
                orderCode, OrderConstants.ORDER_ITEM_STATUS_ONGOING);
        if (CollectionUtils.isEmpty(entities)) {
            return;
        }
        
        for (OrderItemInfoEntity entity : entities) {
            if (!OrderConstants.ORDER_ITEM_STATUS_ONGOING.equals(entity.getItemStatus())) {
                continue;
            }
            try {
                orderFeignApi.serviceStop(buildOrderRecordStatusReqVO(entity.getStationOrderCode()), SecurityConstants.FROM_IN);
            } catch (Exception e) {
                log.error("停止工单项目异常, 详情=[{}]", ExceptionUtil.getBriefStackTrace(e));
            }
        }
    }

    @Override
    public List<OrderFeeInfoRspVO> selectListFee(Set<String> stationOrderCodeList) {
        OrderFeeInfoReqVO orderFeeInfoReqVO = new OrderFeeInfoReqVO(new ArrayList<>(stationOrderCodeList));
        try {
            R<List<OrderFeeInfoRspVO>> result = orderFeignApi.selectListFee(orderFeeInfoReqVO, SecurityConstants.FROM_IN);
            if (!CommonConstants.SUCCESS.equals(result.getCode()) || Objects.isNull(result.getData())) {
                return new ArrayList<>();
            }
            return result.getData();
        } catch (Exception e) {
            log.error("查询费用列表异常, 详情=[{}]", ExceptionUtil.getBriefStackTrace(e));
        }
        return new ArrayList<>();
    }

    private OrderRecordStatusReqVO buildOrderRecordStatusReqVO(String stationOrderCode) {
        return OrderRecordStatusReqVO.builder()
                .orderCode(stationOrderCode)
                .status(OrderRecordStatusEnum.ORDER_STATUS_CANCELED)
                .build();
    }

    private OrderRecordReqVO buildOrderRecordReqVO(String stationCode, String vin, Integer itemCode) {
        OrderTypeEnum enums = getOrderTypeEnum(itemCode);
        if (enums == null) {
            return null;
        }

        return OrderRecordReqVO.builder()
                .orderType(enums)
                .sourceApp(SourceAppEnum.SOURCE_H_VIP)
                .stationCode(stationCode)
                .vin(vin)
                .plateNo(cacheFacade.getPlateNoByVinFromCache(vin))
                .parkNo(vehicleParkRecordRepository.selectVehicleCurrentPark(vin).getPakNo())
                .build();
    }

    private OrderTypeEnum getOrderTypeEnum(Integer itemCode) {
        OrderTypeEnum enums;
        if (OrderConstants.ORDER_ITEM_CODE_CHARGING.equals(itemCode)) {
            enums = OrderTypeEnum.ORDER_TYPE_CHARGING;
        } else if (OrderConstants.ORDER_ITEM_CODE_CHECK.equals(itemCode)) {
            enums = OrderTypeEnum.ORDER_TYPE_CHECK;
        } else if (OrderConstants.ORDER_ITEM_CODE_WASH.equals(itemCode)) {
            enums = OrderTypeEnum.ORDER_TYPE_WASH;
        } else {
            return null;
        }
        return enums;
    }
}
