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
 * 场站高精地图Entity
 *
 * @author shichongying
 * @datetime 2023-10-20 17:28
 */
@Data
@Entity
@Table(name = "station_map_info")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class StationMapInfoEntity {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    @Comment("服务编号")
    @Column(name = "station_code", nullable = false, length = 20)
    private String stationCode;

    @Comment("箭头")
    @Column(name = "map_arrow", columnDefinition = "TEXT")
    private String mapArrow;

    @Comment("道路路网")
    @Column(name = "map_boundary", columnDefinition = "TEXT")
    private String mapBoundary;

    @Comment("减速带")
    @Column(name = "map_bump", columnDefinition = "TEXT")
    private String mapBump;

    @Comment("减速带区域横向的中心线")
    @Column(name = "map_bump_line", columnDefinition = "TEXT")
    private String mapBumpLine;

    @Comment("停车场中心点")
    @Column(name = "map_center", columnDefinition = "TEXT")
    private String mapCenter;

    @Comment("道路中心线")
    @Column(name = "map_center_line", columnDefinition = "TEXT")
    private String mapCenterLine;

    @Comment("lane的连接点(虚拟概念)")
    @Column(name = "map_connector", columnDefinition = "TEXT")
    private String mapConnector;

    @Comment("斑马线")
    @Column(name = "map_crosswalk", columnDefinition = "TEXT")
    private String mapCrosswalk;

    @Comment("单条车道的骨架线(虚拟概念)")
    @Column(name = "map_lane", columnDefinition = "TEXT")
    private String mapLane;

    @Comment("整条道路的骨架线(虚拟概念)")
    @Column(name = "map_link", columnDefinition = "TEXT")
    private String mapLink;

    @Comment("不同link的连接点(虚拟概念)")
    @Column(name = "map_node", columnDefinition = "TEXT")
    private String mapNode;

    @Comment("接驳区域")
    @Column(name = "map_out_area", columnDefinition = "TEXT")
    private String mapOutArea;

    @Comment("停车场轮廓")
    @Column(name = "map_outline", columnDefinition = "TEXT")
    private String mapOutline;

    @Comment("柱子")
    @Column(name = "map_pillar", columnDefinition = "TEXT")
    private String mapPillar;

    @Comment("(带开口方向的)停车位边框线")
    @Column(name = "map_spot_lines", columnDefinition = "TEXT")
    private String mapSpotLines;

    @Comment("停车位")
    @Column(name = "map_spots", columnDefinition = "TEXT")
    private String mapSpots;

    @Comment("道路的路面")
    @Column(name = "map_surface", columnDefinition = "TEXT")
    private String mapSurface;
}