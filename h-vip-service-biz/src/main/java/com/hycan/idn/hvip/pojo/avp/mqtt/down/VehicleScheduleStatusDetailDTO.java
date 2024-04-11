package com.hycan.idn.hvip.pojo.avp.mqtt.down;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 车辆调度状态详情
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
public class VehicleScheduleStatusDetailDTO {

    /**
     * 目标服务类型(0:停车 1:充电 2:洗车 3:检测)
     */
    @JsonProperty(value = "dst_service_type")
    private Integer dstServiceType;

    /**
     * 当前调度状态(0:完成 1:正在泊车 2:泊车排队中)
     */
    @JsonProperty(value = "current_status")
    private Integer currentStatus;

    /**
     * 当前车位类型(0:停车位 1:充电位 2:洗车位 3:检测位 101:待泊位 100:取车位)
     */
    @JsonProperty(value = "current_space_type")
    private Integer currentSpaceType;

    /**
     * 目标车位类型(0:停车位 1:充电位 2:洗车位 3:检测位 101:待泊位 100:取车位)
     */
    @JsonProperty(value = "dst_space_type")
    private Integer dstSpaceType;
}
