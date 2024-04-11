package com.hycan.idn.hvip.mqtt.adapter;

import com.hycan.idn.hvip.config.AvpMqttConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.annotation.Resource;

/**
 * AVP MQTT 发布消息适配器
 *
 * @author shichongying
 * @datetime 2023-10-21 11:30
 */
@Slf4j
@Configuration
public class AvpMqttOutboundAdapter {

    /**
     * 订阅者和发布者需要使用不同client_id，否则会报 Lost connection
     */
    public static final String PUB_CLIENT_ID = "AVP_HVIP_PUB_" + System.currentTimeMillis();

    @Qualifier("mqttClientFactory")
    @Resource
    private MqttPahoClientFactory factory;

    @Bean
    @ServiceActivator(inputChannel = AvpMqttConfig.AVP_OUTBOUND_CHANNEL)
    public MessageHandler avpMqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(PUB_CLIENT_ID, factory);
        messageHandler.setDefaultQos(1);
        //如果设置成true，发送消息时将不会阻塞。
        messageHandler.setAsync(true);
        // 消息发送和传输完成会有异步的通知回调
        messageHandler.setAsyncEvents(true);
        return messageHandler;
    }

    @Bean(name = AvpMqttConfig.AVP_OUTBOUND_CHANNEL)
    public MessageChannel avpMqttOutboundChannel() {
        return new DirectChannel();
    }
}
