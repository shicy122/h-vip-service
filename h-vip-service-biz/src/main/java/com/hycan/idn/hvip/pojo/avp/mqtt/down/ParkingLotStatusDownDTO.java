package com.hycan.idn.hvip.pojo.avp.mqtt.down;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hycan.idn.hvip.pojo.avp.mqtt.AvpPidPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 边缘云状态下行对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ParkingLotStatusDownDTO extends AvpPidPayload {

    /**
     * 上线0 下线1
     */
    @JsonProperty("online_status")
    private Integer onlineStatus;
}
