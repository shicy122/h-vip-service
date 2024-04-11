package com.hycan.idn.hvip.mqtt.listener;

import cn.hutool.json.JSONUtil;
import com.hycan.idn.hvip.aop.MqttLog;
import com.hycan.idn.hvip.enums.MqttMsgTypeEnum;
import com.hycan.idn.hvip.mqtt.event.AvpMessageEvent;
import com.hycan.idn.hvip.mqtt.gateway.AvpMqttGateway;
import io.netty.handler.codec.mqtt.MqttQoS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * AVP MQTT 上行消息监听器
 *
 * @author shichongying
 * @datetime 2023-10-21 11:42
 */
@Slf4j
@Component
public class AvpUpMessageListener {

    @Resource
    private AvpMqttGateway gateway;

    @Async
    @MqttLog(MqttMsgTypeEnum.MSG_TYPE_AVP)
    @EventListener(condition = "#event.topic.startsWith(T(com.hycan.idn.hvip.enums.AvpMqttTopicEnum).AVP_UP_TOPIC_PARKING_SPACE_STATUS.prefix)")
    public void sendParkingSpaceStatus(AvpMessageEvent event) {
        gateway.send(event.getTopic(), MqttQoS.AT_LEAST_ONCE.value(), JSONUtil.toJsonStr(event.getMessage()));
    }

    @Async
    @MqttLog(MqttMsgTypeEnum.MSG_TYPE_AVP)
    @EventListener(condition = "event.topic.startsWith(T(com.hycan.idn.hvip.enums.AvpMqttTopicEnum).AVP_UP_TOPIC_AI_PARK_IN.prefix)")
    public void sendAiParkIn(AvpMessageEvent event) {
        gateway.send(event.getTopic(), MqttQoS.AT_LEAST_ONCE.value(), JSONUtil.toJsonStr(event.getMessage()));
    }

    @Async
    @MqttLog(MqttMsgTypeEnum.MSG_TYPE_AVP)
    @EventListener(condition = "event.topic.startsWith(T(com.hycan.idn.hvip.enums.AvpMqttTopicEnum).AVP_UP_TOPIC_AI_PARK_OUT.prefix)")
    public void sendAiParkOut(AvpMessageEvent event) {
        gateway.send(event.getTopic(), MqttQoS.AT_LEAST_ONCE.value(), JSONUtil.toJsonStr(event.getMessage()));
    }

    @Async
    @MqttLog(MqttMsgTypeEnum.MSG_TYPE_AVP)
    @EventListener(condition = "event.topic.startsWith(T(com.hycan.idn.hvip.enums.AvpMqttTopicEnum).AVP_UP_TOPIC_SET_VEHICLE_SCHEDULE_PERMISSION.prefix)")
    public void sendSetVehicleSchedulePermission(AvpMessageEvent event) {
        gateway.send(event.getTopic(), MqttQoS.AT_LEAST_ONCE.value(), JSONUtil.toJsonStr(event.getMessage()));
    }

    @Async
    @MqttLog(MqttMsgTypeEnum.MSG_TYPE_AVP)
    @EventListener(condition = "event.topic.startsWith(T(com.hycan.idn.hvip.enums.AvpMqttTopicEnum).AVP_UP_TOPIC_VEHICLE_SCHEDULE_PERMISSION.prefix)")
    public void sendVehicleSchedulePermission(AvpMessageEvent event) {
        gateway.send(event.getTopic(), MqttQoS.AT_LEAST_ONCE.value(), JSONUtil.toJsonStr(event.getMessage()));
    }

    @Async
    @MqttLog(MqttMsgTypeEnum.MSG_TYPE_AVP)
    @EventListener(condition = "event.topic.startsWith(T(com.hycan.idn.hvip.enums.AvpMqttTopicEnum).AVP_UP_TOPIC_CANCEL_AI_PARK_IN.prefix)")
    public void sendCancelAiParkIn(AvpMessageEvent event) {
        gateway.send(event.getTopic(), MqttQoS.AT_LEAST_ONCE.value(), JSONUtil.toJsonStr(event.getMessage()));
    }
}
