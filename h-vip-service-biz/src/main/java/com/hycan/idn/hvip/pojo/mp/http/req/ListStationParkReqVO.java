package com.hycan.idn.hvip.pojo.mp.http.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 查询车位列表请求对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "查询车位列表请求对象")
public class ListStationParkReqVO {

    @Length(max = 20, message = "停车位编号长度错误")
    @ApiModelProperty(value = "停车位编号", example = "A01", position = 1)
    @JsonProperty("park_no")
    private String parkNo;

    @Min(value = 0, message = "场站状态值不能小于0")
    @Max(value = 101, message = "场站状态值不能大于1")
    @ApiModelProperty(value = "车位类型", allowableValues = "0:停车位 1:充电位 2:洗车位 3:检测位 101:待泊位 100:取车位", example = "1", position = 2)
    @JsonProperty("park_type")
    private Integer parkType;

    @Min(value = 0, message = "场站状态值不能小于0")
    @Max(value = 4, message = "场站状态值不能大于1")
    @ApiModelProperty(value = "车位状态", allowableValues = "0:空闲 1:占用 2:故障 3:不可用 4:未知", example = "1", position = 3)
    @JsonProperty("park_status")
    private Integer parkStatus;
}
