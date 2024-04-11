package com.hycan.idn.hvip.pojo.avp.mqtt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AVP消息对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvpMessage {

   /**
    * 头部信息
    */
   private AvpHeader header;

   /**
    * 消息体
    */
   private Payload payload;
}
