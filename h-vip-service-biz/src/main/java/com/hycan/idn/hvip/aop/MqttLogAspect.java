package com.hycan.idn.hvip.aop;

import com.hycan.idn.hvip.entity.MqttAvpRecordEntity;
import com.hycan.idn.hvip.entity.MqttMpRecordEntity;
import com.hycan.idn.hvip.enums.MqttMsgTypeEnum;
import com.hycan.idn.hvip.mqtt.event.AvpMessageEvent;
import com.hycan.idn.hvip.mqtt.event.MpMessageEvent;
import com.hycan.idn.hvip.repository.MqttAvpRecordRepository;
import com.hycan.idn.hvip.repository.MqttMpRecordRepository;
import com.hycan.idn.hvip.util.JsonUtil;
import com.hycan.idn.tsp.common.core.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 记录MQTT日志切面，统一将与AVP、H-VIP小程序交互的MQTT消息，记录到数据库
 *
 * @author shichongying
 * @datetime 2023-10-19 15:52
 */
@Slf4j
@Aspect
@Component
public class MqttLogAspect {

    @Resource
    private MqttMpRecordRepository mqttMpRecordRepository;

    @Resource
    private MqttAvpRecordRepository mqttAvpRecordRepository;

    /**
     * 在MQTT消息进入业务逻辑之前，异步将消息记录到数据库中
     *
     * @param joinPoint 切入点
     * @param mqttLog   MQTT交互消息自定义注解
     */
    @Before("@annotation(mqttLog)")
    public void interceptor(JoinPoint joinPoint, MqttLog mqttLog) {
        Object[] args = joinPoint.getArgs();
        try {
            MqttMsgTypeEnum msgType = mqttLog.value();

            switch (msgType) {
                case MSG_TYPE_AVP:
                    AvpMessageEvent avpEvent = (AvpMessageEvent) args[0];
                    MqttAvpRecordEntity avpEntity = MqttAvpRecordEntity.of(avpEvent.getTopic(), JsonUtil.obj2Json(avpEvent.getMessage()));
                    mqttAvpRecordRepository.save(avpEntity);
                    break;
                case MSG_TYPE_MP:
                    MpMessageEvent mpEvent = (MpMessageEvent) args[0];
                    MqttMpRecordEntity mpEntity = MqttMpRecordEntity.of(mpEvent.getTopic(), JsonUtil.obj2Json(mpEvent.getMessage()));
                    mqttMpRecordRepository.save(mpEntity);
                    break;
                default :
                    log.warn("不支持的消息类型 {}", msgType);
            }
        } catch (Throwable t) {
            log.error("存储MQTT消息错误，异常详情={}", ExceptionUtil.getBriefStackTrace(t));
        }
    }
}