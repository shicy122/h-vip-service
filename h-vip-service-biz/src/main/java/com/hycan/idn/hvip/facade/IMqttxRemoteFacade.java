package com.hycan.idn.hvip.facade;

/**
 * MQTTX HTTP调用包装类
 *
 * @author shichongying
 * @datetime 2023-10-20 18:00
 */
public interface IMqttxRemoteFacade {

    /**
     * 获取连接MQTT所需的密码
     *
     * @param clientId 客户端ID
     * @return 密码
     */
    char[] searchEncryptPwd(String clientId);
}
