package com.hycan.idn.hvip.pojo.mp.http.rsp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hycan.idn.hvip.entity.StationMapInfoEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * 场站高精地图响应对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "场站高精地图响应对象")
public class StationMapRspVO {

    @ApiModelProperty(value = "服务编号")
    @JsonProperty("station_code")
    private String stationCode;

    @ApiModelProperty(value = "箭头")
    @JsonProperty("map_arrow")
    private String mapArrow;

    @ApiModelProperty(value = "道路路网")
    @JsonProperty("map_boundary")
    private String mapBoundary;

    @ApiModelProperty(value = "减速带")
    @JsonProperty("map_bump")
    private String mapBump;

    @ApiModelProperty(value = "减速带区域横向的中心线")
    @JsonProperty("map_bump_line")
    private String mapBumpLine;

    @ApiModelProperty(value = "停车场中心点")
    @JsonProperty("map_center")
    private String mapCenter;

    @ApiModelProperty(value = "lane的连接点(虚拟概念)")
    @JsonProperty("map_connector")
    private String mapConnector;

    @ApiModelProperty(value = "斑马线")
    @JsonProperty("map_crosswalk")
    private String mapCrosswalk;

    @ApiModelProperty(value = "单条车道的骨架线(虚拟概念)")
    @JsonProperty("map_lane")
    private String mapLane;

    @ApiModelProperty(value = "整条道路的骨架线(虚拟概念)")
    @JsonProperty("map_link")
    private String mapLink;

    @ApiModelProperty(value = "不同link的连接点(虚拟概念)")
    @JsonProperty("map_node")
    private String mapNode;

    @ApiModelProperty(value = "接驳区域")
    @JsonProperty("map_out_area")
    private String mapOutArea;

    @ApiModelProperty(value = "停车场轮廓")
    @JsonProperty("map_outline")
    private String mapOutline;

    @ApiModelProperty(value = "柱子")
    @JsonProperty("map_pilar")
    private String mapPilar;

    @ApiModelProperty(value = "(带开口方向的)停车位边框线")
    @JsonProperty("map_spot_lines")
    private String mapSpotLines;

    @ApiModelProperty(value = "停车位")
    @JsonProperty("map_spots")
    private String mapSpots;

    @ApiModelProperty(value = "道路的路面")
    @JsonProperty("map_surface")
    private String mapSurface;

    public static StationMapRspVO of(StationMapInfoEntity entity) {
        StationMapRspVO rsp = new StationMapRspVO();
        BeanUtils.copyProperties(entity, rsp);
        return rsp;
    }
}