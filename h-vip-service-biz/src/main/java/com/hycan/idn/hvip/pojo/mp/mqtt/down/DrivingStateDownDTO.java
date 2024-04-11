package com.hycan.idn.hvip.pojo.mp.mqtt.down;

import com.hycan.idn.hvip.pojo.avp.mqtt.down.VehicleFeedbackDownDTO;
import com.hycan.idn.hvip.pojo.mp.mqtt.MpPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 行驶状态通知下行对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DrivingStateDownDTO extends MpPayload {

    /**
     * 车速
     */
    private Double speed;

    /**
     * 经度(精确到小数点后6位)
     */
    private Double longitude;

    /**
     * 纬度(精确到小数点后6位)
     */
    private Double latitude;

    public static DrivingStateDownDTO of(VehicleFeedbackDownDTO payload, String plateNo) {
        DrivingStateDownDTO dto = new DrivingStateDownDTO();
        dto.setPlateNo(plateNo);
        dto.setSpeed(payload.getSpeed());
        dto.setLongitude(payload.getLongitude());
        dto.setLatitude(payload.getLatitude());
        return dto;
    }
}
