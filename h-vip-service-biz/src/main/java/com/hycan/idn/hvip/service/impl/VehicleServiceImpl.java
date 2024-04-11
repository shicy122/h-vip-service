package com.hycan.idn.hvip.service.impl;

import com.hycan.idn.hvip.entity.VehicleBasicInfoEntity;
import com.hycan.idn.hvip.pojo.mp.http.rsp.VehicleInfoRspVO;
import com.hycan.idn.hvip.pojo.mp.http.rsp.VehicleParkRspVO;
import com.hycan.idn.hvip.repository.VehicleBasicInfoRepository;
import com.hycan.idn.hvip.repository.VehicleParkRecordRepository;
import com.hycan.idn.hvip.service.IVehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 车辆相关业务Service实现
 *
 * @author shichongying
 * @datetime 2023-10-26 16:42
 */
@Slf4j
@Service
public class VehicleServiceImpl implements IVehicleService {

    @Resource
    private VehicleBasicInfoRepository vehicleBasicInfoRepository;

    @Resource
    private VehicleParkRecordRepository vehicleParkRecordRepository;

    @Override
    public VehicleInfoRspVO showVehicleInfo(String vin) {
        // TODO 数据库中如果不存在车辆信息，则需要调用VMS接口，查询车辆基础信息和AVP能力
        VehicleBasicInfoEntity entity = vehicleBasicInfoRepository.findByVin(vin);
        return VehicleInfoRspVO.of(entity);

    }

    @Override
    public VehicleParkRspVO showVehicleCurrentPark(String vin) {
        return vehicleParkRecordRepository.selectVehicleCurrentPark(vin);
    }
}
