package com.hycan.idn.hvip.constants;

/**
 * AVP URI相关常量
 *
 * @author shichongying
 * @datetime 2023-10-19 19:31
 */
public interface AvpUriConstants {

    /**
     * 获取AVP的Token
     */
    @SuppressWarnings("all")
    String AVP_TOKEN = "/oauth/user/login";

    /**
     * 查询所有停车场列表
     */
    String AVP_PARKING_LIST = "/map/v1/parking-fac/all-list";

    /**
     * 查询停车场秘钥
     */
    String AVP_PARKING_KEY = "/ums/api/v1/ums/parkinglot/key/";

    /**
     * 查询停车场高精地图
     */
    String AVP_PARKING_MAP = "/map/v1/parking-fac/new-release";

    /**
     * 查询车辆秘钥
     */
    String AVP_UMS_DEVICE = "/ums/api/v1/ums/device/";
}
