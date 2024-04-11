package com.hycan.idn.hvip.execption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 通用业务异常包装
 *
 * @author shichongying
 * @datetime 2023-10-20 17:37
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommonBizException extends RuntimeException {

    private ExceptionEnums exceptionEnums;
}