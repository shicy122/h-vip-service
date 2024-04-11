package com.hycan.idn.hvip.repository;

import com.hycan.idn.hvip.entity.StationMapInfoEntity;
import com.hycan.idn.hvip.pojo.avp.http.rsp.UrlsRspVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * 场站高精地图 Repository
 *
 * @author shichongying
 * @datetime 2023-10-23 10:45
 */
public interface StationMapInfoRepository extends JpaRepository<StationMapInfoEntity, String> {

    /**
     * 查询单个场站高精地图
     *
     * @param stationCode 场站编号
     * @return 场站高精地图Entity
     */
    StationMapInfoEntity findByStationCode(String stationCode);

    /**
     * 更新场站高精地图
     *
     * @param urls        场站地图数据
     * @param stationCode 场站编号
     */
    @Transactional
    @Modifying
    @Query("update StationMapInfoEntity smi set" +
            " smi.mapArrow = :#{#urls.parkingArrow}," +
            " smi.mapBoundary = :#{#urls.parkingBoundary}," +
            " smi.mapBump = :#{#urls.parkingBump}," +
            " smi.mapBumpLine = :#{#urls.parkingBumpLine}," +
            " smi.mapCenter = :#{#urls.parkingCenter}," +
            " smi.mapCenterLine = :#{#urls.parkingCenterLine}," +
            " smi.mapConnector = :#{#urls.parkingConnector}," +
            " smi.mapCrosswalk = :#{#urls.parkingCrosswalk}," +
            " smi.mapLane = :#{#urls.parkingLane}," +
            " smi.mapLink = :#{#urls.parkingLink}," +
            " smi.mapNode = :#{#urls.parkingNode}," +
            " smi.mapOutArea = :#{#urls.parkingOutArea}," +
            " smi.mapOutline = :#{#urls.parkingOutline}," +
            " smi.mapPillar = :#{#urls.parkingPillar}," +
            " smi.mapSpotLines = :#{#urls.parkingSpotLines}," +
            " smi.mapSpots = :#{#urls.parkingSpots}," +
            " smi.mapSurface = :#{#urls.parkingSurface}" +
            " where smi.stationCode = :stationCode")
    void updateStationMap(@Param("urls") UrlsRspVO urls, @Param("stationCode") String stationCode);
}
