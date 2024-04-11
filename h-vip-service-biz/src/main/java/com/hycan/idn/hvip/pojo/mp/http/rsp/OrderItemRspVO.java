package com.hycan.idn.hvip.pojo.mp.http.rsp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hycan.idn.hvip.entity.OrderItemInfoEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * 工单项目信息响应对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "工单项目信息响应对象")
public class OrderItemRspVO {

    @ApiModelProperty(value = "工单编号")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty(value = "服务项(0:停车 1:充电 2:洗车 3:检测)")
    @JsonProperty("item_code")
    private Integer itemCode;

    @ApiModelProperty(value = "服务状态(0:末开始 1:进行中 2:已取消 3:已完成 4:取消中)")
    @JsonProperty("item_status")
    private Integer itemStatus;

    @ApiModelProperty(value = "AVP调度状态(0:已完成 1:进行中 2:排队中)")
    @JsonProperty("avp_status")
    private Integer avpStatus;

    @ApiModelProperty(value = "服务开始时间")
    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "服务结束时间")
    @JsonProperty("end_time")
    private LocalDateTime endTime;

    public static OrderItemRspVO of(OrderItemInfoEntity entity) {
        OrderItemRspVO rsp = new OrderItemRspVO();
        BeanUtils.copyProperties(entity, rsp);
        return rsp;
    }
}