package com.hycan.idn.hvip.pojo.mp.mqtt.down;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hycan.idn.hvip.pojo.avp.mqtt.down.NewRoutPathDTO;
import com.hycan.idn.hvip.pojo.mp.mqtt.MpPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 调度路线通知下行对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DispatchLineDownDTO extends MpPayload {

    /**
     * 场站编号
     */
    @JsonProperty(value = "station_code")
    private String stationCode;

    private List<DispatchLinePositionDTO> positions = new ArrayList<>();

    public static DispatchLineDownDTO of(String stationCode, String plateNo, List<NewRoutPathDTO> path) {
        DispatchLineDownDTO dto = new DispatchLineDownDTO();
        dto.setStationCode(stationCode);
        dto.setPlateNo(plateNo);

        List<DispatchLinePositionDTO> positions = new ArrayList<>();
        BeanUtils.copyProperties(path, positions);
        dto.setPositions(positions);
        return dto;
    }
}
