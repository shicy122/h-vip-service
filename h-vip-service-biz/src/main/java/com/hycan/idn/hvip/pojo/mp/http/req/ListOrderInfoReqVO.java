package com.hycan.idn.hvip.pojo.mp.http.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hycan.idn.hvip.constants.HttpConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 分页查询工单列表请求对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "分页查询工单列表请求对象")
public class ListOrderInfoReqVO {

    @NotBlank
    @Length(min = 1, max = 20, message = "车架号长度错误")
    @ApiModelProperty(value="车架号", example = "LMWT31S57N1S00036", required = true, position = 1)
    @JsonProperty("vin")
    private String vin;

    @Min(value = 1, message = "工单状态值不能小于1")
    @Max(value = 5, message = "工单状态值不能大于4")
    @ApiModelProperty(value="工单状态", notes = "0:末开始 1:进行中 2:已完成 3:等待取车 4:取车中 5:取车完成", example = "1", allowableValues = "1, 2, 3, 4, 5", position = 2)
    @JsonProperty("order_status")
    private Integer orderStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="工单开始时间", example = "2023-07-01 00:00:00", position = 3)
    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value="工单结束时间", example = "2023-07-01 23:59:59", position = 4)
    @JsonProperty("end_time")
    private LocalDateTime endTime;

    @Min(value = 0, message = "起始页码值不能小于0")
    @Max(value = Integer.MAX_VALUE)
    @ApiModelProperty(value = "起始页码，默认值：0", notes = "默认值：0，范围：[1-0x7fffffff]", example = "10", required = true, position = 5)
    @JsonProperty("current")
    private Integer current = HttpConstants.DEFAULT_CURRENT;

    @Min(value = 1, message = "分页大小值不能小于1")
    @Max(value = 100, message = "分页大小值不能大于100")
    @ApiModelProperty(value = "分页大小", notes = "默认值：10，范围：[1-100]", example = "10", required = true, position = 6)
    @JsonProperty("size")
    private Integer size = HttpConstants.DEFAULT_SIZE;
}