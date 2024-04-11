package com.hycan.idn.hvip.mqtt.listener;

import com.hycan.idn.hvip.aop.MqttLog;
import com.hycan.idn.hvip.constants.CommonConstants;
import com.hycan.idn.hvip.constants.OrderConstants;
import com.hycan.idn.hvip.entity.OrderBasicInfoEntity;
import com.hycan.idn.hvip.entity.OrderItemInfoEntity;
import com.hycan.idn.hvip.entity.StationParkInfoEntity;
import com.hycan.idn.hvip.enums.AvpMqttTopicEnum;
import com.hycan.idn.hvip.enums.MpMqttTopicEnum;
import com.hycan.idn.hvip.enums.MqttMsgTypeEnum;
import com.hycan.idn.hvip.facade.IAvpRemoteFacade;
import com.hycan.idn.hvip.facade.ICacheFacade;
import com.hycan.idn.hvip.facade.IMqttMessageFacade;
import com.hycan.idn.hvip.facade.IStationFeignFacade;
import com.hycan.idn.hvip.mqtt.event.AvpMessageEvent;
import com.hycan.idn.hvip.pojo.avp.mqtt.down.AiParkOutDownDTO;
import com.hycan.idn.hvip.pojo.avp.mqtt.down.CancelAiParkInDownDTO;
import com.hycan.idn.hvip.pojo.avp.mqtt.down.CancelAiParkInResultDTO;
import com.hycan.idn.hvip.pojo.avp.mqtt.down.NewRouteDownDTO;
import com.hycan.idn.hvip.pojo.avp.mqtt.down.ParkingLotKeyChangedDownDTO;
import com.hycan.idn.hvip.pojo.avp.mqtt.down.ParkingLotStatusDownDTO;
import com.hycan.idn.hvip.pojo.avp.mqtt.down.ParkingSpaceDetailDTO;
import com.hycan.idn.hvip.pojo.avp.mqtt.down.ParkingSpaceStatusDownDTO;
import com.hycan.idn.hvip.pojo.avp.mqtt.down.RouteInfoDownDTO;
import com.hycan.idn.hvip.pojo.avp.mqtt.down.VehicleFeedbackDownDTO;
import com.hycan.idn.hvip.pojo.avp.mqtt.down.VehicleSchedulePermissionDownDTO;
import com.hycan.idn.hvip.pojo.avp.mqtt.down.VehicleScheduleStatusDetailDTO;
import com.hycan.idn.hvip.pojo.avp.mqtt.down.VehicleScheduleStatusDownDTO;
import com.hycan.idn.hvip.pojo.avp.mqtt.up.VehicleSchedulePermissionUpDTO;
import com.hycan.idn.hvip.pojo.mp.mqtt.down.CancelResultDownDTO;
import com.hycan.idn.hvip.pojo.mp.mqtt.down.DispatchLineDownDTO;
import com.hycan.idn.hvip.pojo.mp.mqtt.down.DispatchStateDownDTO;
import com.hycan.idn.hvip.pojo.mp.mqtt.down.DrivingLineDownDTO;
import com.hycan.idn.hvip.pojo.mp.mqtt.down.DrivingStateDownDTO;
import com.hycan.idn.hvip.pojo.mp.mqtt.down.ParkingOutResultDownDTO;
import com.hycan.idn.hvip.pojo.mp.mqtt.down.ParkingStaceStatusDownDTO;
import com.hycan.idn.hvip.repository.OrderBasicInfoRepository;
import com.hycan.idn.hvip.repository.OrderItemInfoRepository;
import com.hycan.idn.hvip.repository.StationBasicInfoRepository;
import com.hycan.idn.hvip.repository.StationParkInfoRepository;
import com.hycan.idn.hvip.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * AVP MQTT 下行消息监听器
 *
 * @author shichongying
 * @datetime 2023-10-21 11:42
 */
@Slf4j
@Component
public class AvpDownMessageListener {

    @Resource
    private StationParkInfoRepository stationParkInfoRepository;

