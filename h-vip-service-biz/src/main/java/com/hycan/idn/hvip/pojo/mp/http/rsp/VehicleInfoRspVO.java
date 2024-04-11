package com.hycan.idn.hvip.pojo.mp.http.rsp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hycan.idn.hvip.entity.VehicleBasicInfoEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 车辆基础信息响应对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "车辆基础信息响应对象")
public class VehicleInfoRspVO {

    @ApiModelProperty(value = "车架号")
    private String vin;

    @ApiModelProperty(value = "车牌")
    @JsonProperty("plate_no")
    private String plateNo;

    @ApiModelProperty(value = " 是支持AVP(0:支持 1:不支持)")
    @JsonProperty("is_avp")
    private Integer isAvp;

    public static VehicleInfoRspVO of(VehicleBasicInfoEntity entity) {
        VehicleInfoRspVO vo = new VehicleInfoRspVO();
        vo.setVin(entity.getVin());
        vo.setPlateNo(entity.getPlateNo());
        vo.setIsAvp(entity.getIsAvp());
        return vo;
    }
}