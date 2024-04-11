package com.hycan.idn.hvip.constants;

import okhttp3.MediaType;

/**
 * HTTP相关常量
 *
 * @author shichongying
 * @datetime 2023-10-19 17:57
 */
public interface HttpConstants {

    // @formatter:off

    /** HTTP请求Header部分的 userId */
    String H_USER_ID = "H-USER-ID";

    /** HTTP请求Header部分的 userName */
    String H_USER_NAME = "H-USER-NAME";

    /** 默认分页大小 */
    Integer DEFAULT_SIZE = 10;

    /** 默认页码 */
    Integer DEFAULT_CURRENT = 0;

    /** 数据类型 */
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /** AVP鉴权Header key */
    String AVP_AUTHORIZATION = "Authorization";

    /** AVP鉴权Header value前缀 */
    String AVP_BEARER = "Bearer ";
}