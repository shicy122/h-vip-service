package com.hycan.idn.hvip.repository;

import com.hycan.idn.hvip.entity.OrderBasicInfoEntity;
import com.hycan.idn.hvip.pojo.mp.http.req.ListOrderInfoReqVO;
import com.hycan.idn.hvip.pojo.mp.http.rsp.OrderInfoRspVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 工单基础信息 Repository
 *
 * @author shichongying
 * @datetime 2023-10-23 10:45
 */
@Repository
public interface OrderBasicInfoRepository extends JpaRepository<OrderBasicInfoEntity, String> {

    /**
     * 查询单条工单基础信息
     *
     * @param orderCode 工单编号
     * @return 工单基础信息Entity
     */
    OrderBasicInfoEntity findByOrderCode(String orderCode);

    /**
     * 分页查询工单基础信息
     *
     * @param req 分页查询工单列表请求对象
     * @return 工单基础信息响应VO分页数据
     */
    @Query("SELECT new com.hycan.idn.hvip.pojo.mp.http.rsp.OrderInfoRspVO(o.orderCode, " +
            "o.stationCode, o.vin, o.itemList, o.orderStatus, o.startTime, o.endTime) " +
            "FROM OrderBasicInfoEntity o WHERE (o.uid = :uid) " +
            "AND (:#{#req.vin} IS NULL OR o.vin = :#{#req.vin}) " +
            "AND (:#{#req.orderStatus} IS NULL OR o.orderStatus = :#{#req.orderStatus}) " +
            "AND (:#{#req.startTime} IS NULL OR o.startTime >= :#{#req.startTime}) " +
            "AND (:#{#req.endTime} IS NULL OR o.endTime >= :#{#req.endTime}) " +
            "ORDER BY o.startTime DESC")
    Page<OrderInfoRspVO> findPage(Pageable pageable, @Param("req") ListOrderInfoReqVO req, @Param("uid") Long uid);

    /**
     * 查询【状态 != 2】的工单基础信息
     * 状态值含义: 0:末开始 1:进行中 2:已完成 3:等待取车 4:取车中 5:取车完成
     *
     * @param stationCode 场站编号
     * @param vin         VIN码
     * @return 工单基础信息Entity
     */
    @Query("SELECT obi FROM OrderBasicInfoEntity obi WHERE obi.stationCode = :stationCode " +
            "AND obi.vin = :vin AND obi.orderStatus IN (0, 1, 3, 4, 5)")
    OrderBasicInfoEntity selectUnfinishedOrder(String stationCode, String vin);
}