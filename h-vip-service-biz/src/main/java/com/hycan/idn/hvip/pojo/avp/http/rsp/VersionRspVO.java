package com.hycan.idn.hvip.pojo.avp.http.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 车场版本
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VersionRspVO implements Serializable {

    @ApiModelProperty(value = "停车场id")
    private String parkingFacId;

    @ApiModelProperty(value = "version")
    private Integer version;
}