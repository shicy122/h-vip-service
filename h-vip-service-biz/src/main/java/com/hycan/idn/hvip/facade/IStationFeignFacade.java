package com.hycan.idn.hvip.facade;

import com.hycan.idn.station.pojo.resp.OrderFeeInfoRspVO;

import java.util.List;
import java.util.Set;

/**
 * 场站服务Feign调用包装类
 *
 * @author shichongying
 * @datetime 2023-10-20 17:58
 */
public interface IStationFeignFacade {

    /***
     * H-VIP服务创建工单时，调用该接口
     *
     * @param stationCode 场站编号
     * @param vin 车架号
     * @param itemCode 服务类型(0:停车 1:充电 2:洗车 3:检测)
     * @return 场站服务返回的工单编号
     */
    String startOrderItem(String stationCode, String vin, Integer itemCode);

    /***
     * H-VIP服务结束工单时，调用该接口
     *
     * @param orderCode 工单编号
     * @param itemCode 服务类型(0:停车 1:充电 2:洗车 3:检测)
     */
    void stopOrderItem(String orderCode, Integer itemCode);

    /***
     * H-VIP服务结束工单时，调用该接口
     *
     * @param orderCode 工单编号
     */
    void stopOrder(String orderCode);

    /***
     * H-VIP服务查询工单费用时，调用该接口
     *
     * @param stationOrderCodeList 场站工单号列表
     * @return 工单费用列表
     */
    List<OrderFeeInfoRspVO> selectListFee(Set<String> stationOrderCodeList);
}