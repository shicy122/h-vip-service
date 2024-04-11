package com.hycan.idn.hvip.pojo.vip;

import lombok.Data;
import lombok.ToString;

/**
 * OK HTTP统一封装响应对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@ToString
public class OkHttpResponseDTO {

    /** HTTP响应码 */
    private int code;

    /** 成功/失败 */
    private boolean isSuccess;

    /** 响应的内容 */
    private String body;
}