    @Resource
    private StationBasicInfoRepository stationBasicInfoRepository;

    @Resource
    private OrderBasicInfoRepository orderBasicInfoRepository;

    @Resource
    private OrderItemInfoRepository orderItemInfoRepository;

    @Resource
    private IStationFeignFacade stationFeignFacade;

    @Resource
    private IOrderService orderService;

    @Resource
    private ICacheFacade cacheFacade;

    @Resource
    private IMqttMessageFacade mqttMessageFacade;

    @Resource
    private IAvpRemoteFacade avpRemoteFacade;

    @Async
    @MqttLog(MqttMsgTypeEnum.MSG_TYPE_AVP)
    @EventListener(condition = "#event.topic.startsWith(T(com.hycan.idn.hvip.enums.AvpMqttTopicEnum).AVP_DOWN_TOPIC_PARKING_LOT_STATUS.prefix)")
    public void receiveParkingLotStatus(AvpMessageEvent event) {
        ParkingLotStatusDownDTO payload = (ParkingLotStatusDownDTO) event.getMessage().getPayload();
        String stationCode = cacheFacade.getStationCodeByAvpStationCodeFromCache(payload.getPid());
        if (StringUtils.isEmpty(stationCode)) {
            log.warn("非H-VIP场站, 不更新场站状态, AVP场站编号=[{}]", payload.getPid());
            return;
        }
        int result = stationBasicInfoRepository.updateStationStatus(payload.getPid(), payload.getOnlineStatus());
        log.info("更新场站状态{}, H-VIP场站编号=[{}], 场站状态=[{}](0:上线 1:下线)",
                CommonConstants.SUCCESS_FLAG == result ? "成功" : "失败", stationCode, payload.getOnlineStatus());
    }

    @Async
    @MqttLog(MqttMsgTypeEnum.MSG_TYPE_AVP)
    @EventListener(condition = "#event.topic.startsWith(T(com.hycan.idn.hvip.enums.AvpMqttTopicEnum).AVP_DOWN_TOPIC_PARKING_LOT_KEY_CHANGED.prefix)")
    public void receiveParkingLotKeyChanged(AvpMessageEvent event) {
        ParkingLotKeyChangedDownDTO payload = (ParkingLotKeyChangedDownDTO) event.getMessage().getPayload();
        String avpStationCode = payload.getPid();

        String stationCode = cacheFacade.getStationCodeByAvpStationCodeFromCache(avpStationCode);
        if (StringUtils.isEmpty(stationCode)) {
            log.warn("非H-VIP场站, 不更新场站秘钥, AVP场站编号=[{}]", avpStationCode);
            return;
        }

        String stationSecret = avpRemoteFacade.getOrUpdateStationSecret(avpStationCode);
        log.info("更新场站秘钥{}, H-VIP场站编号=[{}], 场站秘钥=[{}]",
                StringUtils.isNotEmpty(stationSecret) ? "成功" : "失败", stationCode,
                StringUtils.center(stationSecret, stationSecret.length(), "*"));
    }

