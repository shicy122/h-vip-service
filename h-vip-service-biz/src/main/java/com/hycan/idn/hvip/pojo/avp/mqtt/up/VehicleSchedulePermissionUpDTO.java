package com.hycan.idn.hvip.pojo.avp.mqtt.up;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hycan.idn.hvip.pojo.avp.mqtt.AvpVinPayload;
import com.hycan.idn.hvip.pojo.avp.mqtt.down.VehicleSchedulePermissionDownDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

/**
 * 车辆调度权限应答上行对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class VehicleSchedulePermissionUpDTO extends AvpVinPayload {

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

    /**
     * 应答结果(true:授权 false:拒绝)
     */
    private Boolean result;

    public static VehicleSchedulePermissionUpDTO of(VehicleSchedulePermissionDownDTO downDto, boolean result){
        VehicleSchedulePermissionUpDTO upDto = new VehicleSchedulePermissionUpDTO();
        BeanUtils.copyProperties(downDto, upDto);
        upDto.setResult(result);
        return upDto;
    }
}
