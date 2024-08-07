package com.hycan.idn.hvip.mqtt.gateway;

import com.hycan.idn.hvip.config.MpMqttConfig;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * H-VIP小程序发布消息网关
 *
 * @author shichongying
 * @datetime 2023-10-21 11:38
 */
@Component
@MessagingGateway(defaultRequestChannel = MpMqttConfig.MP_OUTBOUND_CHANNEL)
public interface MpMqttGateway {

    /**
     * 发送消息到MQTT Broker
     *
     * @param topic   MQTT主题
     * @param qos     对消息处理的几种机制。
     *                * 0 表示的是订阅者没收到消息不会再次发送，消息会丢失。<br>
     *                * 1 表示的是会尝试重试，一直到接收到消息，但这种情况可能导致订阅者收到多次重复消息。<br>
     *                * 2 多了一次去重的动作，确保订阅者收到的消息有一次。
     * @param payload json串
     */
    void send(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, String payload);
}