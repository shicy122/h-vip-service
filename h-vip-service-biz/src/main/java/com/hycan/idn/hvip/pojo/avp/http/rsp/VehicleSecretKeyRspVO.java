package com.hycan.idn.hvip.pojo.avp.http.rsp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 车控秘钥响应对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleSecretKeyRspVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * deviceUuid
     */
    private String deviceUuid;

    /**
     * deviceToken
     */
    private String deviceToken;

    /**
     * deviceName
     */
    private String deviceName;

    /**
     * created
     */
    private Long created;

    /**
     * updated
     */
    private Long updated;
}