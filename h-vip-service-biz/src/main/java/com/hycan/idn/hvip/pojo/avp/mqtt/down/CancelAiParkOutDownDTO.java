package com.hycan.idn.hvip.pojo.avp.mqtt.down;

import com.hycan.idn.hvip.pojo.avp.mqtt.AvpVinPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 取消取车服务应答下行对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CancelAiParkOutDownDTO extends AvpVinPayload {

    /**
     * 停车场ID
     */
    private String pid;

    /**
     * 完成情况(true:取消成功 false取消失败)
     */
    private Boolean result;

    /**
     * 如果不成功反馈error描述
     */
    private String error;
}
