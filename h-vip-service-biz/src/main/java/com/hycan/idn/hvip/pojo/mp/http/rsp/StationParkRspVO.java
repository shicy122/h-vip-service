package com.hycan.idn.hvip.pojo.mp.http.rsp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 场站车位信息响应对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@NoArgsConstructor
@ApiModel(value = "场站车位信息响应对象")
public class StationParkRspVO {

    @ApiModelProperty(value = "车架号")
    @JsonProperty("vin")
    private String vin;

    @ApiModelProperty(value = "场站编号")
    @JsonProperty("station_code")
    private String stationCode;

    @ApiModelProperty(value = "停车位编号")
    @JsonProperty("park_no")
    private String parkNo;

    @ApiModelProperty(value = "车位类型(0:停车位 1:充电位 2:洗车位 3:检测位 101:待泊位 100:取车位)")
    @JsonProperty("park_type")
    private Integer parkType;

    @ApiModelProperty(value = "车位状态(0:空闲 1:占用 2:故障 3:不可用 4:未知)")
    @JsonProperty("park_status")
    private Integer parkStatus;

    public StationParkRspVO(String vin, String stationCode, String parkNo, Integer parkType, Integer parkStatus) {
        this.vin = vin;
        this.stationCode = stationCode;
        this.parkNo = parkNo;
        this.parkType = parkType;
        this.parkStatus = parkStatus;
    }
}
