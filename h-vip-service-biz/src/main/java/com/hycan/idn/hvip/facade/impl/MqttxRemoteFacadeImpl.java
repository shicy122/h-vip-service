package com.hycan.idn.hvip.facade.impl;

import com.hycan.idn.hvip.config.MpMqttConfig;
import com.hycan.idn.hvip.facade.IMqttxRemoteFacade;
import com.hycan.idn.hvip.pojo.vip.OkHttpResponseDTO;
import com.hycan.idn.hvip.util.OkHttpUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * MQTTX HTTP调用包装实现类
 *
 * @author shichongying
 * @datetime 2023-10-20 18:00
 */
@Slf4j
@Service
public class MqttxRemoteFacadeImpl implements IMqttxRemoteFacade {

    @SuppressWarnings("all")
    private static final String PWD_URI = "/api/v1/mqtt/encrypt/%s";

    @Resource
    private OkHttpUtil okHttpUtil;

    @Resource
    private MpMqttConfig mqttConfig;

    /**
     * 调用MQTTX接口获取密码，直到获取成功
     *
     * @return 密码
     */
    @Override
    public char[] searchEncryptPwd(String clientId) {
        while (true) {
            try {
                String uri = String.format(PWD_URI, clientId);
                Request request = new Request.Builder()
                        .get()
                        .url(mqttConfig.getEndpoint() + uri)
                        .build();
                OkHttpResponseDTO response = okHttpUtil.request(request);
                log.debug("search encrypt pwd response is {}", response.isSuccess());
                String pwd = response.getBody();
                if (StringUtils.isNotEmpty(pwd)) {
                    return pwd.toCharArray();
                }
            } catch (Exception e) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