    // TODO 推送取车通知、泊入通知、VIN不在场站则更新订单状态为完成
    @Async
    @MqttLog(MqttMsgTypeEnum.MSG_TYPE_AVP)
    @EventListener(condition = "#event.topic.startsWith(T(com.hycan.idn.hvip.enums.AvpMqttTopicEnum).AVP_DOWN_TOPIC_PARKING_SPACE_STATUS.prefix)")
    public void receiveParkingSpaceStatus(AvpMessageEvent event) {
        ParkingSpaceStatusDownDTO payload = (ParkingSpaceStatusDownDTO) event.getMessage().getPayload();

        String stationCode = cacheFacade.getStationCodeByAvpStationCodeFromCache(payload.getPid());

        List<StationParkInfoEntity> entityList = stationParkInfoRepository.findAllByStationCode(stationCode);
        // key:AVP车位号 value:场站场站车位信息Entity
        Map<String, StationParkInfoEntity> stationParkInfoMap = entityList.stream().collect(
                Collectors.toMap(StationParkInfoEntity::getAvpParkNo, entity -> entity));

        for (ParkingSpaceDetailDTO parkingSpaceDetailDTO : payload.getParkingSpaces()) {
            String avpParkNo = parkingSpaceDetailDTO.getSpaceId();
            if (!stationParkInfoMap.containsKey(avpParkNo)) {
                log.warn("场站不存在该车位号, 不更新车位状态! H-VIP场站编号=[{}], AVP车位号=[{}]", stationCode, avpParkNo);
                continue;
            }

            String vin = parkingSpaceDetailDTO.getVin();
            StationParkInfoEntity stationParkInfoEntity = stationParkInfoMap.get(avpParkNo);
            if (Objects.equals(stationParkInfoEntity.getParkStatus(), parkingSpaceDetailDTO.getStatus())
                    && Objects.equals(stationParkInfoEntity.getVin(), vin)
                    && Objects.equals(stationParkInfoEntity.getParkType(), parkingSpaceDetailDTO.getSpaceType())) {
                log.debug("车位未发生变化, 不更新车位状态! H-VIP场站编号=[{}], H-VIP车位号=[{}]", stationCode, stationParkInfoEntity.getParkNo());
                continue;
            }

            stationParkInfoEntity.setVin(vin);
            stationParkInfoEntity.setParkStatus(parkingSpaceDetailDTO.getStatus());
            stationParkInfoRepository.save(stationParkInfoEntity);

            String plateNo = cacheFacade.getPlateNoByVinFromCache(vin);

            log.info("车位状态发生变化, 向H-VIP小程序推送消息. H-VIP场站编号=[{}], H-VIP车位号=[{}]",
                    stationCode, stationParkInfoEntity.getParkNo());
            mqttMessageFacade.sendMqttMessage(MpMqttTopicEnum.MP_DOWN_TOPIC_PARKING_SPACE_STATUS,
                    ParkingStaceStatusDownDTO.of(stationParkInfoEntity, plateNo));
        }
    }

    @Async
    @MqttLog(MqttMsgTypeEnum.MSG_TYPE_AVP)
    @EventListener(condition = "#event.topic.startsWith(T(com.hycan.idn.hvip.enums.AvpMqttTopicEnum).AVP_DOWN_TOPIC_VEHICLE_SCHEDULE_PERMISSION.prefix)")
    public void receiveVehicleSchedulePermission(AvpMessageEvent event) {
        VehicleSchedulePermissionDownDTO payload = (VehicleSchedulePermissionDownDTO) event.getMessage().getPayload();

        String stationCode = cacheFacade.getStationCodeByAvpStationCodeFromCache(payload.getPid());
        String currentStationCode = cacheFacade.getCurrentStationCodeByVinFromCache(payload.getVin());

        if (StringUtils.isEmpty(stationCode)) {
            log.warn("H-VIP场站编号为空, 调度权限应答不允许调度! AVP场站编号=[{}]", payload.getPid());
            return;
        }

        if (!stationCode.equals(currentStationCode)) {
            log.warn("车辆当前所在场站与调度场站不匹配, 调度权限应答不允许调度! 车辆所在H-VIP场站编号=[{}], 调度H-VIP场站编号=[{}]",
                    currentStationCode, stationCode);
            return;
        }

        List<StationParkInfoEntity> stationParkInfoEntityList =
                stationParkInfoRepository.selectIdleParkList(stationCode, payload.getToType());
        if (CollectionUtils.isEmpty(stationParkInfoEntityList)) {
            log.warn("目标车位类型当前无空闲车位, 调度权限应答不允许调度! H-VIP场站编号=[{}], 车位类型=[{}]",
                    stationCode, payload.getToType());
            return;
        }

        log.info("调度权限应答允许调度, H-VIP场站编号=[{}], VIN码=[{}], 起始车位类型=[{}], 目标车位类型=[{}]",
                stationCode, payload.getVin(), payload.getFromType(), payload.getToType());
        mqttMessageFacade.sendMqttMessage(AvpMqttTopicEnum.AVP_UP_TOPIC_VEHICLE_SCHEDULE_PERMISSION,
                VehicleSchedulePermissionUpDTO.of(payload, true));
    }

