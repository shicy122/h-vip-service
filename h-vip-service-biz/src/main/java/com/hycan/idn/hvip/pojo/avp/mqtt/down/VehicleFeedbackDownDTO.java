package com.hycan.idn.hvip.pojo.avp.mqtt.down;

import com.hycan.idn.hvip.pojo.avp.mqtt.AvpVinPayload;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 车辆反馈通知下行对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class VehicleFeedbackDownDTO extends AvpVinPayload {

    /**
     * 车速度m/s
     */
    private Double speed;

    /**
     * 车加速度m/s^2负数表示刹车
     */
    private Double acc;

    /**
     * 扭矩Nm，vcu当前输出扭矩
     */
    private Double torque;

    /**
     * 方向盘转角，单位degree，逆时针方向为正
     */
    private Double steer;

    /**
     * D档1:R档2:N档3:P档
     */
    private Integer gear;

    /**
     * 熄灭1双闪2故障
     */
    private String blink;

    /**
     * 没有1手刹2故障
     */
    private Integer epb;

    /**
     * 电量
     */
    private Double battery;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 经度
     */
    private Double longitude;

}
