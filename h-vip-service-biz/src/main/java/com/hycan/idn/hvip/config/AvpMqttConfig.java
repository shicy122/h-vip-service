package com.hycan.idn.hvip.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;

/**
 * H-VIP服务端连接AVP MQTT参数配置类
 *
 * @author shichongying
 * @datetime 2023-10-19 15:35
 */
@Data
@Slf4j
@Order(600)
@Configuration
@ConfigurationProperties(prefix = "hvip.mqtt.avp")
public class AvpMqttConfig {

    /** 出站通道 */
    public static final String AVP_OUTBOUND_CHANNEL = "avpMqttOutboundChannel";

    /** 输入通道 */
    public static final String AVP_INPUT_CHANNEL = "avpMqttInputChannel";

    /** Qos 报文的服务质量等级，默认1(最少一次) */
    public static final Integer DEFAULT_QOS = 1;

    /** 连接MQTT Broker集群的用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 订阅主题列表 */
    private String[] topics;

    /** MQTT Broker集群地址 */
    private String[] url;

    /** 连接超时 */
    public Integer connectionTimeout = 60;

    /** 长连接最大时长为120秒 */
    public Integer keepAliveInterval = 120;

    /** 适配器消费消息超时时间60秒 */
    public Integer completionTimeOut = 60000;

    /** 控制适配器在失败后尝试重新连接的时间间隔 */
    public Integer recoveryInterval = 10000;

    /**
     * 注册MQTT客户端工厂
     */
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        final DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        final MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setConnectionTimeout(connectionTimeout);
        // 设置长连接最大时长为120秒
        options.setKeepAliveInterval(keepAliveInterval);
        options.setAutomaticReconnect(true);
        options.setUserName(this.getUsername());
        options.setPassword(this.getPassword().toCharArray());
        options.setServerURIs(this.getUrl());
        factory.setConnectionOptions(options);
        return factory;
    }
}
