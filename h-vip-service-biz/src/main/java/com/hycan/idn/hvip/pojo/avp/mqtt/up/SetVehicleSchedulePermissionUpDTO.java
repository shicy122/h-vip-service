package com.hycan.idn.hvip.pojo.avp.mqtt.up;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hycan.idn.hvip.pojo.avp.mqtt.AvpVinPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设置车辆调度权限上行对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SetVehicleSchedulePermissionUpDTO extends AvpVinPayload {

    /**
     * 停车场id
     */
    private String pid;

    /**
     * true:表示设置边缘云拥有调度车辆的权限。false：表示设置边缘云没有拥有调度车辆的权限
     */
    @JsonProperty("enable_schedule_permission")
    private Boolean enableSchedulePermission;

    public static SetVehicleSchedulePermissionUpDTO of(String avpStationCode, String vin) {
        SetVehicleSchedulePermissionUpDTO dto = new SetVehicleSchedulePermissionUpDTO();
        dto.setPid(avpStationCode);
        dto.setVin(vin);
        dto.setEnableSchedulePermission(true);
        return dto;
    }
}
