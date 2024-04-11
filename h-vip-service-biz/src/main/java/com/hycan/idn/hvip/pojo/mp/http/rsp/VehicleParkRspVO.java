package com.hycan.idn.hvip.pojo.mp.http.rsp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 车辆泊车信息响应对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@NoArgsConstructor
@ApiModel(value = "车辆泊车信息响应对象")
public class VehicleParkRspVO {

    @ApiModelProperty(value = "车架号")
    @JsonProperty("vin")
    private String vin;

    @ApiModelProperty(value = "场站编号")
    @JsonProperty("station_code")
    private String stationCode;

    @ApiModelProperty(value = "车牌号")
    @JsonProperty("plate_no")
    private String plateNo;

    @ApiModelProperty(value = "车位号")
    @JsonProperty("pak_no")
    private String pakNo;

    @ApiModelProperty(value = "工单编号")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty(value = "记录时间")
    @JsonProperty("record_time")
    private LocalDateTime recordTime;

    public VehicleParkRspVO(String vin, String stationCode, String plateNo, String pakNo, String orderCode, LocalDateTime recordTime) {
        this.vin = vin;
        this.stationCode = stationCode;
        this.plateNo = plateNo;
        this.pakNo = pakNo;
        this.orderCode = orderCode;
        this.recordTime = recordTime;
    }
}
