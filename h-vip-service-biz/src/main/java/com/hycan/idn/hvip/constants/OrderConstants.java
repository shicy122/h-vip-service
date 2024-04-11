package com.hycan.idn.hvip.constants;

/**
 * 工单相关常量
 *
 * @author shichongying
 * @datetime 2023-10-19 17:57
 */
public interface OrderConstants {

    /** 工单项 0:停车 1:充电 2:洗车 3:检测 */
    Integer ORDER_ITEM_CODE_PARKING = 0;

    Integer ORDER_ITEM_CODE_CHARGING = 1;

    Integer ORDER_ITEM_CODE_WASH = 2;

    Integer ORDER_ITEM_CODE_CHECK = 3;

    /** 工单状态 0:末开始 1:进行中 2:已完成 3:等待取车 4:取车中 5:取车完成 */
    Integer ORDER_STATUS_NOT_START = 0;

    Integer ORDER_STATUS_ONGOING = 1;

    Integer ORDER_STATUS_FINISH = 2;

    Integer ORDER_STATUS_WAIT_PICKUP = 3;

    Integer ORDER_STATUS_PICKING_UP = 4;

    Integer ORDER_STATUS_PICKED_UP = 5;

    /** 服务状态(0:末开始 1:进行中 2:已取消 3:已完成 4:取消中) */
    Integer ORDER_ITEM_STATUS_NOT_START = 0;

    Integer ORDER_ITEM_STATUS_ONGOING = 1;

    Integer ORDER_ITEM_STATUS_CANCELED = 2;

    Integer ORDER_ITEM_STATUS_FINISH = 3;

    Integer ORDER_ITEM_STATUS_CANCELLING = 4;

    /** 调度状态(0:完成 1:正在泊车 2:泊车排队中 3:未开始) */
    Integer DISPATCH_STATUS_FINISH = 0;

    Integer DISPATCH_STATUS_PARKING = 1;

    Integer DISPATCH_STATUS_WAITING = 2;

    Integer DISPATCH_STATUS_NOT_START = 3;
}
