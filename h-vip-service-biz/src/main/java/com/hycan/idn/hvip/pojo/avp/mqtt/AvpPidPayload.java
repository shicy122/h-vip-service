package com.hycan.idn.hvip.pojo.avp.mqtt;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * AVP Payload对象（Topic含pid）
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AvpPidPayload extends Payload {
    /**
     * 停车场id
     */
    private String pid;
}
