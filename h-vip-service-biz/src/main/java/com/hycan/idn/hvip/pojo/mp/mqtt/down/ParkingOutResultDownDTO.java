package com.hycan.idn.hvip.pojo.mp.mqtt.down;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hycan.idn.hvip.pojo.avp.mqtt.down.AiParkOutDownDTO;
import com.hycan.idn.hvip.pojo.mp.mqtt.MpPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 取车结果通知下行对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ParkingOutResultDownDTO extends MpPayload {

    /**
     * 场站编号
     */
    @JsonProperty(value = "station_code")
    private String stationCode;

    /**
     * 车牌号
     */
    @JsonProperty(value = "plate_no")
    private String plateNo;

    /**
     * 车位号
     */
    @JsonProperty(value = "park_no")
    private String parkNo;

    /**
     * 泊车结果(true:成功，false:失败)
     */
    private Boolean result;

    /**
     * 失败原因描述
     */
    private String error;

    public static ParkingOutResultDownDTO of(String stationCode, String plateNo, AiParkOutDownDTO aiParkOutDownDTO) {
        ParkingOutResultDownDTO dto = new ParkingOutResultDownDTO();
        dto.setStationCode(stationCode);
        dto.setPlateNo(plateNo);
        dto.setResult(aiParkOutDownDTO.getResult());
        dto.setError(aiParkOutDownDTO.getError());
        return dto;
    }

    public static ParkingOutResultDownDTO of(String stationCode, String plateNo, String parkNo, Boolean result) {
        ParkingOutResultDownDTO dto = new ParkingOutResultDownDTO();
        dto.setStationCode(stationCode);
        dto.setParkNo(parkNo);
        dto.setPlateNo(plateNo);
        dto.setResult(result);
        return dto;
    }
}
