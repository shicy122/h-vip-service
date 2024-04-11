package com.hycan.idn.hvip.mqtt.publisher;

import com.hycan.idn.hvip.pojo.avp.mqtt.AvpMessage;
import com.hycan.idn.hvip.mqtt.event.AvpMessageEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * AVP系统消息事件发布者
 *
 * @author shichongying
 * @datetime 2023-10-21 11:52
 */
@Component
public class AvpMessageEventPublisher {

    private final ApplicationContext applicationContext;

    public AvpMessageEventPublisher(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 发布系统消息事件
     *
     * @param topic   主题
     * @param message AVP消息
     */
    public void publish(String topic, AvpMessage message) {
        applicationContext.publishEvent(new AvpMessageEvent(this, topic, message));
    }
}