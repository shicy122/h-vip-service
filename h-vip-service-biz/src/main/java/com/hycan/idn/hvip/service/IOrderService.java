package com.hycan.idn.hvip.service;

import com.hycan.idn.hvip.entity.OrderItemInfoEntity;
import com.hycan.idn.hvip.pojo.mp.http.PageVO;
import com.hycan.idn.hvip.pojo.mp.http.req.ListOrderInfoReqVO;
import com.hycan.idn.hvip.pojo.mp.http.req.SaveOrderInfoReqVO;
import com.hycan.idn.hvip.pojo.mp.http.rsp.OrderDetailRspVO;
import com.hycan.idn.hvip.pojo.mp.http.rsp.OrderInfoRspVO;
import com.hycan.idn.hvip.pojo.mp.http.rsp.OrderItemRspVO;

/**
 * 工单相关业务Service
 *
 * @author shichongying
 * @datetime 2023-10-23 14:47
 */
public interface IOrderService {

    /**
     * 创建工单
     *
     * @param req 创建工单列表请求VO
     * @return 工单信息响应VO
     */
    OrderInfoRspVO saveOrderInfo(SaveOrderInfoReqVO req);

    /**
     * 分页查询工单列表
     *
     * @param req 分页查询工单列表请求VO
     * @return 工单列表分页VO
     */
    PageVO<OrderInfoRspVO> listOrderInfo(ListOrderInfoReqVO req);

    /**
     * 查询工单详情
     *
     * @param orderCode 工单编号
     * @return 工单详情
     */
    OrderDetailRspVO showOrderDetail(String orderCode);

    /**
     * 取消工单项目
     *
     * @param orderCode 工单编号
     * @param itemCode  工单项目
     * @return 工单项目信息响应
     */
    OrderItemRspVO cancelOrderItem(String orderCode, Integer itemCode);

    /**
     * 停止工单
     *
     * @param orderCode 工单编号
     * @return 工单信息响应VO
     */
    OrderInfoRspVO stopOrder(String orderCode);

    /**
     * 修改工单项目状态
     *
     * @param entity           工单项目信息Entity
     * @param avpStatus        AVP调度状态
     * @param itemStatus       工单项目状态
     * @param stationOrderCode 场站服务工单号
     */
    void updateOrderItem(OrderItemInfoEntity entity, Integer avpStatus, Integer itemStatus, String stationOrderCode);
}
