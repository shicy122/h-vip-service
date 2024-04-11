package com.hycan.idn.hvip.pojo.avp.http.rsp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 高精地图响应对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StationMapRsp implements Serializable {

    private Long timeStamp;

    private List<ParkingFacNewReleaseRspVO> parkingFacNewReleaseList;

}