package com.hycan.idn.hvip.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * MP MQTT Topic 枚举
 *
 * @author shichongying
 * @datetime 2023-10-20 17:34
 */
@Getter
@AllArgsConstructor
public enum MpMqttTopicEnum {

    // @formatter:off
    /** 功能位状态变化通知 下行 Topic:down/mp/hvip/${vin}/parking_space_status */
    MP_DOWN_TOPIC_PARKING_SPACE_STATUS("parking_space_status","down/mp/hvip/%s/parking_space_status", "功能位状态变化通知"),

    /** 调度状态通知 下行 Topic:down/mp/hvip/${vin}/dispatch_state */
    MP_DOWN_TOPIC_DISPATCH_STATE("dispatch_state","down/mp/hvip/%s/dispatch_state", "车辆调度状态通知"),

    /** 调度路线通知 下行 Topic:down/mp/hvip/${vin}/dispatch_line */
    MP_DOWN_TOPIC_DISPATCH_LINE("dispatch_line","down/mp/hvip/%s/dispatch_line", "调度路线通知"),

    /** 行驶状态通知 下行 Topic:down/mp/hvip/${vin}/driving_state */
    MP_DOWN_TOPIC_DRIVING_STATE("driving_state","down/mp/hvip/%s/driving_state", "调度状态通知"),

    /** 行驶路线通知 下行 Topic:down/mp/hvip/${vin}/driving_line */
    MP_DOWN_TOPIC_DRIVING_LINE("driving_line","down/mp/hvip/%s/driving_line", "行驶路线通知"),

    /** 取消服务通知 下行 Topic:down/mp/hvip/${vin}/cancel_result */
    MP_DOWN_TOPIC_CANCEL_RESULT("cancel_result","down/mp/hvip/%s/cancel_result", "取消服务通知"),

    /** 取车结果通知 下行 Topic:down/mp/hvip/${vin}/parking_out_result */
    MP_DOWN_TOPIC_PARKING_OUT_RESULT("parking_out_result","down/mp/hvip/%s/parking_out_result", "取车结果通知"),

    ;
    // @formatter:on

    private final String suffix;

    private final String topic;

    private final String desc;
}