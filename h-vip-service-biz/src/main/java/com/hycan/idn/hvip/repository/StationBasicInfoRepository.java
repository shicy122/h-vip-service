package com.hycan.idn.hvip.repository;

import com.hycan.idn.hvip.entity.StationBasicInfoEntity;
import com.hycan.idn.hvip.pojo.mp.http.req.ListStationInfoReqVO;
import com.hycan.idn.hvip.pojo.mp.http.rsp.StationInfoRspVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 场站基础信息 Repository
 *
 * @author shichongying
 * @datetime 2023-10-23 10:45
 */
@Repository
public interface StationBasicInfoRepository extends JpaRepository<StationBasicInfoEntity, String>, JpaSpecificationExecutor<StationBasicInfoEntity> {

    /**
     * 分页查询场站基础信息列表
     *
     * @param req 分页查询场站列表请求对象
     * @return 场站基础信息响应VO分页数据
     */
    @Query(value = "SELECT new com.hycan.idn.hvip.pojo.mp.http.rsp.StationInfoRspVO(" +
            "sbi.stationCode, sbi.stationName, sbi.stationStatus, sbi.stationAddress, " +
            "sbi.stationLabel, sbi.longitude, sbi.latitude, sbi.itemList) " +
            "FROM StationBasicInfoEntity sbi WHERE 1=1 " +
            "AND (:#{#req.stationName} IS NULL OR sbi.stationName=:#{#req.stationName}) " +
            "AND (:#{#req.stationCode} IS NULL OR sbi.stationCode=:#{#req.stationCode}) " +
            "AND (:#{#req.stationStatus} IS NULL OR sbi.stationStatus=:#{#req.stationStatus}) " +
            "ORDER BY EARTH_DISTANCE(LL_TO_EARTH(:#{#req.latitude}, :#{#req.longitude}), LL_TO_EARTH(sbi.latitude, sbi.longitude))")
    Page<StationInfoRspVO> findPage(Pageable pageable, @Param("req") ListStationInfoReqVO req);

    /**
     * 按场站编号，查询单条场站基础信息
     *
     * @param stationCode 场站编号
     * @return 场站基础信息Entity
     */
    StationBasicInfoEntity findByStationCode(String stationCode);

    /**
     * 按AVP场站编号，查询单条场站基础信息
     *
     * @param avpStationCode AVP场站编号
     * @return H-VIP场站编号
     */
    StationBasicInfoEntity findByAvpStationCode(String avpStationCode);

    /**
     * 按AVP场站编号，更新场站秘钥
     *
     * @param avpStationCode AVP场站编号
     * @param secret         场站秘钥
     * @return true/false
     */
    @Transactional
    @Modifying
    @Query("UPDATE StationBasicInfoEntity sbi SET sbi.stationKey = :secret WHERE sbi.avpStationCode = :avpStationCode")
    int updateStationSecret(@Param("avpStationCode") String avpStationCode, @Param("secret") String secret);

    /**
     * 按AVP场站编号，更新场站状态
     *
     * @param avpStationCode AVP场站编号
     * @param status         场站状态
     * @return true/false
     */
    @Transactional
    @Modifying
    @Query("UPDATE StationBasicInfoEntity sbi SET sbi.stationStatus = :status WHERE sbi.avpStationCode = :avpStationCode")
    int updateStationStatus(@Param("avpStationCode") String avpStationCode, @Param("status") int status);
}