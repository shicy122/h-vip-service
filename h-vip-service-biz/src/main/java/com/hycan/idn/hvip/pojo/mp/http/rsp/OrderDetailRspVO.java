package com.hycan.idn.hvip.pojo.mp.http.rsp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hycan.idn.hvip.constants.OrderConstants;
import com.hycan.idn.station.enums.FeeTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 工单详情响应对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "工单详情响应对象")
public class OrderDetailRspVO {

    @ApiModelProperty(value = "工单信息")
    @JsonProperty("orderInfo")
    private OrderInfoRspVO orderInfo;

    @ApiModelProperty(value = "费用明细")
    @JsonProperty("fee_detail")
    private FeeDetail feeDetail;

    @ApiModelProperty(value = "充电服务项")
    @JsonProperty("charge_order_item")
    private ChargeOrderItem chargeOrderItem;

    @ApiModelProperty(value = "洗车服务项")
    @JsonProperty("wash_order_item")
    private WashOrderItem washOrderItem;

    @ApiModelProperty(value = "检测服务项")
    @JsonProperty("check_order_item")
    private CheckOrderItem checkOrderItem;

    public void setFeeDetail(FeeTypeEnum type, BigDecimal itemFee) {
        FeeDetail fee = this.getFeeDetail();
        if (FeeTypeEnum.FEE_TYPE_CHARGING.equals(type)) {
            fee.setChargingFee(itemFee);
        } else if (FeeTypeEnum.FEE_TYPE_CHECK.equals(type)) {
            fee.setCheckFee(itemFee);
        } else if (FeeTypeEnum.FEE_TYPE_WASH.equals(type)) {
            fee.setWashFee(itemFee);
        } else if (FeeTypeEnum.FEE_TYPE_CHARGING_SERVICE.equals(type)) {
            fee.setChargeServiceFee(itemFee);
        } else if (FeeTypeEnum.FEE_TYPE_HELP_CHARGING.equals(type)) {
            fee.setChargeLaborFee(itemFee);
        }
    }

    public void setOrderItem(Integer itemCode, Integer itemStatus, LocalDateTime startTime, LocalDateTime endTime){
        if (OrderConstants.ORDER_ITEM_CODE_WASH.equals(itemCode)) {
            setWashOrderItem(itemStatus, startTime, endTime);
        } else if (OrderConstants.ORDER_ITEM_CODE_CHECK.equals(itemCode)) {
            setCheckOrderItem(itemStatus, startTime, endTime);
        } else if (OrderConstants.ORDER_ITEM_CODE_CHARGING.equals(itemCode)) {
            setChargeOrderItem(itemStatus, startTime, endTime);
        }
    }

    private void setChargeOrderItem(Integer itemStatus, LocalDateTime startTime, LocalDateTime endTime) {
        ChargeOrderItem item = this.chargeOrderItem;
        item.setItemStatus(itemStatus);
        item.setStartTime(startTime);
        item.setEndTime(endTime);
    }

    public void setChargeOrderItem(Double chargeCapacity, String connectorName) {
        ChargeOrderItem item = this.chargeOrderItem;
        item.setChargeCapacity(chargeCapacity);
        item.setConnectorName(connectorName);
    }

    private void setWashOrderItem(Integer itemStatus, LocalDateTime startTime, LocalDateTime endTime) {
        WashOrderItem item = this.washOrderItem;
        item.setItemStatus(itemStatus);
        item.setStartTime(startTime);
        item.setEndTime(endTime);
    }

    private void setCheckOrderItem(Integer itemStatus, LocalDateTime startTime, LocalDateTime endTime) {
        CheckOrderItem item = this.checkOrderItem;
        item.setItemStatus(itemStatus);
        item.setStartTime(startTime);
        item.setEndTime(endTime);
    }
}

@Data
class FeeDetail {
    @ApiModelProperty(value = "停车费")
    @JsonProperty("parking_fee")
    private BigDecimal parkingFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "充电电费")
    @JsonProperty("charging_fee")
    private BigDecimal chargingFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "充电服务费")
    @JsonProperty("charge_service_fee")
    private BigDecimal chargeServiceFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "充电人工费")
    @JsonProperty("charge_labor_fee")
    private BigDecimal chargeLaborFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "洗车费")
    @JsonProperty("wash_fee")
    private BigDecimal washFee = BigDecimal.ZERO;

    @ApiModelProperty(value = "检测费")
    @JsonProperty("check_fee")
    private BigDecimal checkFee = BigDecimal.ZERO;
}

@Data
class ChargeOrderItem {
    @ApiModelProperty(value = "充电服务项状态")
    @JsonProperty("item_status")
    private Integer itemStatus;

    @ApiModelProperty(value = "服务开始时间")
    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "服务结束时间")
    @JsonProperty("end_time")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "充电电量")
    @JsonProperty("charge_capacity")
    private Double chargeCapacity;

    @ApiModelProperty(value = "充电枪名称")
    @JsonProperty("connector_name")
    private String connectorName;
}

@Data
class WashOrderItem {
    @ApiModelProperty(value = "洗车服务项状态")
    @JsonProperty("item_status")
    private Integer itemStatus;

    @ApiModelProperty(value = "服务开始时间")
    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "服务结束时间")
    @JsonProperty("end_time")
    private LocalDateTime endTime;
}

@Data
class CheckOrderItem {
    @ApiModelProperty(value = "检测服务项状态")
    @JsonProperty("item_status")
    private Integer itemStatus;

    @ApiModelProperty(value = "服务开始时间")
    @JsonProperty("start_time")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "服务结束时间")
    @JsonProperty("end_time")
    private LocalDateTime endTime;
}