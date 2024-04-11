package com.hycan.idn.hvip.facade;

import com.hycan.idn.hvip.pojo.avp.http.rsp.StationMapRsp;
import com.hycan.idn.hvip.pojo.avp.http.rsp.StationRspVO;
import com.hycan.idn.hvip.pojo.avp.http.rsp.VersionRspVO;

import java.util.List;

/**
 * AVP HTTP接口包装类
 *
 * @author shichongying
 * @datetime 2023-10-20 17:40
 */
public interface IAvpRemoteFacade {

    /**
     * 查询场站秘钥
     *
     * @param avpStationCode AVP场站编号
     * @return 场站秘钥
     */
    String getOrUpdateStationSecret(String avpStationCode);

    /**
     * 查询车辆秘钥
     *
     * @param vin VIN码
     * @return 车辆秘钥
     */
    String getOrUpdateVehicleSecret(String vin);

    /**
     * 获取场站列表
     *
     * @return 场站列表
     */
    List<StationRspVO> getStationList();

    /**
     * 获取场站高精地图
     *
     * @param parkingFacVersionList 场站列表
     * @return 高精地图
     */
    StationMapRsp getStationMap(List<VersionRspVO> parkingFacVersionList);
}