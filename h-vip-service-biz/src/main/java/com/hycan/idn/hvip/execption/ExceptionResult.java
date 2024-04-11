package com.hycan.idn.hvip.execption;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 异常信息结果
 *
 * @author shichongying
 * @datetime 2023/10/10 14:07
 */
@Data
public class ExceptionResult {

    /**
     * 错误码
     */
    @JsonProperty(value = "error_code")
    private String errorCode;

    /**
     * 错误描述
     */
    @JsonProperty(value = "error_msg")
    private String errorMsg;

    /**
     * 时间戳
     */
    private Long timestamp;

    public ExceptionResult(ExceptionEnums em){
        this.errorCode = em.getErrorCode();
        this.errorMsg = em.getErrorMsg();
        this.timestamp = System.currentTimeMillis();
    }
}