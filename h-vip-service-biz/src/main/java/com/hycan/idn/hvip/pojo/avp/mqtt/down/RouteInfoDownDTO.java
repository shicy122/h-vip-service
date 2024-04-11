package com.hycan.idn.hvip.pojo.avp.mqtt.down;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hycan.idn.hvip.pojo.avp.mqtt.AvpVinPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 路线信息下行对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RouteInfoDownDTO extends AvpVinPayload {

    /**
     * 总时间(单位: 秒)
     */
    @JsonProperty(value = "total_time")
    private Integer totalTime;

    /**
     * 总距离(单位: 米)
     */
    @JsonProperty(value = "total_distance")
    private Integer totalDistance;

    /**
     * 剩余时间(单位: 秒)
     */
    @JsonProperty(value = "remain_time")
    private Integer remainTime;

    /**
     * 剩余距离(单位: 米)
     */
    @JsonProperty(value = "remain_distance")
    private Integer remainDistance;

    /**
     * 当前最近的路径点的ID，用于显示走过的和没走过的道路
     */
    private Integer index;
}
