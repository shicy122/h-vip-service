package com.hycan.idn.hvip.pojo.mp.mqtt;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * H-VIP小程序 MQTT 消息 Header
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@Builder
@ToString
public class MpHeader implements Serializable {

    /**
     * 消息id
     */
    private String id;

    /**
     * 时间
     */
    private String time;
}