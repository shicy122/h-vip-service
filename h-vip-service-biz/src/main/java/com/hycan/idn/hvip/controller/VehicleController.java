package com.hycan.idn.hvip.controller;

import com.hycan.idn.hvip.aop.HttpLog;
import com.hycan.idn.hvip.pojo.mp.http.rsp.VehicleInfoRspVO;
import com.hycan.idn.hvip.pojo.mp.http.rsp.VehicleParkRspVO;
import com.hycan.idn.hvip.service.IVehicleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 车辆相关接口
 *
 * @author shichongying
 * @datetime 2023-10-20 16:54
 */
@Validated
@Api(tags = "车辆管理")
@RestController
@RequestMapping(value = "/vip-svc/v1/vehicles")
public class VehicleController {

    @Resource
    private IVehicleService vehicleService;

    /**
     * 查询车辆信息
     *
     * @param vin VIN码
     * @return 车辆信息响应对象
     */
    @HttpLog
    @GetMapping(value = "/{vin}")
    @ApiOperation(value = "查询车辆信息")
    public ResponseEntity<VehicleInfoRspVO> listVehicles(@PathVariable("vin") String vin) {
        return ResponseEntity.ok(vehicleService.showVehicleInfo(vin));
    }

    /**
     * 查询车辆泊车信息
     *
     * @param vin VIN码
     * @return 车辆泊车信息响应对象
     */
    @HttpLog
    @GetMapping(value = "/{vin}/park")
    @ApiOperation(value = "查询车辆当前泊车信息")
    public ResponseEntity<VehicleParkRspVO> showVehiclePark(@PathVariable("vin") String vin) {
        return ResponseEntity.ok(vehicleService.showVehicleCurrentPark(vin));
    }
}