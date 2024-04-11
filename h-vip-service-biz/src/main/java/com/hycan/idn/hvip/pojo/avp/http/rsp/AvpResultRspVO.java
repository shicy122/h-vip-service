package com.hycan.idn.hvip.pojo.avp.http.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * AVP HTTP请求调用结果响应对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvpResultRspVO implements Serializable {

    @ApiModelProperty(value = "状态(OK 表示成功)")
    private String status;

    @ApiModelProperty(value = "错误码(10000 表示成功)")
    private String code;

    @ApiModelProperty(value = "描述")
    private String message;

    @ApiModelProperty(value = "业务数据")
    private Object data;

    @ApiModelProperty(value = "毫秒时间戳")
    private Long date;
}