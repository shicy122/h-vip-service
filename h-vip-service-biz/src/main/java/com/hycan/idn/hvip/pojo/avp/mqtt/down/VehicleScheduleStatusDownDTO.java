package com.hycan.idn.hvip.pojo.avp.mqtt.down;

import com.hycan.idn.hvip.pojo.avp.mqtt.AvpVinPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 车辆调度状态下行对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class VehicleScheduleStatusDownDTO extends AvpVinPayload {

    /**
     * 停车场ID
     */
    private String pid;

    /**
     * 车辆调度状态
     */
    private List<VehicleScheduleStatusDetailDTO> status = new ArrayList<>();

}
