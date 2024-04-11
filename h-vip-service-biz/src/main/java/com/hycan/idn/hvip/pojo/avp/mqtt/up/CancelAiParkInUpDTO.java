package com.hycan.idn.hvip.pojo.avp.mqtt.up;

import com.hycan.idn.hvip.pojo.avp.mqtt.AvpVinPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 取消泊车服务上行对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CancelAiParkInUpDTO extends AvpVinPayload {

    /**
     * 停车场id
     */
    @NotBlank
    private String pid;

    /**
     * 服务类型：0智慧停车 1智慧充电
     */
    @Min(1)
    private List<Integer> types;

    public static CancelAiParkInUpDTO of(String avpStationCode, String vin, List<Integer> itemList) {
        CancelAiParkInUpDTO dto = new CancelAiParkInUpDTO();
        dto.setPid(avpStationCode);
        dto.setVin(vin);
        dto.setTypes(itemList);
        return dto;
    }
}
