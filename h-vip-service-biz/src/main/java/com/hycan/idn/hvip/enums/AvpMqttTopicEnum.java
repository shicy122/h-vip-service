package com.hycan.idn.hvip.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * AVP MQTT Topic 枚举
 *
 * @author shichongying
 * @datetime 2023-10-20 17:34
 */
@Getter
@AllArgsConstructor
public enum AvpMqttTopicEnum {

    // @formatter:off

    /**
     * H-VIP无使用场景, 服务初始化时, 已调用中心云接口, 获取场站基础信息和场站状态
     * 请求边缘云状态 上行 Topic:/mp/cmd/parkinglot_status/${pid}/${pkey}
     */
    AVP_UP_TOPIC_PARKING_LOT_STATUS("/mp/cmd/parkinglot_status", "/mp/cmd/parkinglot_status/%s/%s", "请求边缘云状态"),

    /** 通知边缘云状态 下行 Topic:/cc/ind/parkinglot_status/${pid}/${pkey} */
    AVP_DOWN_TOPIC_PARKING_LOT_STATUS("/cc/ind/parkinglot_status", "/cc/ind/parkinglot_status/%s/%s", "通知边缘云状态"),

    /** 边缘云key变化 下行 Topic:/cc/ind/parkinglot_key_changed/${pid}/${pkey} */
    AVP_DOWN_TOPIC_PARKING_LOT_KEY_CHANGED("/cc/ind/parkinglot_key_changed", "/cc/ind/parkinglot_key_changed/%s/%s", "边缘云key变化"),

    /** 请求车位状态 上行 Topic:/mp/cmd/parking_space_status/${pid}/${pkey} */
    AVP_UP_TOPIC_PARKING_SPACE_STATUS("/mp/cmd/parking_space_status", "/mp/cmd/parking_space_status/%s/%s", "请求车位状态"),

    /** 通知车位状态 下行 Topic:/cc/ind/parking_space_status/${pid}/${pkey} */
    AVP_DOWN_TOPIC_PARKING_SPACE_STATUS("/cc/ind/parking_space_status", "/cc/ind/parking_space_status/%s/%s", "通知车位状态"),

    /** 车辆反馈通知 下行 Topic:/cc/ind/vehicle_feedback/${vin}/${vkey} */
    AVP_DOWN_TOPIC_VEHICLE_FEEDBACK("/cc/ind/vehicle_feedback", "/cc/ind/vehicle_feedback/%s/%s", "车辆反馈通知"),

    /** 泊车服务 上行 Topic:/mp/req/ai_park_in/${vin}/${vkey} */
    AVP_UP_TOPIC_AI_PARK_IN("/mp/req/ai_park_in", "/mp/req/ai_park_in/%s/%s", "泊车服务"),

    /**
     * H-VIP无使用场景, 中心云只有在所有服务型完成时, 才会推送泊车服务结果, H-VIP只需要每次服务完成时的结果, 可以使用调度状态消息替代
     * 泊车服务结果 下行 Topic:/cc/res/ai_park_in/${vin}/${vkey}
     */
    AVP_DOWN_TOPIC_AI_PARK_IN("/cc/res/ai_park_in", "/cc/res/ai_park_in/%s/%s", "泊车服务结果"),

    /** 取车服务 上行 Topic:/mp/req/ai_park_out/${vin}/${vkey} */
    AVP_UP_TOPIC_AI_PARK_OUT("/mp/req/ai_park_out", "/mp/req/ai_park_out/%s/%s", "取车服务"),

    /** 取车服务应答 下行 Topic:/cc/res/ai_park_out/${vin}/${vkey} */
    AVP_DOWN_TOPIC_AI_PARK_OUT("/cc/res/ai_park_out", "/cc/res/ai_park_out/%s/%s", "取车服务应答"),

    /** 新路线 下行 Topic:/cc/ind/new_route/${vin}/${vkey} */
    AVP_DOWN_TOPIC_NEW_ROUTE("/cc/ind/new_route", "/cc/ind/new_route/%s/%s", "新路线"),

    /** 路线信息 下行 Topic:/cc/ind/route_info/${vin}/${vkey} */
    AVP_DOWN_TOPIC_ROUTE_INFO("/cc/ind/route_info", "/cc/ind/route_info/%s/%s", "路线信息"),

    /** 设置车辆调度权限 上行 Topic:/mp/cmd/set_vehicle_schedule_permission/${vin}/${vkey} */
    AVP_UP_TOPIC_SET_VEHICLE_SCHEDULE_PERMISSION("/mp/cmd/set_vehicle_schedule_permission", "/mp/cmd/set_vehicle_schedule_permission/%s/%s", "设置车辆调度权限"),

    /** 车辆调度状态通知 下行 Topic:/cc/ind/vehicle_schedule_status/${vin}/${vkey} */
    AVP_DOWN_TOPIC_VEHICLE_SCHEDULE_STATUS("/cc/ind/vehicle_schedule_status", "/cc/ind/vehicle_schedule_status/%s/%s", "车辆调度状态通知"),

    /** 调度权限请求 下行 Topic:/cc/req/vehicle_schedule_permission/${vin}/${vkey} */
    AVP_DOWN_TOPIC_VEHICLE_SCHEDULE_PERMISSION("/cc/req/vehicle_schedule_permission", "/cc/req/vehicle_schedule_permission/%s/%s", "调度权限请求"),

    /** 调度权限应答 上行 Topic:/mp/res/vehicle_schedule_permission/${vin}/${vkey} */
    AVP_UP_TOPIC_VEHICLE_SCHEDULE_PERMISSION("/mp/res/vehicle_schedule_permission", "/mp/res/vehicle_schedule_permission/%s/%s", "调度权限应答"),

    /** 取消泊车服务 上行 Topic:/mp/req/cancel_ai_park_in/${vin}/${vkey} */
    AVP_UP_TOPIC_CANCEL_AI_PARK_IN("/mp/req/cancel_ai_park_in", "/mp/req/cancel_ai_park_in/%s/%s", "取消泊车服务"),

    /** 取消泊车服务应答 下行 Topic:/cc/res/cancel_ai_park_in/${vin}/${vkey} */
    AVP_DOWN_TOPIC_CANCEL_AI_PARK_IN("/cc/res/cancel_ai_park_in", "/cc/res/cancel_ai_park_in/%s/%s", "取消泊车服务应答"),

    /**
     * H-VIP无使用场景, 不支持取消取车
     * 取消取车服务 上行 Topic:/mp/req/cancel_ai_park_out/${vin}/${vkey}
     */
    AVP_UP_TOPIC_CANCEL_AI_PARK_OUT("/mp/req/cancel_ai_park_out", "/mp/req/cancel_ai_park_out/%s/%s", "取消取车服务"),

    /**
     * H-VIP无使用场景, 不支持取消取车
     * 取消取车服务应答 下行 Topic:/cc/res/cancel_ai_park_out/${vin}/${vkey}
     */
    AVP_DOWN_TOPIC_CANCEL_AI_PARK_OUT("/cc/res/cancel_ai_park_out", "/cc/res/cancel_ai_park_out/%s/%s", "取消取车服务应答");

    // @formatter:on

    private final String prefix;

    private final String topic;

    private final String desc;
}