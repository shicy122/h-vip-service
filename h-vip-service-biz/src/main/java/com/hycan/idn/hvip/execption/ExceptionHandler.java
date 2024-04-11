package com.hycan.idn.hvip.execption;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 通用异常处理
 *
 * @author shichongying
 * @datetime 2023/10/10 14:07
 */
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(CommonBizException.class)
  	@ResponseBody
    public ResponseEntity<ExceptionResult> exceptionHandler(CommonBizException ex){
        //获取枚举
        ExceptionEnums em = ex.getExceptionEnums();
        //返回异常信息
        return ResponseEntity.status(em.getHttpStatus()).body(new ExceptionResult(em));
    }
}