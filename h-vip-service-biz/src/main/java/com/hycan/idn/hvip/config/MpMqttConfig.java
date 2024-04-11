package com.hycan.idn.hvip.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * H-VIP服务端连接BASE MQTT参数配置类
 *
 * @author shichongying
 * @datetime 2023-10-19 15:41
 */
@Data
@Slf4j
@Order(600)
@Configuration
@ConfigurationProperties(prefix = "hvip.mqtt.mp")
public class MpMqttConfig {

    /** 出站通道 */
    public static final String MP_OUTBOUND_CHANNEL = "mpMqttOutboundChannel";

    /** 输入通道 */
    public static final String MP_INPUT_CHANNEL = "mpMqttInputChannel";

    /** Qos 报文的服务质量等级，默认1(最少一次) */
    public static final Integer DEFAULT_QOS = 1;

    /** 连接MQTT Broker集群的用户名 */
    private String username;

    /** 订阅主题列表 */
    private String[] topics;

    /** MQTT Broker集群地址 */
    private String[] url;

    /** MQTT Broker HTTP 接口地址 */
    private String endpoint;

    /** 连接超时 */
    public Integer connectionTimeout = 60;

    /** 长连接最大时长为120秒 */
    public Integer keepAliveInterval = 120;

    /** 适配器消费消息超时时间60秒 */
    public Integer completionTimeOut = 60000;

    /** 控制适配器在失败后尝试重新连接的时间间隔 */
    public Integer recoveryInterval = 10000;

}
