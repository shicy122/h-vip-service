package com.hycan.idn.hvip.pojo.avp.mqtt.down;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hycan.idn.hvip.pojo.avp.mqtt.AvpVinPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 车辆调度权限下行对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class VehicleSchedulePermissionDownDTO extends AvpVinPayload {

    /**
     * 停车场id
     */
    private String pid;

    /**
     * 请求调度权限from车位类型(0:停车位 1:充电位 2:洗车位 3:检测位 101:待泊位 100:取车位)
     */
    @JsonProperty(value = "from_type")
    private Integer fromType;

    /**
     * 请求调度权限to车位类型(0:停车位 1:充电位 2:洗车位 3:检测位 101:待泊位 100:取车位)
     */
    @JsonProperty(value = "to_type")
    private Integer toType;
}
