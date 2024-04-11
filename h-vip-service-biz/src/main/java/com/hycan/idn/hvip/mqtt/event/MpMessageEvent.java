package com.hycan.idn.hvip.mqtt.event;

import com.hycan.idn.hvip.pojo.mp.mqtt.MpMessage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * H-VIP小程序消息事件
 *
 * @author shichongying
 * @datetime 2023-10-21 11:37
 */
@Getter
@Setter
public class MpMessageEvent extends ApplicationEvent {

    private String topic;
    private MpMessage message;

    public MpMessageEvent(Object source, String topic, MpMessage message) {
        super(source);
        this.topic = topic;
        this.message = message;
    }
}
