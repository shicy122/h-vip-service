package com.hycan.idn.hvip.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * MQTT消息类型枚举
 *
 * @author shichongying
 * @datetime 2023-10-19 16:00
 */
@Getter
@AllArgsConstructor
public enum MqttMsgTypeEnum {

    MSG_TYPE_AVP(1, "AVP"),

    MSG_TYPE_MP(2, "MP")
    ;

    private final Integer type;

    private final String desc;
}
