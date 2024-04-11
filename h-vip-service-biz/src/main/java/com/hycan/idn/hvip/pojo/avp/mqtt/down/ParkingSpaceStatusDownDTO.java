package com.hycan.idn.hvip.pojo.avp.mqtt.down;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hycan.idn.hvip.pojo.avp.mqtt.AvpPidPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * 车位状态下行对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ParkingSpaceStatusDownDTO extends AvpPidPayload {

    /**
     * List
     */
    @Size(min = 1, message = "AVP车位状态数据为空")
    @JsonProperty("parking_spaces")
    private List<ParkingSpaceDetailDTO> parkingSpaces = new ArrayList<>();
}


