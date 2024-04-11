package com.hycan.idn.hvip.pojo.mp.http.rsp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hycan.idn.hvip.entity.OrderBasicInfoEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 工单基础信息响应对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "工单基础信息响应对象")
public class OrderInfoRspVO {

    @ApiModelProperty(value = "工单编号")
    @JsonProperty("order_code")
    private String orderCode;

    @ApiModelProperty(value = "场站编号")
    @JsonProperty("station_code")
    private String stationCode;

    @ApiModelProperty(value = "车架号")
    private String vin;

    @ApiModelProperty(value = "服务清单(0:停车 1:充电 2:洗车 3:检测)")
    @JsonProperty("item_list")
    private Integer[] itemList;

    @ApiModelProperty(value = "工单状态(0:末开始 1:进行中 2:已完成 3:等待取车 4:取车中 5:取车完成)")
    @JsonProperty("order_status")
    private Integer orderStatus;

    @ApiModelProperty(value = "总费用")
    @JsonProperty("total_fee")
    private BigDecimal totalFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "服务开始时间")
    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "服务结束时间")
    @JsonProperty("end_time")
    private LocalDateTime endTime;

    public OrderInfoRspVO(String orderCode, String stationCode, String vin,
                          Object itemList, Integer orderStatus,
                          LocalDateTime startTime, LocalDateTime endTime) {
        this.orderCode = orderCode;
        this.stationCode = stationCode;
        this.vin = vin;
        this.itemList = (Integer[]) itemList;
        this.orderStatus = orderStatus;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static OrderInfoRspVO of(OrderBasicInfoEntity entity) {
        OrderInfoRspVO rsp = new OrderInfoRspVO();
        BeanUtils.copyProperties(entity, rsp);
        return rsp;
    }
}