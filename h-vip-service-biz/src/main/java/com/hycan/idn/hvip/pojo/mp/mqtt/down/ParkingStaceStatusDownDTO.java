package com.hycan.idn.hvip.pojo.mp.mqtt.down;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hycan.idn.hvip.entity.StationParkInfoEntity;
import com.hycan.idn.hvip.pojo.mp.mqtt.MpPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

/**
 * 车位状态变化通知下行对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ParkingStaceStatusDownDTO extends MpPayload {

    /**
     * 场站编号
     */
    @JsonProperty(value = "station_code")
    private String stationCode;

    /**
     * 车位号
     */
    @JsonProperty(value = "park_no")
    private String parkNo;

    /**
     * 车位类型(0:停车位 1:充电位 2:洗车位 3:检测位 4:待泊位 5:取车位)
     */
    @JsonProperty(value = "park_type")
    private Integer parkType;

    /**
     * 车位状态(0:空闲1:占用 2:故障 3:不可用 4:未知)
     */
    @JsonProperty(value = "park_status")
    private Integer parkStatus;

    public static ParkingStaceStatusDownDTO of(StationParkInfoEntity stationParkInfoEntity, String plateNo) {
        ParkingStaceStatusDownDTO parkingStaceStatusDownDTO = new ParkingStaceStatusDownDTO();
        BeanUtils.copyProperties(stationParkInfoEntity, parkingStaceStatusDownDTO);
        parkingStaceStatusDownDTO.setPlateNo(plateNo);
        return parkingStaceStatusDownDTO;
    }
}
