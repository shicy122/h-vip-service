package com.hycan.idn.hvip.pojo.mp.http.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hycan.idn.hvip.constants.HttpConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 分页查询场站列表请求对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "分页查询场站列表请求对象")
public class ListStationInfoReqVO {

    @Length(max = 20, message = "场站编号长度错误")
    @ApiModelProperty(value = "场站编号", example = "ZJW123456789", position = 1)
    @JsonProperty("station_code")
    private String stationCode;

    @Length(max = 50, message = "场站名称长度错误")
    @ApiModelProperty(value = "场站名称", example = "珠江湾充电桩", position = 2)
    @JsonProperty("station_name")
    private String stationName;

    @Min(value = 0, message = "场站状态值不能小于0")
    @Max(value = 1, message = "场站状态值不能大于1")
    @ApiModelProperty(value = "场站状态", allowableValues = "0:在线 1:离线", example = "1", position = 3)
    @JsonProperty("station_status")
    private Integer stationStatus;

    @NotNull
    @DecimalMin(value = "-180", message = "场站经度最小值不能小于-180")
    @DecimalMax(value = "180", message = "场站经度最大值不能大于180")
    @ApiModelProperty(value = "场站经度", example = "113.75", position = 4)
    @JsonProperty("longitude")
    private Double longitude;

    @NotNull
    @DecimalMin(value = "-90", message = "场站纬度最小值不能小于-90")
    @DecimalMax(value = "90", message = "场站纬度最大值不能大于90")
    @ApiModelProperty(value = "场站纬度", example = "26.30", position = 5)
    @JsonProperty("latitude")
    private Double latitude;

    @Min(value = 1, message = "起始页码值不能小于1")
    @Max(value = Integer.MAX_VALUE)
    @ApiModelProperty(value = "起始页码，默认值：1", notes = "默认值：1，范围：[1-0x7fffffff]", example = "10", required = true, position = 5)
    private Integer current = HttpConstants.DEFAULT_CURRENT;

    @Min(value = 1, message = "分页大小值不能小于1")
    @Max(value = 100, message = "分页大小值不能大于100")
    @ApiModelProperty(value = "分页大小", notes = "默认值：10，范围：[1-100]", example = "10", required = true, position = 6)
    private Integer size = HttpConstants.DEFAULT_SIZE;
}