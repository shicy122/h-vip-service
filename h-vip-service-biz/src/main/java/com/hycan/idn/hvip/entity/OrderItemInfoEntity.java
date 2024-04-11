package com.hycan.idn.hvip.entity;

import lombok.Data;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 工单项目信息Entity
 *
 * @author shichongying
 * @datetime 2023-10-20 17:26
 */
@Data
@Entity
@Table(name = "order_item_info",
        indexes = {
                @Index(name = "idx_orderItem_orderCode_itemCode", columnList = "order_code, item_code", unique = true),
                @Index(name = "idx_orderItem_orderCode", columnList = "order_code"),
                @Index(name = "idx_orderItem_itemStatus", columnList = "order_code, item_status")
        })
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class OrderItemInfoEntity {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    @Comment("工单编号")
    @Column(name = "order_code", nullable = false, length = 20)
    private String orderCode;

    @Comment("场站服务生成的工单编号")
    @Column(name = "station_order_code", nullable = false, length = 20)
    private String stationOrderCode;

    @Comment("服务类型(0:停车 1:充电 2洗车 3:检测)")
    @Column(name = "item_code", nullable = false)
    private Integer itemCode;

    @Comment("AVP调度状态(0:已完成 1:进行中 2:排队中 3:未开始)")
    @Column(name = "avp_status", nullable = false)
    private Integer avpStatus;

    @Comment("服务状态(0:末开始 1:进行中 2:已取消 3:已完成 4:取消中)")
    @Column(name = "item_status", nullable = false)
    private Integer itemStatus;

    @Comment("服务开始时间")
    @Column(name = "start_time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime startTime;

    @Comment("服务结束时间")
    @Column(name = "end_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime endTime;
}
