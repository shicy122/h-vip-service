package com.hycan.idn.hvip.pojo.avp.mqtt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * AVP消息Header对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Builder
@Data
public class AvpHeader implements Serializable {

    /**
     * 数据发送的序号，每次+1，类型：uint32_t
     */
    private String seq;

    /**
     * unix时间戳，精确纳秒，类型：int64_t
     */
    private String time;

    /**
     * 发送者的名称，边缘云
     */
    private String sender;

    /**
     * 数据模型版本
     */
    @JsonProperty("model_version")
    private String modelVersion;
}