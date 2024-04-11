package com.hycan.idn.hvip.mqtt.publisher;

import com.hycan.idn.hvip.mqtt.event.MpMessageEvent;
import com.hycan.idn.hvip.pojo.mp.mqtt.MpMessage;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * H-VIP小程序系统消息事件发布者
 *
 * @author shichongying
 * @datetime 2023-10-21 11:52
 */
@Component
public class MpMessageEventPublisher {

    private final ApplicationContext applicationContext;

    public MpMessageEventPublisher(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 发布系统消息事件
     * @param topic   主题
     * @param mpMessage    小程序消息
     */
    public void publish(String topic, MpMessage mpMessage) {
        applicationContext.publishEvent(new MpMessageEvent(this, topic, mpMessage));
    }
}