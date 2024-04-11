package com.hycan.idn.hvip.execption;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 异常信息枚举
 *
 * @author shichongying
 * @datetime 2023/10/10 14:07
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum  ExceptionEnums {

    STATION_NOT_EXIST(HttpStatus.BAD_REQUEST, "1011001", "操作失败, 服场站不存在!"),
    ORDER_NOT_EXIST(HttpStatus.BAD_REQUEST, "1011002", "操作失败, 服服务工单不存在!"),
    ORDER_UNFINISHED(HttpStatus.BAD_REQUEST, "1011003", "操作失败, 服车辆存在未完成工单!"),
    ORDER_ITEM_NOT_EXIST(HttpStatus.BAD_REQUEST, "1011004", "操作失败, 服服务项目不存在!"),
    VIN_NOT_EXIST(HttpStatus.BAD_REQUEST, "1011005", "操作失败, 服车辆不存在!"),
    ORDER_ITEM_IS_CANCELED(HttpStatus.BAD_REQUEST, "1011006", "操作失败, 服服务项目已取消!"),
    ORDER_ITEM_IS_FINISH(HttpStatus.BAD_REQUEST, "1011007", "操作失败, 服服务项目已完成!"),
    ORDER_ITEM_IS_CANCELLING(HttpStatus.BAD_REQUEST, "1011008", "操作失败, 服务项目取消中!"),

    SYS_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "2015001", "服务端处理异常!"),
    ;

    /**
     * HTTP状态码
     */
    private HttpStatus httpStatus;

    /**
     * 错误码
     * 错误码规则：错误来源 + 服务模块 + 错误编号
     * 错误来源：1=用户端错误  2=系统错误  3=第三方错误
     * 服务模块：H-VIP服务使用 01
     * 错误编号：1001~1999 业务异常  5001~5999 系统异常
     */
    private String errorCode;

    /**
     * 错误详情
     */
    private String errorMsg;
}