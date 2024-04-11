package com.hycan.idn.hvip.entity;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
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

/**
 * 场站基础信息Entity
 *
 * @author shichongying
 * @datetime 2023-10-20 17:27
 */
@Data
@Entity
@Table(name = "station_basic_info")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@TypeDefs({
        @TypeDef(name = "string-array", typeClass = StringArrayType.class),
        @TypeDef(name = "int-array", typeClass = IntArrayType.class)
})
public class StationBasicInfoEntity {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    @Comment("场站编号")
    @Column(name = "station_code", nullable = false, length = 20)
    private String stationCode;

    @Comment("场站名称")
    @Column(name = "station_name", nullable = false, length = 50)
    private String stationName;

    @Comment("AVP站编号")
    @Column(name = "avp_station_code", nullable = false, length = 20)
    private String avpStationCode;

    @Comment("场站标签")
    @Type(type = "string-array")
    @Column(name = "station_label", columnDefinition = "VARCHAR[]")
    private String[] stationLabel;

    @Comment("场站地址")
    @Column(name = "station_address", nullable = false, length = 200)
    private String stationAddress;

    @Comment("场站状态(0:在线 1:离线)")
    @Column(name = "station_status", nullable = false)
    private Integer stationStatus;

    @Comment("车场秘钥")
    @Column(name = "station_key", length = 100)
    private String stationKey;

    @Comment("场站经度")
    @Column(name = "longitude", nullable = false, length = 100, columnDefinition = "FLOAT8")
    private Double longitude;

    @Comment("场站纬度")
    @Column(name = "latitude", nullable = false, length = 100, columnDefinition = "FLOAT8")
    private Double latitude;

    @Comment("服务清单(0:停车 1:充电 2:洗车 3:检测)")
    @Type(type = "int-array")
    @Column(name = "item_list", nullable = false, columnDefinition = "INTEGER[]")
    private Integer[] itemList;
}