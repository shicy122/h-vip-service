package com.hycan.idn.hvip.pojo.mp.mqtt.down;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hycan.idn.hvip.pojo.avp.mqtt.down.VehicleScheduleStatusDetailDTO;
import com.hycan.idn.hvip.pojo.mp.mqtt.MpPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 调度状态通知下行对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DispatchStateDownDTO extends MpPayload {

    /**
     * 场站编号
     */
    @JsonProperty(value = "station_code")
    private String stationCode;

    /**
     * 调度状态(0: 已完成 1:进行中 2:排队中)
     */
    @JsonProperty(value = "dispatch_status")
    private Integer dispatchStatus;

    /**
     * 起始车位类型(0:停车位 1:充电位 2:洗车位 3:检测位 4:待泊位 5:取车位)
     */
    @JsonProperty(value = "src_park_type")
    private Integer srcParkType;

    /**
     * 目标车位类型(0:停车位 1:充电位 2:洗车位 3:检测位 4:待泊位 5:取车位)
     */
    @JsonProperty(value = "dst_park_type")
    private Integer dstParkType;

    /**
     * 目标服务类型(0:停车 1:充电 2:洗车 3:检测)
     */
    @JsonProperty(value = "dst_service_type")
    private Integer dstServiceType;

    public static DispatchStateDownDTO of(String stationCode, String plateNo, VehicleScheduleStatusDetailDTO avpDTO) {
        DispatchStateDownDTO dto = new DispatchStateDownDTO();
        dto.setStationCode(stationCode);
        dto.setPlateNo(plateNo);
        dto.setDispatchStatus(avpDTO.getCurrentStatus());
        dto.setSrcParkType(avpDTO.getCurrentSpaceType());
        dto.setDstParkType(avpDTO.getDstSpaceType());
        dto.setDstServiceType(avpDTO.getDstServiceType());
        return dto;
    }
}
