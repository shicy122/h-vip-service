package com.hycan.idn.hvip.pojo.avp.mqtt.up;

import com.hycan.idn.hvip.pojo.avp.mqtt.AvpPidPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 车位状态上行对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ParkingSpaceStatusUpDTO extends AvpPidPayload {

    public static ParkingSpaceStatusUpDTO of(String pid) {
        ParkingSpaceStatusUpDTO dto = new ParkingSpaceStatusUpDTO();
        dto.setPid(pid);
        return dto;
    }
}
