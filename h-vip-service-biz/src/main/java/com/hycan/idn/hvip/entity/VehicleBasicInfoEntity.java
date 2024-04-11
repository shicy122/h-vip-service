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
 * 车辆基础信息Entity
 *
 * @author shichongying
 * @datetime 2023-10-20 17:30
 */
@Data
@Entity
@Table(name = "vehicle_basic_info")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class VehicleBasicInfoEntity {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    @Comment("用户ID")
    @Column(name = "uid", nullable = false)
    private Long uid;

    @Comment("车架号")
    @Column(name = "vin", nullable = false, length = 25)
    private String vin;

    @Comment("车牌")
    @Column(name = "plate_no", nullable = false, length = 20)
    private String plateNo;

    @Comment("是否支持AVP(0:支持 1:不支持)")
    @Column(name = "is_avp", nullable = false)
    private Integer isAvp;

    @Comment("车辆秘钥")
    @Column(name = "ccm_secret", length = 100)
    private String ccmSecret;
}