package com.hycan.idn.hvip.pojo.mp.mqtt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * H-VIP小程序 MQTT 消息 Body
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@NoArgsConstructor
@ToString
public class MpPayload implements Serializable {

    /**
     * 车牌号
     */
    @JsonProperty(value = "plate_no")
    private String plateNo;
}
