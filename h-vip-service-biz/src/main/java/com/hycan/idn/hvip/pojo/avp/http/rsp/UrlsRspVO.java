package com.hycan.idn.hvip.pojo.avp.http.rsp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 高精地图
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlsRspVO implements Serializable {

    // @formatter:off

    /** 含义：箭头  类型：点  是否显示：否 */
    @JsonProperty("parking_arrow")
    private String parkingArrow;

    /** 含义：道路路网  类型：线  是否显示：是 */
    @JsonProperty("parking_boundary")
    private String parkingBoundary;

    /** 含义：减速带  类型：面  是否显示：是 */
    @JsonProperty("parking_bump")
    private String parkingBump;

    /** 含义：减速带区域横向的中心线  类型：线  是否显示：是 */
    @JsonProperty("parking_bump_line")
    private String parkingBumpLine;

    /** 含义：停车场中心点  类型：点  是否显示：是(缩小zoomlevel时) */
    @JsonProperty("parking_center")
    private String parkingCenter;

    /** 含义：道路中心线  类型：线  是否显示：是 */
    @JsonProperty("parking_center_line")
    private String parkingCenterLine;

    /** 含义：lane的连接点(虚拟概念)  类型：点  是否显示：否 */
    @JsonProperty("parking_connector")
    private String parkingConnector;

    /** 含义：斑马线  类型：面  是否显示：是 */
    @JsonProperty("parking_crosswalk")
    private String parkingCrosswalk;

    /** 含义：单条车道的骨架线(虚拟概念)  类型：线  是否显示：否 */
    @JsonProperty("parking_lane")
    private String parkingLane;

    /** 含义：整条道路的骨架线(虚拟概念)  类型：线  是否显示：否 */
    @JsonProperty("parking_link")
    private String parkingLink;

    /** 含义：不同link的连接点(虚拟概念)  类型：点  是否显示：否 */
    @JsonProperty("parking_node")
    private String parkingNode;

    /** 含义：接驳区域  类型：点  是否显示：是(点击泊出时) */
    @JsonProperty("parking_out_area")
    private String parkingOutArea;

    /** 含义：停车场轮廓  类型：面  是否显示：是(缩小zoomlevel时) */
    @JsonProperty("parking_outline")
    private String parkingOutline;

    /** 含义：柱子  类型：面  是否显示：是 */
    @JsonProperty("parking_pillar")
    private String parkingPillar;

    /** 含义：(带开口方向的)停车位边框线  类型：线  是否显示：是 */
    @JsonProperty("parking_spot_lines")
    private String parkingSpotLines;

    /** 含义：停车位  类型：面  是否显示：是 */
    @JsonProperty("parking_spots")
    private String parkingSpots;

    /** 含义：道路的路面  类型：面  是否显示：是 */
    @JsonProperty("parking_surface")
    private String parkingSurface;

    // @formatter:on
}
