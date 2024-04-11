package com.hycan.idn.hvip.facade.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.hycan.idn.hvip.entity.OrderBasicInfoEntity;
import com.hycan.idn.hvip.entity.StationBasicInfoEntity;
import com.hycan.idn.hvip.entity.StationParkInfoEntity;
import com.hycan.idn.hvip.entity.VehicleBasicInfoEntity;
import com.hycan.idn.hvip.facade.ICacheFacade;
import com.hycan.idn.hvip.pojo.mp.http.rsp.VehicleParkRspVO;
import com.hycan.idn.hvip.repository.OrderBasicInfoRepository;
import com.hycan.idn.hvip.repository.StationBasicInfoRepository;
import com.hycan.idn.hvip.repository.StationParkInfoRepository;
import com.hycan.idn.hvip.repository.VehicleBasicInfoRepository;
import com.hycan.idn.hvip.repository.VehicleParkRecordRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 本地缓存包装实现类
 *
 * @author shichongying
 * @datetime 2023-10-20 17:49
 */
@Service
public class CacheFacadeImpl implements ICacheFacade {

    @Resource
    private StationBasicInfoRepository stationBasicInfoRepository;

    @Resource
    private OrderBasicInfoRepository orderBasicInfoRepository;

    @Resource
    private VehicleBasicInfoRepository vehicleBasicInfoRepository;

    @Resource
    private StationParkInfoRepository stationParkInfoRepository;

    @Resource
    private VehicleParkRecordRepository vehicleParkRecordRepository;

    // key:stationCode value:StationBasicInfoEntity
    private final Cache<String, StationBasicInfoEntity> stationInfoCache = Caffeine.newBuilder()
            .initialCapacity(20)
            .maximumSize(500)
            .expireAfterWrite(24, TimeUnit.HOURS)
            .build();

    // key:avpStationCode value:stationCode
    private final Cache<String, String> stationCodeCache = Caffeine.newBuilder()
            .initialCapacity(20)
            .maximumSize(500)
            .expireAfterWrite(24, TimeUnit.HOURS)
            .build();

    // key:orderCode value:OrderBasicInfoEntity
    private final Cache<String, OrderBasicInfoEntity> orderInfoCache = Caffeine.newBuilder()
            .initialCapacity(20)
            .maximumSize(5000)
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build();

    // key:vin value:VehicleBasicInfoEntity
    private final Cache<String, VehicleBasicInfoEntity> vehicleInfoCache = Caffeine.newBuilder()
            .initialCapacity(20)
            .maximumSize(500)
            .expireAfterWrite(24, TimeUnit.HOURS)
            .build();

    // key:plateNo value:vin
    private final Cache<String, String> vinCache = Caffeine.newBuilder()
            .initialCapacity(20)
            .maximumSize(5000)
            .expireAfterWrite(24, TimeUnit.HOURS)
            .build();

    // key:vin value:stationCode
    private final Cache<String, String> currentStationCodeCache = Caffeine.newBuilder()
            .initialCapacity(20)
            .maximumSize(5000)
            .expireAfterWrite(24, TimeUnit.HOURS)
            .build();

    @Scheduled(initialDelay = 1, fixedDelay = 1800, timeUnit = TimeUnit.SECONDS)
    private void initCacheSchedule() {
        List<StationBasicInfoEntity> stationEntities = stationBasicInfoRepository.findAll();
        if (!CollectionUtils.isEmpty(stationEntities)) {
            for (StationBasicInfoEntity entity : stationEntities) {
                String stationCode = entity.getStationCode();
                String avpStationCode = entity.getAvpStationCode();
                if (StringUtils.hasText(stationCode) || StringUtils.hasText(avpStationCode)) {
                    continue;
                }
                stationInfoCache.put(stationCode, entity);
                stationCodeCache.put(avpStationCode, stationCode);
            }
        }

        List<VehicleBasicInfoEntity> vehicleEntities = vehicleBasicInfoRepository.findAll();
        if (!CollectionUtils.isEmpty(vehicleEntities)) {
            for (VehicleBasicInfoEntity entity : vehicleEntities) {
                String vin = entity.getVin();
                if (!StringUtils.hasText(vin)) {
                    continue;
                }
                vehicleInfoCache.put(vin, entity);
                String plateNo = entity.getPlateNo();
                if (!StringUtils.hasText(plateNo)) {
                    continue;
                }
                vinCache.put(plateNo, vin);
            }
        }

        List<StationParkInfoEntity> stationParkEntities = stationParkInfoRepository.findAll();
        if (!CollectionUtils.isEmpty(stationParkEntities)) {
            for (StationParkInfoEntity entity : stationParkEntities) {
                String parkNo = entity.getParkNo();
                String avpParkNo = entity.getAvpParkNo();
                if (StringUtils.hasText(parkNo) || StringUtils.hasText(avpParkNo)) {
                    continue;
                }
            }
        }
    }

