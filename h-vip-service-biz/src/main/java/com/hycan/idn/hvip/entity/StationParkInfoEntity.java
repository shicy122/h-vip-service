package com.hycan.idn.hvip.entity;

import lombok.Data;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 场站车位信息Entity
 *
 * @author shichongying
 * @datetime 2023-10-20 17:30
 */
@Data
@Entity
@Table(name = "station_park_info")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class StationParkInfoEntity {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    @Comment("车架号")
    @Column(name = "vin", nullable = false, length = 25)
    private String vin;

    @Comment("场站编号")
    @Column(name = "station_code", nullable = false, length = 20)
    private String stationCode;

    @Comment("停车位编号")
    @Column(name = "park_no", nullable = false, length = 10)
    private String parkNo;

    @Comment("AVP车位编号")
    @Column(name = "avp_park_no", nullable = false, length = 10)
    private String avpParkNo;

    @Comment("车位类型(0:停车位 1:充电位 2:洗车位 3:检测位 101:待泊位 100:取车位)")
    @Column(name = "park_type", nullable = false)
    private Integer parkType;

    @Comment("车位状态(0:空闲 1:占用 2:故障 3:不可用 4:未知)")
    @Column(name = "park_status", nullable = false)
    private Integer parkStatus;
}
