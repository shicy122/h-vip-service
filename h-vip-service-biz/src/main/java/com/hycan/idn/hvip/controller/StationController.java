package com.hycan.idn.hvip.controller;

import com.hycan.idn.hvip.aop.HttpLog;
import com.hycan.idn.hvip.pojo.mp.http.PageVO;
import com.hycan.idn.hvip.pojo.mp.http.req.ListStationInfoReqVO;
import com.hycan.idn.hvip.pojo.mp.http.req.ListStationParkReqVO;
import com.hycan.idn.hvip.pojo.mp.http.rsp.StationInfoRspVO;
import com.hycan.idn.hvip.pojo.mp.http.rsp.StationMapRspVO;
import com.hycan.idn.hvip.pojo.mp.http.rsp.StationParkRspVO;
import com.hycan.idn.hvip.service.IStationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 场站相关接口
 *
 * @author shichongying
 * @datetime 2023-10-20 16:53
 */
@Validated
@Api(tags = "场站管理")
@RestController
@RequestMapping(value = "/vip-svc/v1/stations")
public class StationController {

    @Resource
    private IStationService stationService;

    /**
     * 查询场站列表
     *
     * @param req 分页查询场站列表请求对象
     * @return 分页查询场站列表响应对象
     */
    @HttpLog
    @GetMapping
    @ApiOperation(value = "查询场站列表")
    public ResponseEntity<PageVO<StationInfoRspVO>> listStationInfo(ListStationInfoReqVO req) {
        return ResponseEntity.ok(stationService.listStationInfo(req));
    }

    /**
     * 查询场站详情
     *
     * @param stationCode 场站编号
     * @return 场站详情响应对象
     */
    @HttpLog
    @GetMapping(value = "/{station_code}/detail")
    @ApiOperation(value = "查询场站详情")
    public ResponseEntity<StationInfoRspVO> showStationDetail(@PathVariable("station_code") String stationCode) {
        return ResponseEntity.ok(stationService.showStationDetail(stationCode));
    }

    /**
     * 查询高精地图
     *
     * @param stationCode 场站编号
     * @return 高精地图响应对象
     */
    @HttpLog
    @GetMapping(value = "/{station_code}/map")
    @ApiOperation(value = "查询高精地图")
    public ResponseEntity<StationMapRspVO> showStationMap(@PathVariable("station_code") String stationCode) {
        return ResponseEntity.ok(stationService.showStationMap(stationCode));
    }

    /**
     * 查询场站车位列表
     *
     * @param stationCode        场站编号
     * @param listStationParkReqVO 查询场站车位列表请求对象
     * @return 查询场站车位列表响应对象
     */
    @HttpLog
    @GetMapping(value = "/{station_code}/parks")
    @ApiOperation(value = "查询场站车位列表")
    public ResponseEntity<List<StationParkRspVO>> listStationPark(
            @PathVariable("station_code") String stationCode, ListStationParkReqVO listStationParkReqVO) {
        return ResponseEntity.ok(stationService.listStationPark(stationCode, listStationParkReqVO));
    }
}