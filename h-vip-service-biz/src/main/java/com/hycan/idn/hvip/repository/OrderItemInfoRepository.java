package com.hycan.idn.hvip.repository;


import com.hycan.idn.hvip.entity.OrderItemInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 工单项目信息 Repository
 *
 * @author shichongying
 * @datetime 2023-10-23 10:45
 */
@Repository
public interface OrderItemInfoRepository extends JpaRepository<OrderItemInfoEntity, String> {

    /**
     * 查询单条工单项目信息
     *
     * @param orderCode 工单编号
     * @param itemCode  工单项目编号
     * @return 工单项目信息Entity
     */
    OrderItemInfoEntity findByOrderCodeAndItemCode(String orderCode, Integer itemCode);

    /**
     * 按工单编号和工单项目状态，查询工单项目信息列表
     *
     * @param orderCode  工单编号
     * @param itemStatus 工单项目状态
     * @return 工单项目信息Entity列表
     */
    List<OrderItemInfoEntity> findAllByOrderCodeAndItemStatus(String orderCode, Integer itemStatus);

    /**
     * 按工单编号和工单项目列表，查询工单项目信息列表
     *
     * @param orderCode    工单编号
     * @param itemCodeList 工单项目编号列表
     * @return 工单项目信息Entity列表
     */
    List<OrderItemInfoEntity> findAllByOrderCodeAndItemCodeIn(String orderCode, List<Integer> itemCodeList);

    /**
     * 按工单编号，查询工单项目信息列表
     *
     * @param orderCode  工单编号
     * @return 工单项目信息Entity列表
     */
    List<OrderItemInfoEntity> findAllByOrderCode(String orderCode);

    /**
     * 按工单编号列表和，查询场站工单号不为空的，工单项目信息列表
     *
     * @param orderCodes 工单编号
     * @return 工单项目信息Entity列表
     */
    List<OrderItemInfoEntity> findByOrderCodeInAndStationOrderCodeIsNotNull(List<String> orderCodes);
}
