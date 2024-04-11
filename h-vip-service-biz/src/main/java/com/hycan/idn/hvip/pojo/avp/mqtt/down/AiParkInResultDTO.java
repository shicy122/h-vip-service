package com.hycan.idn.hvip.pojo.avp.mqtt.down;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 泊车服务结果详情
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
public class AiParkInResultDTO {

    @JsonProperty(value = "space_type")
    private Integer spaceType;

    private Boolean result;

    private String error;
}
