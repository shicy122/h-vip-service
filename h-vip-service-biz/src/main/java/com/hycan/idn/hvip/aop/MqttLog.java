package com.hycan.idn.hvip.aop;

import com.hycan.idn.hvip.enums.MqttMsgTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * MQTT记录上下行日志注解
 *
 * @author shichongying
 * @datetime 2023-10-19 15:48
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MqttLog {

    /**
     * 类型(1:AVP, 2:MP)
     */
    MqttMsgTypeEnum value() default MqttMsgTypeEnum.MSG_TYPE_AVP;
}