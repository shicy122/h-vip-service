package com.hycan.idn.hvip.pojo.avp.mqtt.down;

import com.hycan.idn.hvip.pojo.avp.mqtt.AvpPidPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * 边缘云Key变化下行对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ParkingLotKeyChangedDownDTO extends AvpPidPayload {
}