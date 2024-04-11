package com.hycan.idn.hvip.service;

import com.hycan.idn.hvip.pojo.mp.http.PageVO;
import com.hycan.idn.hvip.pojo.mp.http.req.ListStationInfoReqVO;
import com.hycan.idn.hvip.pojo.mp.http.req.ListStationParkReqVO;
import com.hycan.idn.hvip.pojo.mp.http.rsp.StationInfoRspVO;
import com.hycan.idn.hvip.pojo.mp.http.rsp.StationMapRspVO;
import com.hycan.idn.hvip.pojo.mp.http.rsp.StationParkRspVO;

import java.util.List;

/**
 * 场站相关业务Service
 *
 * @author shichongying
 * @datetime 2023-10-26 17:11
 */
public interface IStationService {

    /**
     * 分页查询场站列表
     *
     * @param req 分页查询场站列表请求VO
     * @return 场站列表分页VO
     */
    PageVO<StationInfoRspVO> listStationInfo(ListStationInfoReqVO req);

    /**
     * 查询场站详情
     *
     * @param stationCode 场站编号
     * @return 场站信息响应VO
     */
    StationInfoRspVO showStationDetail(String stationCode);

    /**
     * 查询场站高精地图
     *
     * @param stationCode 场站编号
     * @return 场站高精地图响应VO
     */
    StationMapRspVO showStationMap(String stationCode);

    /**
     * 查询停车位列表
     *
     * @param stationCode 场站编号
     * @param req         查询车位列表请求VO
     * @return 场站车位列表响应VO
     */
    List<StationParkRspVO> listStationPark(String stationCode, ListStationParkReqVO req);
}