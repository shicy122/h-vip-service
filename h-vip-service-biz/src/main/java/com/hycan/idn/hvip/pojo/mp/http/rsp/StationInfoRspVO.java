package com.hycan.idn.hvip.pojo.mp.http.rsp;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 场站几乎成信息响应对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "场站基础信息响应对象")
public class StationInfoRspVO {

    @ApiModelProperty(value = "场站编号")
    @JsonProperty("station_code")
    private String stationCode;

    @ApiModelProperty(value = "场站名称")
    @JsonProperty("station_name")
    private String stationName;

    @ApiModelProperty(value = "场站状态(0:在线1:离线)")
    @JsonProperty("station_status")
    private Integer stationStatus;

    @ApiModelProperty(value = "场站地址")
    @JsonProperty("station_address")
    private String stationAddress;

    @ApiModelProperty(value = "场站标签")
    @JsonProperty("station_label")
    private String[] stationLabel;

    @ApiModelProperty(value = "场站经度")
    @JsonProperty("longitude")
    private Double longitude;

    @ApiModelProperty(value = "场站纬度")
    @JsonProperty("latitude")
    private Double latitude;

    @ApiModelProperty(value = "服务清单(0停车 1:充电 2:洗车 3:检测)")
    @JsonProperty("item_list")
    private Integer[] itemList;

    @ApiModelProperty(value = "距离")
    @JsonProperty("distance")
    private Double distance;

    public StationInfoRspVO(String stationCode, String stationName, Integer stationStatus, String stationAddress,
                            Object stationLabel, Double longitude, Double latitude,
                            Object itemList) {
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.stationStatus = stationStatus;
        this.stationAddress = stationAddress;
        this.stationLabel = (String[]) stationLabel;
        this.longitude = longitude;
        this.latitude = latitude;
        this.itemList = (Integer[]) itemList;
    }
}