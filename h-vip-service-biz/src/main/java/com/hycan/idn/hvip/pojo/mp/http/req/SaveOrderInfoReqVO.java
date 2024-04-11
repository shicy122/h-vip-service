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
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建工单列表请求对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "创建工单列表请求对象")
public class SaveOrderInfoReqVO {

    @NotBlank
    @Length(min = 1, max = 20, message = "车架号长度错误")
    @ApiModelProperty(value="车架号", example = "LMWT31S57N1S00036", required = true, position = 1)
    @JsonProperty("vin")
    private String vin;

    @NotBlank
    @Length(min = 1, max = 20, message = "场站编号长度错误")
    @ApiModelProperty(value="场站编号", example = "ZJW123456789", required = true, position = 2)
    @JsonProperty("station_code")
    private String stationCode;

    @ApiModelProperty(value="服务列表", notes = "值为空时，默认表示仅停车", example = "[1, 2, 3]", allowableValues = "1, 2, 3", position = 3)
    @JsonProperty("item_list")
    private List<@Min(1) @Max(3) Integer> itemList = new ArrayList<>();
}