    @Async
    @MqttLog(MqttMsgTypeEnum.MSG_TYPE_AVP)
    @EventListener(condition = "#event.topic.startsWith(T(com.hycan.idn.hvip.enums.AvpMqttTopicEnum).AVP_DOWN_TOPIC_VEHICLE_FEEDBACK.prefix)")
    public void receiveVehicleFeedback(AvpMessageEvent event) {
        VehicleFeedbackDownDTO payload = (VehicleFeedbackDownDTO) event.getMessage().getPayload();
        String plateNo = cacheFacade.getPlateNoByVinFromCache(payload.getVin());
        mqttMessageFacade.sendMqttMessage(MpMqttTopicEnum.MP_DOWN_TOPIC_DRIVING_STATE, DrivingStateDownDTO.of(payload, plateNo));
    }

    @Async
    @MqttLog(MqttMsgTypeEnum.MSG_TYPE_AVP)
    @EventListener(condition = "#event.topic.startsWith(T(com.hycan.idn.hvip.enums.AvpMqttTopicEnum).AVP_DOWN_TOPIC_NEW_ROUTE.prefix)")
    public void receiveNewRoute(AvpMessageEvent event) {
        NewRouteDownDTO payload = (NewRouteDownDTO) event.getMessage().getPayload();
        String stationCode = cacheFacade.getCurrentStationCodeByVinFromCache(payload.getVin());
        String plateNo = cacheFacade.getPlateNoByVinFromCache(payload.getVin());
        mqttMessageFacade.sendMqttMessage(MpMqttTopicEnum.MP_DOWN_TOPIC_DISPATCH_LINE,
                DispatchLineDownDTO.of(stationCode, plateNo, payload.getPath()));
    }

    @Async
    @MqttLog(MqttMsgTypeEnum.MSG_TYPE_AVP)
    @EventListener(condition = "#event.topic.startsWith(T(com.hycan.idn.hvip.enums.AvpMqttTopicEnum).AVP_DOWN_TOPIC_ROUTE_INFO.prefix)")
    public void receiveRouteInfo(AvpMessageEvent event) {
        RouteInfoDownDTO payload = (RouteInfoDownDTO) event.getMessage().getPayload();
        String stationCode = cacheFacade.getCurrentStationCodeByVinFromCache(payload.getVin());
        String plateNo = cacheFacade.getPlateNoByVinFromCache(payload.getVin());
        mqttMessageFacade.sendMqttMessage(MpMqttTopicEnum.MP_DOWN_TOPIC_DRIVING_LINE, DrivingLineDownDTO.of(stationCode, plateNo, payload));
    }

