package com.hycan.idn.hvip.constants;

/**
 * Redis相关常量
 *
 * @author shichongying
 * @datetime 2023-10-19 17:57
 */
public interface RedisConstants {

    /**
     * AVP HTTP请求Token
     */
    String AVP_TOKEN = "epa:hvip:avp:http:token";

    /**
     * AVP 车控秘钥
     */
    String AVP_VEHICLE_SECRET = "epa:hvip:avp:vehicle:secret:";

    /**
     * AVP 场站秘钥
     */
    String AVP_STATION_SECRET = "epa:hvip:avp:station:secret:";
}
