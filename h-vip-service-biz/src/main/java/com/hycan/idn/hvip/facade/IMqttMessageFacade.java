package com.hycan.idn.hvip.facade;

import com.hycan.idn.hvip.enums.AvpMqttTopicEnum;
import com.hycan.idn.hvip.enums.MpMqttTopicEnum;
import com.hycan.idn.hvip.pojo.avp.mqtt.AvpPidPayload;
import com.hycan.idn.hvip.pojo.avp.mqtt.AvpVinPayload;
import com.hycan.idn.hvip.pojo.mp.mqtt.MpPayload;

/**
 * MQTT消息包装类
 *
 * @author shichongying
 * @datetime 2023-10-20 17:50
 */
public interface IMqttMessageFacade {

    /**
     * 向AVP发送MQTT消息（Topic指定AVP场站编号场景使用）
     *
     * @param avpMqttTopicEnum AVP MQTT Topic 枚举
     * @param payload 消息载荷
     */
    void sendMqttMessage(AvpMqttTopicEnum avpMqttTopicEnum, AvpPidPayload payload);

    /**
     * 向AVP发送MQTT消息（Topic指定VIN码场景使用）
     *
     * @param avpMqttTopicEnum AVP MQTT Topic 枚举
     * @param payload 消息载荷
     */
    void sendMqttMessage(AvpMqttTopicEnum avpMqttTopicEnum, AvpVinPayload payload);

    /**
     * 向H-VIP小程序发送MQTT消息
     *
     * @param mpMqttTopicEnum MP MQTT Topic 枚举
     * @param payload 消息载荷
     */
    void sendMqttMessage(MpMqttTopicEnum mpMqttTopicEnum, MpPayload payload);
}
