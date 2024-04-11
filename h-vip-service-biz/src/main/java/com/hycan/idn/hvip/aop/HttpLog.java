package com.hycan.idn.hvip.aop;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * HTTP请求记录出入口日志注解
 *
 * @author shichongying
 * @datetime 2023-10-20 11:30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface HttpLog {

    @AliasFor("msg")
    String value() default "";

    @AliasFor("value")
    String msg() default "";

    /**
     * 需要被匿名化的字段数组
     */
    String[] anonymous() default {""};
}