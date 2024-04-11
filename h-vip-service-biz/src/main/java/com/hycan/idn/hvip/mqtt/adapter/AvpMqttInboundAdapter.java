package com.hycan.idn.hvip.mqtt.adapter;

import com.hycan.idn.hvip.config.AvpMqttConfig;
import com.hycan.idn.hvip.mqtt.handler.AvpMqttMessageHandler;
import com.hycan.idn.tsp.common.core.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.annotation.Resource;

/**
 * AVP MQTT 订阅消息适配器
 *
 * @author shichongying
 * @datetime 2023-10-21 11:26
 */
@Slf4j
@Configuration
@IntegrationComponentScan
public class AvpMqttInboundAdapter {

    /**
     * 订阅者和发布者需要使用不同client_id，否则会报 Lost connection
     */
    public static final String SUB_CLIENT_ID = "AVP_HVIP_SUB_" + System.currentTimeMillis();

    @Resource
    private AvpMqttConfig mqttConfig;

    @Qualifier("mqttClientFactory")
    @Resource
    private MqttPahoClientFactory factory;

    /**
     * MQTT消息接收处理
     */
    @Bean
    public MessageChannel avpMqttInputChannel() {
        return new DirectChannel();
    }

    /**
     * 适配器，多个topic共用一个adapter
     * 客户端作为消费者，订阅主题、消费消息
     *
     * @return (name = MqttConfiguration.INPUT_CHANNEL)
     */
    @Bean
    public MessageProducerSupport avpInbound() {
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(
                SUB_CLIENT_ID, factory, mqttConfig.getTopics());
        DefaultPahoMessageConverter converter = new DefaultPahoMessageConverter();
        converter.setPayloadAsBytes(true);
        adapter.setConverter(converter);
        adapter.setQos(AvpMqttConfig.DEFAULT_QOS);
        adapter.setOutputChannel(avpMqttInputChannel());
        adapter.setRecoveryInterval(mqttConfig.getRecoveryInterval());
        adapter.setCompletionTimeout(mqttConfig.getCompletionTimeOut());
        return adapter;
    }

    /**
     * mqtt入站消息处理器
     * 对于指定消息入站通道接收到生产者生产的消息后处理消息的工具。
     */
    @Bean
    @ServiceActivator(inputChannel = AvpMqttConfig.AVP_INPUT_CHANNEL)
    public MessageHandler avpMessageHandler(AvpMqttMessageHandler mqttMessageHandler) {
        try {
            return mqttMessageHandler::handleMessage;
        } catch (Exception e) {
            log.error("处理消息失败, 异常详情={}", ExceptionUtil.getAllExceptionStackTrace(e));
        }
        return null;
    }
}
