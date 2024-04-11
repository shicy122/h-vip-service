package com.hycan.idn.hvip.mqtt.handler;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.hycan.idn.hvip.mqtt.publisher.AvpMessageEventPublisher;
import com.hycan.idn.hvip.pojo.avp.mqtt.AvpHeader;
import com.hycan.idn.hvip.pojo.avp.mqtt.AvpMessage;
import com.hycan.idn.hvip.pojo.avp.mqtt.Payload;
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
public class AvpMqttMessageHandler {

    @Resource
    private AvpMessageEventPublisher publisher;

    /**
     * 处理订阅消息入口
     *
     * @param message MQTT消息
     */
    public void handleMessage(Message<?> message) {
        String topic = (String) message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC);
        AvpMessage avpMessage = (AvpMessage) message.getPayload();
        if (!StringUtils.hasText(topic)) {
            log.error("AVP MQTT Topic错误! Topic=[{}], AvpMessage=[{}]", topic, avpMessage);
            return;
        }


        if (Objects.isNull(avpMessage.getPayload())) {
            log.error("AVP MQTT Data错误! Topic=[{}], Payload=[{}]", topic, avpMessage);
            return;
        }

        publisher.publish(topic, avpMessage);
    }

    /**
     * 处理发布消息入口
     *
     * @param topic   MQTT主题
     * @param payload 业务消息Body
     */
    public void handleMessage(String topic, Payload payload) {
        if (!StringUtils.hasText(topic) || Objects.isNull(payload)) {
            log.error("AVP MQTT消息格式错误！Topic=[{}], Payload=[{}]", topic, payload);
            return;
        }

        AvpHeader header = AvpHeader.builder()
                .seq(String.valueOf(IdWorker.getId()))
                .time(String.valueOf(System.currentTimeMillis()))
                .sender("HYCAN H-VIP")
                .modelVersion("1")
                .build();
        AvpMessage message = AvpMessage.builder()
                .header(header)
                .payload(payload)
                .build();

        publisher.publish(topic, message);
    }
}
