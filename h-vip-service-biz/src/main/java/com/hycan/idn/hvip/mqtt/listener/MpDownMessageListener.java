package com.hycan.idn.hvip.mqtt.listener;

import cn.hutool.json.JSONUtil;
import com.hycan.idn.hvip.aop.MqttLog;
import com.hycan.idn.hvip.enums.MqttMsgTypeEnum;
import com.hycan.idn.hvip.mqtt.event.MpMessageEvent;
import com.hycan.idn.hvip.mqtt.gateway.MpMqttGateway;
import io.netty.handler.codec.mqtt.MqttQoS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * H-VIP小程序 MQTT 下行消息监听器
 *
 * @author shichongying
 * @datetime 2023-10-21 11:42
 */
@Slf4j
@Component
public class MpDownMessageListener {

    @Resource
    private MpMqttGateway gateway;

    @Async
    @MqttLog(MqttMsgTypeEnum.MSG_TYPE_MP)
    @EventListener(condition = "#event.topic.endsWith(T(com.hycan.idn.hvip.enums.MpMqttTopicEnum).MP_DOWN_TOPIC_PARKING_SPACE_STATUS.suffix)")
    public void sendParkingSpaceStatus(MpMessageEvent event) {
        gateway.send(event.getTopic(), MqttQoS.AT_LEAST_ONCE.value(), JSONUtil.toJsonStr(event.getMessage()));
    }

    @Async
    @MqttLog(MqttMsgTypeEnum.MSG_TYPE_MP)
    @EventListener(condition = "#event.topic.endsWith(T(com.hycan.idn.hvip.enums.MpMqttTopicEnum).MP_DOWN_TOPIC_DISPATCH_STATE.suffix)")
    public void sendDispatchState(MpMessageEvent event) {
        gateway.send(event.getTopic(), MqttQoS.AT_LEAST_ONCE.value(), JSONUtil.toJsonStr(event.getMessage()));
    }

    @Async
    @MqttLog(MqttMsgTypeEnum.MSG_TYPE_MP)
    @EventListener(condition = "#event.topic.endsWith(T(com.hycan.idn.hvip.enums.MpMqttTopicEnum).MP_DOWN_TOPIC_DISPATCH_LINE.suffix)")
    public void sendDispatchLine(MpMessageEvent event) {
        gateway.send(event.getTopic(), MqttQoS.AT_LEAST_ONCE.value(), JSONUtil.toJsonStr(event.getMessage()));
    }

    @Async
    @MqttLog(MqttMsgTypeEnum.MSG_TYPE_MP)
    @EventListener(condition = "#event.topic.endsWith(T(com.hycan.idn.hvip.enums.MpMqttTopicEnum).MP_DOWN_TOPIC_DRIVING_STATE.suffix)")
    public void sendDrivingState(MpMessageEvent event) {
        gateway.send(event.getTopic(), MqttQoS.AT_LEAST_ONCE.value(), JSONUtil.toJsonStr(event.getMessage()));
    }

    @Async
    @MqttLog(MqttMsgTypeEnum.MSG_TYPE_MP)
    @EventListener(condition = "#event.topic.endsWith(T(com.hycan.idn.hvip.enums.MpMqttTopicEnum).MP_DOWN_TOPIC_DRIVING_LINE.suffix)")
    public void sendDrivingLine(MpMessageEvent event) {
        gateway.send(event.getTopic(), MqttQoS.AT_LEAST_ONCE.value(), JSONUtil.toJsonStr(event.getMessage()));
    }

    @Async
    @MqttLog(MqttMsgTypeEnum.MSG_TYPE_MP)
    @EventListener(condition = "#event.topic.endsWith(T(com.hycan.idn.hvip.enums.MpMqttTopicEnum).MP_DOWN_TOPIC_CANCEL_RESULT.suffix)")
    public void sendCancelResult(MpMessageEvent event) {
        gateway.send(event.getTopic(), MqttQoS.AT_LEAST_ONCE.value(), JSONUtil.toJsonStr(event.getMessage()));
    }

    @Async
    @MqttLog(MqttMsgTypeEnum.MSG_TYPE_MP)
    @EventListener(condition = "#event.topic.endsWith(T(com.hycan.idn.hvip.enums.MpMqttTopicEnum).MP_DOWN_TOPIC_PARKING_OUT_RESULT.suffix)")
    public void sendParkingOutResult(MpMessageEvent event) {
        gateway.send(event.getTopic(), MqttQoS.AT_LEAST_ONCE.value(), JSONUtil.toJsonStr(event.getMessage()));
    }
}
