package com.hycan.idn.hvip.pojo.avp.mqtt.down;

import com.hycan.idn.hvip.pojo.avp.mqtt.AvpVinPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 取消泊车服务应答下行对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CancelAiParkInDownDTO extends AvpVinPayload {

    private String pid;

    private List<CancelAiParkInResultDTO> results = new ArrayList<>();
}
