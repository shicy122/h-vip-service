package com.hycan.idn.hvip.mqtt.handler;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.hycan.idn.hvip.pojo.mp.mqtt.MpHeader;
import com.hycan.idn.hvip.pojo.mp.mqtt.MpMessage;
import com.hycan.idn.hvip.mqtt.publisher.MpMessageEventPublisher;
import com.hycan.idn.hvip.pojo.mp.mqtt.MpPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 订阅MQTT Broker消息处理器
 *
 * @author shichongying
 * @datetime 2023-10-21 11:40
 */
@Slf4j
@Component
public class MpMqttMessageHandler {

    @Resource
    private MpMessageEventPublisher publisher;

    /**
     * 处理订阅消息入口
     *
     * @param message MQTT消息
     */
    public void handleMessage(Message<?> message) {
        String topic = (String) message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC);
        MpMessage mpMessage = (MpMessage) message.getPayload();
        if (!StringUtils.hasText(topic)) {
            log.error("MP MQTT Topic错误! Topic=[{}], MpMessage=[{}]", topic, mpMessage);
            return;
        }

        if (Objects.isNull(mpMessage.getPayload())) {
            log.error("MP MQTT Data错误! Topic=[{}], MpMessage=[{}]", topic, mpMessage);
            return;
        }

        publisher.publish(topic, mpMessage);
    }

    /**
     * 处理发布消息入口
     *
     * @param topic   MQTT主题
     * @param payload 业务消息Body
     */
    public void handleMessage(String topic, MpPayload payload) {
        if (!StringUtils.hasText(topic) || Objects.isNull(payload)) {
            log.error("MP MQTT消息格式错误！Topic=[{}], Payload=[{}]", topic, payload);
            return;
        }

        MpHeader header = MpHeader.builder()
                .id(String.valueOf(IdWorker.getId()))
                .time(String.valueOf(System.currentTimeMillis()))
                .build();
        MpMessage message = MpMessage.builder()
                .header(header)
                .payload(payload)
                .build();

        publisher.publish(topic, message);
    }
}