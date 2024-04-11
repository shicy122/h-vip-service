package com.hycan.idn.hvip.pojo.avp.mqtt.up;

import com.hycan.idn.hvip.pojo.avp.mqtt.AvpVinPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 泊车服务上行对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AiParkInUpDTO extends AvpVinPayload {

    /**
     * 停车场ID
     */
    private String pid;

    /**
     * 服务类型(0:停车 1:充电 2:洗车 3:检测)
     */
    private List<Integer> types;

    public static AiParkInUpDTO of(String avpStationCode, String vin, List<Integer> itemList) {
        AiParkInUpDTO dto = new AiParkInUpDTO();
        dto.setPid(avpStationCode);
        dto.setVin(vin);
        dto.setTypes(itemList);
        return dto;
    }
}
