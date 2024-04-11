package com.hycan.idn.hvip.pojo.avp.http.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 场站信息响应对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StationRspVO implements Serializable {

    @ApiModelProperty(value = "版本对象")
    private VersionRspVO version;

    @ApiModelProperty(value = "在线状态")
    private Boolean isOnline;
}
