package com.hycan.idn.hvip.job;

import com.hycan.idn.hvip.constants.StationConstants;
import com.hycan.idn.hvip.entity.StationBasicInfoEntity;
import com.hycan.idn.hvip.enums.AvpMqttTopicEnum;
import com.hycan.idn.hvip.facade.IAvpRemoteFacade;
import com.hycan.idn.hvip.facade.ICacheFacade;
import com.hycan.idn.hvip.facade.IMqttMessageFacade;
import com.hycan.idn.hvip.pojo.avp.http.rsp.ParkingFacNewReleaseRspVO;
import com.hycan.idn.hvip.pojo.avp.http.rsp.StationMapRsp;
import com.hycan.idn.hvip.pojo.avp.http.rsp.StationRspVO;
import com.hycan.idn.hvip.pojo.avp.http.rsp.VersionRspVO;
import com.hycan.idn.hvip.pojo.avp.mqtt.up.ParkingSpaceStatusUpDTO;
import com.hycan.idn.hvip.repository.StationBasicInfoRepository;
import com.hycan.idn.hvip.repository.StationMapInfoRepository;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 初始化场站信息定时任务
 *
 * @author shichongying
 * @datetime 2023-10-21 11:23
 */
@Slf4j
@Component
public class InitStationInfoJob {

    @Resource
    private StationMapInfoRepository stationMapInfoRepository;

    @Resource
    private StationBasicInfoRepository stationBasicInfoRepository;

    @Resource
    private IMqttMessageFacade mqttMessageFacade;

    @Resource
    private IAvpRemoteFacade avpRemoteFacade;

    @Resource
    private ICacheFacade cacheFacade;

    @PostConstruct
    public void init() {
        initStationInfoHandler();
    }

    /**
     * 1.更新场站秘钥和状态
     * 2.更新场站高精地图
     * 3.发送获取场站车位MQTT请求
     */
    @XxlJob("initStationInfoHandler")
    public void initStationInfoHandler() {
        // 请求所有停车场列表状态（更新场站状态）
        List<StationRspVO> parkingList = avpRemoteFacade.getStationList();
        if (CollectionUtils.isEmpty(parkingList)) {
            return;
        }

        Map<String, StationBasicInfoEntity> stationMap = getBasicInfoEntityMap();
        if (CollectionUtils.isEmpty(stationMap)) {
            return;
        }

        List<VersionRspVO> parkingFacVersionList = new ArrayList<>();
        for (StationRspVO resp : parkingList) {
            String avpStationCode = resp.getVersion().getParkingFacId();

            // 1.判断是否为H-VIP场站
            StationBasicInfoEntity stationBasicInfoEntity = stationMap.get(avpStationCode);
            if (Objects.isNull(stationBasicInfoEntity)) {
                continue;
            }
            parkingFacVersionList.add(resp.getVersion());

            // 2.更新场站状态
            stationBasicInfoEntity.setStationStatus(Boolean.TRUE.equals(resp.getIsOnline()) ?
                    StationConstants.STATION_STATUS_ONLINE : StationConstants.STATION_STATUS_OFFLINE);
            stationBasicInfoRepository.save(stationBasicInfoEntity);

            // 3.发送获取场站车位MQTT消息
            mqttMessageFacade.sendMqttMessage(AvpMqttTopicEnum.AVP_UP_TOPIC_PARKING_SPACE_STATUS,
                    ParkingSpaceStatusUpDTO.of(avpStationCode));
        }

        // 更新场站高精地图
        updateStationMap(parkingFacVersionList);
    }

    private void updateStationMap(List<VersionRspVO> parkingFacVersionList) {
        StationMapRsp stationMapRsp = avpRemoteFacade.getStationMap(parkingFacVersionList);
        if (Objects.isNull(stationMapRsp) || CollectionUtils.isEmpty(stationMapRsp.getParkingFacNewReleaseList())) {
            return;
        }

        for (ParkingFacNewReleaseRspVO resp : stationMapRsp.getParkingFacNewReleaseList()) {
            String avpStationCode = resp.getNewVersion().getParkingFacId();
            String stationCode = cacheFacade.getStationCodeByAvpStationCodeFromCache(avpStationCode);
            if (Objects.nonNull(resp.getUrls())) {
                stationMapInfoRepository.updateStationMap(resp.getUrls(), stationCode);
            }
        }
    }

    private Map<String, StationBasicInfoEntity> getBasicInfoEntityMap() {
        List<StationBasicInfoEntity> stationBasicInfoEntityList = stationBasicInfoRepository.findAll();
        if (CollectionUtils.isEmpty(stationBasicInfoEntityList)) {
            return null;
        }

        return stationBasicInfoEntityList.stream()
                .collect(Collectors.toMap(StationBasicInfoEntity::getAvpStationCode, station -> station ));
    }
}
