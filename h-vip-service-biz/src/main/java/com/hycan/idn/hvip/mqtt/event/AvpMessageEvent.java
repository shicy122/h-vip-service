package com.hycan.idn.hvip.mqtt.event;

import com.hycan.idn.hvip.pojo.avp.mqtt.AvpMessage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * AVP消息事件
 *
 * @author shichongying
 * @datetime 2023-10-21 11:36
 */
@Getter
@Setter
public class AvpMessageEvent extends ApplicationEvent {

    private String topic;
    private AvpMessage message;

    public AvpMessageEvent(Object source, String topic, AvpMessage message) {
        super(source);
        this.topic = topic;
        this.message = message;
    }
}
