package com.hycan.idn.hvip.pojo.avp.mqtt.down;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hycan.idn.hvip.pojo.avp.mqtt.AvpVinPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 取车服务应答下行对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AiParkOutDownDTO extends AvpVinPayload {

    /**
     * 停车场ID
     */
    private String pid;

    /**
     * 取车区域编号(预留字段, 适用于多个取车区场景, 用户选择将车辆调度到指定取车区, 单个取车区无需处理该值, 该号码需要与中心云对应)
     */
    @JsonProperty(value = "POIid")
    private String poiId;

    /**
     * 完成情况(true:泊入成功 false:泊入失败)
     */
    private Boolean result;

    /**
     * 如果不成功反馈error描述
     */
    private String error;
}
