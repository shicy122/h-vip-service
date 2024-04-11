package com.hycan.idn.hvip.pojo.avp.mqtt.down;

import com.hycan.idn.hvip.pojo.avp.mqtt.AvpVinPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 新路线下行对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NewRouteDownDTO extends AvpVinPayload {

    /**
     * 车牌号
     */
    private String lpn;

    /**
     * 路径坐标集合
     */
    private List<NewRoutPathDTO> path = new ArrayList<>();
}
