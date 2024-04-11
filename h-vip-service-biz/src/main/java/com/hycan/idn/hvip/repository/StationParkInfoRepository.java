package com.hycan.idn.hvip.repository;


import com.hycan.idn.hvip.entity.StationParkInfoEntity;
import com.hycan.idn.hvip.pojo.mp.http.req.ListStationParkReqVO;
import com.hycan.idn.hvip.pojo.mp.http.rsp.StationParkRspVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

/**
 * 场站车位信息 Repository
 *
 * @author shichongying
 * @datetime 2023-10-23 10:45
 */
public interface StationParkInfoRepository  extends JpaRepository<StationParkInfoEntity, String> {

    /**
     * 查询场站车位列表
     *
     * @param stationCode 场站编号
     * @param req         查询车位列表请求VO
     * @return 场站车位信息响应VO
     */
    @Query("select new com.hycan.idn.hvip.pojo.mp.http.rsp.StationParkRspVO(" +
            "spi.vin, spi.stationCode, spi.parkNo, spi.parkType, spi.parkStatus) " +
            "from StationParkInfoEntity spi " +
            "where (:stationCode is null or spi.stationCode = :stationCode) " +
            "and (:parkNo is null or spi.parkNo = :#{#req.parkNo}) " +
            "and (:parkType is null or spi.parkType = :#{#req.parkType}) " +
            "and (:parkStatus is null or spi.parkStatus = :#{#req.parkStatus}) " +
            "order by spi.parkNo")
    List<StationParkRspVO> selectList(String stationCode, ListStationParkReqVO req);

    /**
     * 查询AVP车位号与场站场站车位信息Entity，组装成MAP返回
     *
     * @param stationCode 场站编号
     * @return key:AVP车位号 value:场站场站车位信息Entity
     */
    List<StationParkInfoEntity> findAllByStationCode(String stationCode);

    /**
     * 查询场站空闲车位列表
     *
     * @param stationCode 场站编号
     * @param parkType    车位类型
     * @return 场站车位信息Entity
     */
    @Query("SELECT spi from StationParkInfoEntity spi " +
            "WHERE spi.stationCode = :stationCode AND spi.parkType = :parkType AND spi.parkStatus = 0")
    List<StationParkInfoEntity> selectIdleParkList(String stationCode, Integer parkType);
}
