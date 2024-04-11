package com.hycan.idn.hvip.service.impl;

import com.hycan.idn.hvip.entity.StationBasicInfoEntity;
import com.hycan.idn.hvip.pojo.mp.http.PageVO;
import com.hycan.idn.hvip.pojo.mp.http.req.ListStationInfoReqVO;
import com.hycan.idn.hvip.pojo.mp.http.req.ListStationParkReqVO;
import com.hycan.idn.hvip.pojo.mp.http.rsp.StationInfoRspVO;
import com.hycan.idn.hvip.pojo.mp.http.rsp.StationMapRspVO;
import com.hycan.idn.hvip.pojo.mp.http.rsp.StationParkRspVO;
import com.hycan.idn.hvip.repository.StationBasicInfoRepository;
import com.hycan.idn.hvip.repository.StationMapInfoRepository;
import com.hycan.idn.hvip.repository.StationParkInfoRepository;
import com.hycan.idn.hvip.service.IStationService;
import com.hycan.idn.hvip.util.PositionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 场站相关业务Service实现
 *
 * @author shichongying
 * @datetime 2023-10-26 16:42
 */
@Slf4j
@Service
public class StationServiceImpl implements IStationService {

    @Resource
    private StationBasicInfoRepository stationBasicInfoRepository;

    @Resource
    private StationMapInfoRepository stationMapInfoRepository;

    @Resource
    private StationParkInfoRepository stationParkInfoRepository;

    @Override
    public PageVO<StationInfoRspVO> listStationInfo(ListStationInfoReqVO req) {
        Pageable pageable = PageRequest.of(req.getCurrent(), req.getSize());

        Page<StationInfoRspVO> page = stationBasicInfoRepository.findPage(pageable, req);
        page.getContent().forEach(vo -> {
            double distance = PositionUtil.getDistance(req.getLongitude(), req.getLatitude(), vo.getLongitude(), vo.getLatitude());
            vo.setDistance(Math.round(distance * 100) * 0.01);
        });
        return PageVO.of(page);
    }

    @Override
    public StationInfoRspVO showStationDetail(String stationCode) {
        StationBasicInfoEntity entity = stationBasicInfoRepository.findByStationCode(stationCode);

        // TODO 需要调用ECP接口，组装场站详情数据

        return null;
    }

    @Override
    public StationMapRspVO showStationMap(String stationCode) {
        return StationMapRspVO.of(stationMapInfoRepository.findByStationCode(stationCode));
    }

    @Override
    public List<StationParkRspVO> listStationPark(String stationCode, ListStationParkReqVO req) {
        return stationParkInfoRepository.selectList(stationCode, req);
    }
}