    @Async
    @MqttLog(MqttMsgTypeEnum.MSG_TYPE_AVP)
    @EventListener(condition = "#event.topic.startsWith(T(com.hycan.idn.hvip.enums.AvpMqttTopicEnum).AVP_DOWN_TOPIC_VEHICLE_SCHEDULE_STATUS.prefix)")
    public void receiveVehicleScheduleStatus(AvpMessageEvent event) {
        // 1.校验数据合法性
        VehicleScheduleStatusDownDTO payload = (VehicleScheduleStatusDownDTO) event.getMessage().getPayload();
        if (CollectionUtils.isEmpty(payload.getStatus())) {
            log.warn("车辆调度状态集合为空! AVP场站编号=[{}], VIN码=[{}]", payload.getPid(), payload.getVin());
            return;
        }
        Map<Integer, VehicleScheduleStatusDetailDTO> vehicleScheduleStatusDetailMap = payload.getStatus().stream()
                .collect(Collectors.toMap(VehicleScheduleStatusDetailDTO::getDstServiceType, dto -> dto));

        // 2.校验车辆是否存在未完成工单
        String stationCode = cacheFacade.getStationCodeByAvpStationCodeFromCache(payload.getPid());
        OrderBasicInfoEntity orderBasicInfoEntity = orderBasicInfoRepository.selectUnfinishedOrder(stationCode, payload.getVin());
        if (Objects.isNull(orderBasicInfoEntity)) {
            log.warn("车辆不存在未完成工单! AVP场站编号=[{}], VIN码=[{}]", payload.getPid(), payload.getVin());
            return;
        }

        // 3.与数据库中的服务项比较，筛选出调度状态有变化的数据
        List<OrderItemInfoEntity> orderItemInfoEntityList = orderItemInfoRepository.findAllByOrderCodeAndItemCodeIn(
                orderBasicInfoEntity.getOrderCode(), Arrays.asList(orderBasicInfoEntity.getItemList()));
        for (OrderItemInfoEntity orderItemInfoEntity : orderItemInfoEntityList) {
            String orderCode = orderItemInfoEntity.getOrderCode();
            Integer itemCode = orderItemInfoEntity.getItemCode();
            VehicleScheduleStatusDetailDTO vehicleScheduleStatusDetail = vehicleScheduleStatusDetailMap.get(itemCode);
            if (Objects.isNull(vehicleScheduleStatusDetail)) {
                log.warn("中心云为通知服务型调度状态, 工单号=[{}], 服务项=[{}]", orderCode, itemCode);
                continue;
            }

            // 3.1.排除调度状态没有变化的数据
            int avpStatus = vehicleScheduleStatusDetail.getCurrentStatus();
            if (Objects.equals(orderItemInfoEntity.getAvpStatus(), avpStatus)) {
                continue;
            }

            // 3.2.向场站小程序发送工单(非停车服务、服务状态进行中、调度状态已完成，向场站小程序发送创建工单请求)
            String stationOrderCode = null;
            if (!OrderConstants.ORDER_ITEM_CODE_PARKING.equals(itemCode)
                    && OrderConstants.ORDER_ITEM_STATUS_ONGOING.equals(orderItemInfoEntity.getItemStatus())
                    && OrderConstants.DISPATCH_STATUS_FINISH.equals(avpStatus)) {
                stationOrderCode = stationFeignFacade.startOrderItem(stationCode, payload.getVin(), itemCode);
            }

            // 3.3.向H-VIP小程序发送调度状态变化通知
            String plateNo = cacheFacade.getPlateNoByVinFromCache(payload.getVin());
            DispatchStateDownDTO dispatchStateDownDTO = DispatchStateDownDTO.of(stationCode, plateNo, vehicleScheduleStatusDetail);
            mqttMessageFacade.sendMqttMessage(MpMqttTopicEnum.MP_DOWN_TOPIC_DISPATCH_STATE, dispatchStateDownDTO);

            // 3.4.更新数据库调度状态和服务状态
            orderService.updateOrderItem(orderItemInfoEntity, avpStatus, OrderConstants.ORDER_ITEM_STATUS_CANCELLING, stationOrderCode);
        }
    }

    @Async
    @MqttLog(MqttMsgTypeEnum.MSG_TYPE_AVP)
    @EventListener(condition = "#event.topic.startsWith(T(com.hycan.idn.hvip.enums.AvpMqttTopicEnum).AVP_DOWN_TOPIC_AI_PARK_OUT.prefix)")
    public void receiveAiParkOut(AvpMessageEvent event) {
        AiParkOutDownDTO payload = (AiParkOutDownDTO) event.getMessage().getPayload();
        String stationCode = cacheFacade.getStationCodeByAvpStationCodeFromCache(payload.getPid());

        // 为true表示泊入取车位成功，不向小程序推送取车结果，车位状态变化时再推送取车成功结果
        // 为false表示泊入取车位失败，需要向小程序推送失败结果和原因
        if (!payload.getResult()) {
            String plateNo = cacheFacade.getPlateNoByVinFromCache(payload.getVin());
            ParkingOutResultDownDTO parkingOutResultDownDTO = ParkingOutResultDownDTO.of(stationCode, plateNo, payload);
            mqttMessageFacade.sendMqttMessage(MpMqttTopicEnum.MP_DOWN_TOPIC_PARKING_OUT_RESULT, parkingOutResultDownDTO);
        }
    }

