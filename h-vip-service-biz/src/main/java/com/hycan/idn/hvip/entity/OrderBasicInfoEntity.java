package com.hycan.idn.hvip.entity;

import com.hycan.idn.hvip.constants.OrderConstants;
import com.hycan.idn.hvip.pojo.mp.http.req.SaveOrderInfoReqVO;
import com.hycan.idn.hvip.util.LoginUserUtils;
import com.vladmihalcea.hibernate.type.array.IntArrayType;
import lombok.Data;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 工单基础信息Entity
 *
 * @author shichongying
 * @datetime 2023-10-20 17:26
 */
@Data
@Entity
@Table(name = "order_basic_info")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@TypeDefs({
        @TypeDef(name = "int-array", typeClass = IntArrayType.class)
})
public class OrderBasicInfoEntity {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    @Comment("服务编号")
    @Column(name = "order_code", nullable = false, length = 20)
    private String orderCode;

    @Comment("场站编号")
    @Column(name = "station_code", nullable = false, length = 20)
    private String stationCode;

    @Comment("用户ID")
    @Column(name = "uid", nullable = false)
    private Long uid;

    @Comment("车架号")
    @Column(name = "vin", nullable = false, length = 25)
    private String vin;

    @Comment("服务清单(0:停车 1:充电 2:洗车 3:检测)")
    @Type(type = "int-array")
    @Column(name = "item_list", nullable = false, columnDefinition = "INTEGER[]")
    private Integer[] itemList;

    @Comment("工单状态(0:末开始 1:进行中 2:已完成 3:等待取车 4:取车中 5:取车完成)")
    @Column(name = "order_status", nullable = false)
    private Integer orderStatus;

    @Comment("服务开始时间")
    @Column(name = "start_time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private LocalDateTime startTime;

    @Comment("服务结束时间")
    @Column(name = "end_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime endTime;

    public static OrderBasicInfoEntity of(SaveOrderInfoReqVO req) {
        OrderBasicInfoEntity entity = new OrderBasicInfoEntity();
        entity.setStationCode(req.getStationCode());
        entity.setVin(req.getVin());
        List<Integer> itemList = req.getItemList();
        itemList.add(OrderConstants.ORDER_ITEM_CODE_PARKING);
        entity.setItemList(itemList.toArray(new Integer[0]));
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmssSSS"));
        entity.setOrderCode(req.getStationCode() + now);
        entity.setUid(LoginUserUtils.getLoginUserId());
        entity.setOrderStatus(OrderConstants.ORDER_STATUS_NOT_START);
        return entity;
    }
}