    private StationBasicInfoEntity getStationBasicInfoFromCache(String stationCode) {
        StationBasicInfoEntity entity = stationInfoCache.getIfPresent(stationCode);
        if (Objects.nonNull(entity)) {
            return entity;
        }

        entity = stationBasicInfoRepository.findByStationCode(stationCode);
        if (Objects.nonNull(entity)) {
            stationInfoCache.put(stationCode, entity);
        }
        return entity;
    }

    private OrderBasicInfoEntity getOrderBasicInfoFromCache(String orderCode) {
        OrderBasicInfoEntity entity = orderInfoCache.getIfPresent(orderCode);
        if (Objects.nonNull(entity)) {
            return entity;
        }

        entity = orderBasicInfoRepository.findByOrderCode(orderCode);
        if (Objects.nonNull(entity)) {
            orderInfoCache.put(orderCode, entity);
        }
        return entity;
    }

    private VehicleBasicInfoEntity getVehicleBasicInfoFromCache(String vin) {
        VehicleBasicInfoEntity entity = vehicleInfoCache.getIfPresent(vin);
        if (Objects.nonNull(entity)) {
            return entity;
        }

        entity = vehicleBasicInfoRepository.findByVin(vin);
        if (Objects.nonNull(entity)) {
            vehicleInfoCache.put(vin, entity);
        }
        return entity;
    }

    @Override
    public String getStationCodeByAvpStationCodeFromCache(String avpStationCode) {
        String stationCode = stationCodeCache.getIfPresent(avpStationCode);
        if (StringUtils.hasText(stationCode)) {
            return stationCode;
        }

        StationBasicInfoEntity entity = stationBasicInfoRepository.findByAvpStationCode(avpStationCode);
        if (Objects.nonNull(entity) && StringUtils.hasText(entity.getStationCode())) {
            stationCode = entity.getStationCode();
            stationCodeCache.put(avpStationCode, stationCode);

        }
        return stationCode;
    }

    @Override
    public String getAvpStationCodeByOrderCodeFromCache(String orderCode) {
        OrderBasicInfoEntity orderBasicInfoEntity = getOrderBasicInfoFromCache(orderCode);
        if (Objects.isNull(orderBasicInfoEntity) || !StringUtils.hasText(orderBasicInfoEntity.getStationCode())) {
            return null;
        }

        StationBasicInfoEntity stationBasicInfoEntity = getStationBasicInfoFromCache(orderBasicInfoEntity.getStationCode());
        if (Objects.isNull(stationBasicInfoEntity)) {
            return null;
        }
        return stationBasicInfoEntity.getAvpStationCode();
    }

    @Override
    public String getVinByOrderCodeFromCache(String orderCode) {
        OrderBasicInfoEntity orderBasicInfoEntity = getOrderBasicInfoFromCache(orderCode);
        if (Objects.isNull(orderBasicInfoEntity)) {
            return null;
        }
        return orderBasicInfoEntity.getVin();
    }

    @Override
    public String getPlateNoByVinFromCache(String vin) {
        VehicleBasicInfoEntity vehicleEntity = getVehicleBasicInfoFromCache(vin);
        if (Objects.isNull(vehicleEntity)) {
            return null;
        }
        return vehicleEntity.getPlateNo();
    }

    @Override
    public String getVinByPlateNoFromCache(String plateNo) {
        String vin = vinCache.getIfPresent(plateNo);
        if (StringUtils.hasText(vin)) {
            return vin;
        }

        VehicleBasicInfoEntity entity = vehicleBasicInfoRepository.findByPlateNo(plateNo);
        if (Objects.nonNull(entity) && !StringUtils.hasText(entity.getVin())) {
            vin = entity.getVin();
            vinCache.put(plateNo, vin);
        }
        return vin;
    }

    @Override
    public String getCurrentStationCodeByVinFromCache(String vin) {
        String currentStationCode = currentStationCodeCache.getIfPresent(vin);
        if (StringUtils.hasText(currentStationCode)) {
            return currentStationCode;
        }
        VehicleParkRspVO vehicleParkRsp = vehicleParkRecordRepository.selectVehicleCurrentPark(vin);
        if (Objects.nonNull(vehicleParkRsp) && StringUtils.hasText(vehicleParkRsp.getStationCode())) {
            currentStationCode = vehicleParkRsp.getStationCode();
            currentStationCodeCache.put(vin, currentStationCode);
        }
        return currentStationCode;
    }
}
