package com.hycan.idn.hvip.mqtt.adapter;

import com.hycan.idn.hvip.config.MpMqttConfig;
import com.hycan.idn.hvip.facade.IMqttxRemoteFacade;
import com.hycan.idn.hvip.mqtt.handler.MpMqttMessageHandler;
import com.hycan.idn.tsp.common.core.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.annotation.Resource;

/**
 * H-VIP小程序 MQTT 订阅消息适配器
 *
 * @author shichongying
 * @datetime 2023年 02月 26日 9:45
 */
@Slf4j
@Configuration
@IntegrationComponentScan
public class MpMqttInboundAdapter {

    /**
     * 订阅者和发布者需要使用不同client_id，否则会报 Lost connection
     */
    public static final String SUB_CLIENT_ID = "MP_HVIP_SUB_" + System.currentTimeMillis();

    @Resource
    private MpMqttConfig mqttConfig;

    @Resource
    private IMqttxRemoteFacade mqttxRemoteService;

    /**
     * MQTT消息接收处理
     */
    @Bean
    public MessageChannel mpMqttInputChannel() {
        return new DirectChannel();
    }

    /**
     * 适配器，多个topic共用一个adapter
     * 客户端作为消费者，订阅主题、消费消息
     *
     * @return (name = MqttConfiguration.INPUT_CHANNEL)
     */
    @Bean
    public MessageProducerSupport mpInbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                SUB_CLIENT_ID, mpMqttInboundClientFactory(), mqttConfig.getTopics());
        DefaultPahoMessageConverter converter = new DefaultPahoMessageConverter();
        converter.setPayloadAsBytes(true);
        adapter.setConverter(converter);
        adapter.setQos(MpMqttConfig.DEFAULT_QOS);
        adapter.setOutputChannel(mpMqttInputChannel());
        adapter.setRecoveryInterval(mqttConfig.getRecoveryInterval());
        adapter.setCompletionTimeout(mqttConfig.getCompletionTimeOut());
        return adapter;
    }

    /**
     * 注册MQTT客户端工厂，注意发布者和订阅者的客户端ID不能重复
     */
    @Bean
    public MqttPahoClientFactory mpMqttInboundClientFactory() {
        final DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        final MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setServerURIs(mqttConfig.getUrl());
        options.setUserName(mqttConfig.getUsername());
        options.setPassword(mqttxRemoteService.searchEncryptPwd(SUB_CLIENT_ID));
        options.setConnectionTimeout(mqttConfig.getConnectionTimeout());
        options.setKeepAliveInterval(mqttConfig.getKeepAliveInterval());
        options.setCleanSession(false);
        factory.setConnectionOptions(options);
        return factory;
    }

    /**
     * mqtt入站消息处理器
     * 对于指定消息入站通道接收到生产者生产的消息后处理消息的工具。
     */
    @Bean
    @ServiceActivator(inputChannel = MpMqttConfig.MP_INPUT_CHANNEL)
    public MessageHandler mpMessageHandler(MpMqttMessageHandler mqttMessageHandler) {
        try {
            return mqttMessageHandler::handleMessage;
        } catch (Exception e) {
            log.error("处理消息失败, 异常详情={}", ExceptionUtil.getAllExceptionStackTrace(e));
        }
        return null;
    }
}
