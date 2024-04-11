package com.hycan.idn.hvip.service;

import com.hycan.idn.hvip.pojo.mp.http.rsp.VehicleInfoRspVO;
import com.hycan.idn.hvip.pojo.mp.http.rsp.VehicleParkRspVO;

/**
 * 车辆相关业务Service
 *
 * @author shichongying
 * @datetime 2023-10-26 17:11
 */
public interface IVehicleService {

    /**
     * 查询车辆信息
     *
     * @param vin VIN码
     * @return 车辆信息响应VO
     */
    VehicleInfoRspVO showVehicleInfo(String vin);

    /**
     * 查询场站车辆列表
     *
     * @param vin VIN码
     * @return 车辆泊车信息响应VO
     */
    VehicleParkRspVO showVehicleCurrentPark(String vin);
}
