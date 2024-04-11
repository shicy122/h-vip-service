package com.hycan.idn.hvip.pojo.mp.mqtt;

import lombok.Builder;
import lombok.Data;

/**
 * H-VIP小程序 MQTT 消息
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@Builder
public class MpMessage {

   /**
    * 头部信息
    */
   private MpHeader header;

   /**
    * 消息体
    */
   private MpPayload payload;
}