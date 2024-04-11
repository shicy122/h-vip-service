package com.hycan.idn.hvip.pojo.avp.mqtt.down;

import lombok.Data;

/**
 * 新路线路径坐标
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
public class NewRoutPathDTO {

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
