package com.hycan.idn.hvip.pojo.mp.mqtt.down;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hycan.idn.hvip.pojo.mp.mqtt.MpPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 取消服务通知下行对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CancelResultDownDTO extends MpPayload {

    /**
     * 场站编号
     */
    @JsonProperty(value = "station_code")
    private String stationCode;

    /**
     * 服务编号
     */
    @JsonProperty(value = "order_code")
    private String orderCode;

    /**
     * 服务类型(0:停车 1:充电 2:洗车 3:检测)
     */
    @JsonProperty(value = "item_code")
    private Integer itemCode;

    /**
     * 泊车结果(true:成功，false:失败)
     */
    private Boolean result;

    /**
     * 失败原因描述
     */
    private String error;

    public static CancelResultDownDTO of(String stationCode, String plateNo, String orderCode,
                                         Integer itemCode, Boolean result, String error) {
        CancelResultDownDTO dto = new CancelResultDownDTO();
        dto.setStationCode(stationCode);
        dto.setPlateNo(plateNo);
        dto.setOrderCode(orderCode);
        dto.setItemCode(itemCode);
        dto.setResult(result);
        dto.setError(error);
        return dto;
    }
}
