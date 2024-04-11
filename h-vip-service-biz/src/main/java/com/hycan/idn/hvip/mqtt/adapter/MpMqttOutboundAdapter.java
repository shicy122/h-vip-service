package com.hycan.idn.hvip.mqtt.adapter;

import com.hycan.idn.hvip.config.MpMqttConfig;
import com.hycan.idn.hvip.facade.IMqttxRemoteFacade;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.annotation.Resource;

/**
 * H-VIP小程序 MQTT 发布消息适配器
 *
 * @author shichongying
 * @datetime 2023-10-21 11:35
 */
@Slf4j
@Configuration
public class MpMqttOutboundAdapter {

    /**
     * 订阅者和发布者需要使用不同client_id，否则会报 Lost connection
     */
    public static final String PUB_CLIENT_ID = "MP_HVIP_PUB_" + System.currentTimeMillis();

    @Resource
    private MpMqttConfig mqttConfig;

    @Resource
    private IMqttxRemoteFacade mqttxRemoteService;

    @Bean
    @ServiceActivator(inputChannel = MpMqttConfig.MP_OUTBOUND_CHANNEL)
    public MessageHandler mpMqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(PUB_CLIENT_ID, mpMqttOutboundClientFactory());
        messageHandler.setDefaultQos(1);
        //如果设置成true，发送消息时将不会阻塞。
        messageHandler.setAsync(true);
        // 消息发送和传输完成会有异步的通知回调
        messageHandler.setAsyncEvents(true);
        return messageHandler;
    }

    @Bean(name = MpMqttConfig.MP_OUTBOUND_CHANNEL)
    public MessageChannel mpMqttOutboundChannel() {
        return new DirectChannel();
    }

    /**
     * 注册MQTT客户端工厂, 注意发布者和订阅者的客户端ID不能重复
     */
    @Bean
    public MqttPahoClientFactory mpMqttOutboundClientFactory() {
        final DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        final MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setAutomaticReconnect(true);
        options.setServerURIs(mqttConfig.getUrl());
        options.setUserName(mqttConfig.getUsername());
        options.setPassword(mqttxRemoteService.searchEncryptPwd(PUB_CLIENT_ID));
        options.setConnectionTimeout(mqttConfig.getConnectionTimeout());
        options.setKeepAliveInterval(mqttConfig.getKeepAliveInterval());
        factory.setConnectionOptions(options);
        return factory;
    }
}
