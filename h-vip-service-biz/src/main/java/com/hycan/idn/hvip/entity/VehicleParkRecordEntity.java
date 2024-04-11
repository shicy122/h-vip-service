package com.hycan.idn.hvip.entity;

import lombok.Data;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * 车辆泊车记录Entity
 *
 * @author shichongying
 * @datetime 2023-10-20 17:31
 */
@Data
@Entity
@Table(name = "vehicle_park_record")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class VehicleParkRecordEntity {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    @Comment("场站编号")
    @Column(name = "station_code", nullable = false, length = 20)
    private String stationCode;

    @Comment("车架号")
    @Column(name = "vin", nullable = false, length = 25)
    private String vin;

    @Comment("车牌号")
    @Column(name = "plate_no", nullable = false, length = 20)
    private String plateNo;

    @Comment("车位号")
    @Column(name = "park_no", nullable = false, length = 10)
    private String parkNo;

    @Comment("记录时间")
    @Column(name = "record_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime recordTime;
}