    @Async
    @MqttLog(MqttMsgTypeEnum.MSG_TYPE_AVP)
    @EventListener(condition = "#event.topic.startsWith(T(com.hycan.idn.hvip.enums.AvpMqttTopicEnum).AVP_DOWN_TOPIC_CANCEL_AI_PARK_IN.prefix)")
    public void receiveCancelAiParkIn(AvpMessageEvent event) {
        // 1.校验数据合法性
        CancelAiParkInDownDTO payload = (CancelAiParkInDownDTO) event.getMessage().getPayload();
        if (Objects.isNull(payload) || CollectionUtils.isEmpty(payload.getResults())) {
            log.warn("取消泊车服务应答结果为空! AVP场站编号=[{}], VIN码=[{}]", payload.getPid(), payload.getVin());
            return;
        }
        Map<Integer, CancelAiParkInResultDTO> cancelAiParkInResultMap = payload.getResults().stream()
                        .collect(Collectors.toMap(CancelAiParkInResultDTO::getType, dto -> dto));

        // 2.校验车辆是否存在未完成工单
        String stationCode = cacheFacade.getStationCodeByAvpStationCodeFromCache(payload.getPid());
        OrderBasicInfoEntity orderBasicInfoEntity = orderBasicInfoRepository.selectUnfinishedOrder(stationCode, payload.getVin());
        if (Objects.isNull(orderBasicInfoEntity)) {
            log.warn("车辆不存在未完成工单! AVP场站编号=[{}], VIN码=[{}]", payload.getPid(), payload.getVin());
            return;
        }

        // 3.与数据库中的服务项比较，根据应答结果修改数据库中服务型状态，并将结果推送H-VIP小程序
        List<OrderItemInfoEntity> orderItemInfoEntityList = orderItemInfoRepository.findAllByOrderCodeAndItemCodeIn(
                orderBasicInfoEntity.getOrderCode(), Arrays.asList(orderBasicInfoEntity.getItemList()));
        for (OrderItemInfoEntity orderItemInfoEntity : orderItemInfoEntityList) {
            CancelAiParkInResultDTO cancelAiParkInResult = cancelAiParkInResultMap.get(orderItemInfoEntity.getItemCode());
            if (Objects.isNull(cancelAiParkInResult)) {
                log.warn("中心云未应答取消泊车服务项结果, 工单号=[{}], 服务项=[{}]", orderItemInfoEntity.getOrderCode(), orderItemInfoEntity.getItemCode());
                continue;
            }

            // 3.1.过滤服务状态为取消中
            if (!OrderConstants.ORDER_ITEM_STATUS_CANCELLING.equals(orderItemInfoEntity.getItemStatus())) {
                continue;
            }

            // 3.2.向H-VIP小程序发送取消服务结果通知
            String plateNo = cacheFacade.getPlateNoByVinFromCache(payload.getVin());
            CancelResultDownDTO cancelResultDownDTO = CancelResultDownDTO.of(stationCode, plateNo, orderItemInfoEntity.getOrderCode(),
                    orderItemInfoEntity.getItemCode(), cancelAiParkInResult.getResult(), cancelAiParkInResult.getError());
            mqttMessageFacade.sendMqttMessage(MpMqttTopicEnum.MP_DOWN_TOPIC_CANCEL_RESULT, cancelResultDownDTO);

            // 3.3.更新数据库中的服务状态
            if (cancelAiParkInResult.getResult()) {
                orderService.updateOrderItem(orderItemInfoEntity, null, OrderConstants.ORDER_ITEM_STATUS_CANCELED, null);
            }
        }
    }
}
