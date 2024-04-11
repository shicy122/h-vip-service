package com.hycan.idn.hvip.mqtt.listener;

import com.hycan.idn.hvip.facade.IMqttxRemoteFacade;
import com.hycan.idn.hvip.mqtt.adapter.MpMqttInboundAdapter;
import com.hycan.idn.hvip.mqtt.adapter.MpMqttOutboundAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.integration.mqtt.event.MqttConnectionFailedEvent;
import org.springframework.integration.mqtt.event.MqttProtocolErrorEvent;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * MQTT Broker回调监听器
 *
 * @author shichongying
 * @datetime 2023-10-21 11:51
 */
@Slf4j
@Component
public class MqttCallbackListener {

    private static final String BEAN_NAME_MP_IN_BOUND = "mpInbound";
    private static final String BEAN_NAME_MP_OUT_BOUND = "mpMqttOutbound";

    @Resource
    private IMqttxRemoteFacade mqttxRemoteService;

    /**
     * mqtt连接失败或者订阅失败时, 触发MqttConnectionFailedEvent事件
     * beanName为mpInbound/mpMqttOutbound则重新向MQTTX发送获取密码请求，更新连接认证密码
     */
    @EventListener(MqttConnectionFailedEvent.class)
    public void mqttConnectionFailedEvent(MqttConnectionFailedEvent event) {
        log.error("MQTT连接错误: 发生时间=[{}], 错误源=[{}], 错误详情=[{}]", LocalDateTime.now(), event.getSource(), event.getCause().getMessage());
        if (event.getSource() instanceof MqttPahoMessageHandler) {
            MqttPahoMessageHandler handler = (MqttPahoMessageHandler) event.getSource();
            if (BEAN_NAME_MP_OUT_BOUND.equals(handler.getBeanName())) {
                char[] pubPwd = mqttxRemoteService.searchEncryptPwd(MpMqttOutboundAdapter.PUB_CLIENT_ID);
                handler.getConnectionInfo().setPassword(pubPwd);
            }
        } else if (event.getSource() instanceof MqttPahoMessageDrivenChannelAdapter) {
            MqttPahoMessageDrivenChannelAdapter adapter = (MqttPahoMessageDrivenChannelAdapter) event.getSource();
            if (BEAN_NAME_MP_IN_BOUND.equals(adapter.getBeanName())) {
                char[] subPwd = mqttxRemoteService.searchEncryptPwd(MpMqttInboundAdapter.SUB_CLIENT_ID);
                adapter.getConnectionInfo().setPassword(subPwd);
            }
        }
    }

    /**
     * 客户端交互期间发生 MQTT 错误
     */
    @EventListener(MqttProtocolErrorEvent.class)
    public void mqttProtocolErrorEvent(MqttProtocolErrorEvent event) {
        log.error("MQTT交互错误: 发生时间=[{}], 错误源=[{}], 错误详情=[{}]", LocalDateTime.now(), event.getSource(), event.getCause().getMessage());
    }
}
