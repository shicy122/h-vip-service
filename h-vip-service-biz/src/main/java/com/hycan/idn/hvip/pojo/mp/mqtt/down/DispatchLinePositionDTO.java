package com.hycan.idn.hvip.pojo.mp.mqtt.down;

import lombok.Data;

/**
 * 调度路线经纬度坐标
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
public class DispatchLinePositionDTO {

    /**
     * 经度
     */
    private Double latitude;

    /**
     * 纬度
     */
    private Double longitude;

    /**
     * 海拔(单位: 米)
     */
    private Double alt;
}
