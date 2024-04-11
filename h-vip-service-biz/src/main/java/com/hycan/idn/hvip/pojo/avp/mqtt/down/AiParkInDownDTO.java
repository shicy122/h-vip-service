package com.hycan.idn.hvip.pojo.avp.mqtt.down;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hycan.idn.hvip.pojo.avp.mqtt.AvpVinPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 泊车服务应答下行对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AiParkInDownDTO extends AvpVinPayload {

    /**
     * 停车场id
     */
    private String pid;

    /**
     * 停车位ID
     */
    @JsonProperty(value = "space_id")
    private String spaceId;

    /**
     * 车位类型
     */
    @JsonProperty(value = "space_type")
    private Integer spaceType;

    /**
     * 泊车结果
     */
    private List<AiParkInResultDTO> results = new ArrayList<>();
}
