package com.hycan.idn.hvip.facade.impl;

import com.hycan.idn.hvip.enums.AvpMqttTopicEnum;
import com.hycan.idn.hvip.enums.MpMqttTopicEnum;
import com.hycan.idn.hvip.facade.IAvpRemoteFacade;
import com.hycan.idn.hvip.facade.IMqttMessageFacade;
import com.hycan.idn.hvip.mqtt.handler.AvpMqttMessageHandler;
import com.hycan.idn.hvip.mqtt.handler.MpMqttMessageHandler;
import com.hycan.idn.hvip.pojo.avp.mqtt.AvpPidPayload;
import com.hycan.idn.hvip.pojo.avp.mqtt.AvpVinPayload;
import com.hycan.idn.hvip.pojo.mp.mqtt.MpPayload;
import com.hycan.idn.hvip.facade.ICacheFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * MQTT消息包装实现类
 *
 * @author shichongying
 * @datetime 2023-10-20 17:50
 */
@Slf4j
@Service
public class MqttMessageFacadeImpl implements IMqttMessageFacade {

    @Resource
    private IAvpRemoteFacade avpRemoteFacade;

    @Resource
    private AvpMqttMessageHandler avpHandler;

    @Resource
    private MpMqttMessageHandler mpHandler;

    @Resource
    private ICacheFacade cacheFacade;

    @Override
    public void sendMqttMessage(AvpMqttTopicEnum avpMqttTopicEnum, AvpPidPayload payload) {
        if (Objects.isNull(avpMqttTopicEnum) || Objects.isNull(payload)) {
            log.error("AVP MQTT参数错误! Topic=[{}], Payload=[{}]", avpMqttTopicEnum, payload);
            return;
        }

        String avpStationCode = payload.getPid();
        String pKey = avpRemoteFacade.getOrUpdateStationSecret(avpStationCode);
        if (StringUtils.isEmpty(pKey)) {
            log.error("场站秘钥不存在, AVP MQTT消息发送失败! AVP场站编号={}", avpStationCode);
            return;
        }

        String topic = String.format(avpMqttTopicEnum.getTopic(), avpStationCode, pKey);
        if (StringUtils.isEmpty(topic)) {
            log.error("AVP MQTT发布Topic为空! Topic枚举=[{}], AVP场站编号=[{}]", avpMqttTopicEnum.getTopic(), avpStationCode);
            return;
        }
        avpHandler.handleMessage(topic, payload);
    }

    @Override
    public void sendMqttMessage(AvpMqttTopicEnum avpMqttTopicEnum, AvpVinPayload payload) {
        if (Objects.isNull(avpMqttTopicEnum) || Objects.isNull(payload)) {
            log.error("AVP MQTT参数错误! Topic=[{}], Payload=[{}]", avpMqttTopicEnum, payload);
            return;
        }

        String vin = payload.getVin();
        String vKey = avpRemoteFacade.getOrUpdateVehicleSecret(vin);
        if (StringUtils.isEmpty(vKey)) {
            log.error("车控秘钥不存在, AVP MQTT消息发送失败! VIN码={}", vin);
            return;
        }

        String topic = String.format(avpMqttTopicEnum.getTopic(), vin, vKey);
        if (StringUtils.isEmpty(topic)) {
            log.error("AVP MQTT发布Topic为空! Topic枚举=[{}], VIN码=[{}]", avpMqttTopicEnum.getTopic(), vin);
            return;
        }

        avpHandler.handleMessage(topic, payload);
    }

    @Override
    public void sendMqttMessage(MpMqttTopicEnum mpMqttTopicEnum, MpPayload payload) {
        if (Objects.isNull(mpMqttTopicEnum) || Objects.isNull(payload)) {
            log.error("MP MQTT参数错误! Topic=[{}], Payload=[{}]", mpMqttTopicEnum, payload);
            return;
        }

        String vin = cacheFacade.getVinByPlateNoFromCache(payload.getPlateNo());
        if (StringUtils.isEmpty(vin)) {
            log.error("根据车牌号查询VIN为空! 车牌号=[{}]", payload.getPlateNo());
            return;
        }

        String topic = String.format(mpMqttTopicEnum.getTopic(), vin);
        if (StringUtils.isEmpty(topic)) {
            log.error("MP MQTT发布Topic为空! Topic枚举=[{}], VIN=[{}]", mpMqttTopicEnum.getTopic(), vin);
            return;
        }

        mpHandler.handleMessage(topic, payload);
    }
}
