package com.hycan.idn.hvip.pojo.avp.mqtt.down;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 车位状态详情
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
public class ParkingSpaceDetailDTO {

    /**
     * 停车位id
     */
    @JsonProperty("space_id")
    private String spaceId;

    /**
     * 车位类型：0:普通位 1:充电位 2:洗车位 3:底盘检测位 4:其它
     */
    @JsonProperty("space_type")
    private Integer spaceType;

    /**
     * 停车位状态：1:占用 2:预约 3:遮档 4:车子泊入中
     */
    private Integer status;

    /**
     * 车架号
     */
    private String vin;

    /**
     * 车牌号
     */
    private String lpn;
}