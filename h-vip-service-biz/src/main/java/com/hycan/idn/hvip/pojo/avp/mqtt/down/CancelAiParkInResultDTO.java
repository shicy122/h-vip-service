package com.hycan.idn.hvip.pojo.avp.mqtt.down;

import lombok.Data;

/**
 * 取消泊车服务结果
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
public class CancelAiParkInResultDTO {

    /**
     * 服务类型(0:停车 1:充电 2:洗车 3:检测)
     */
    private Integer type;

    /**
     * 完成情况(true:取消成功 false:取消失败)
     */
    private Boolean result;

    /**
     * 如果不成功反馈error描述
     */
    private String error;
}
