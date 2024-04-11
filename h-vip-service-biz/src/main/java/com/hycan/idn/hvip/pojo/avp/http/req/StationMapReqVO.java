package com.hycan.idn.hvip.pojo.avp.http.req;

import com.hycan.idn.hvip.pojo.avp.http.rsp.VersionRspVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * AVP高精地图请求对象
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StationMapReqVO implements Serializable {

    /**
     * 时间
     */
    private Long timeStamp;

    /**
     * xm
     */
    private List<VersionRspVO> parkingFacVersionList;

    public static StationMapReqVO of(List<VersionRspVO> versions) {
        StationMapReqVO req = new StationMapReqVO();
        req.setTimeStamp(System.currentTimeMillis());
        req.setParkingFacVersionList(versions);
        return req;
    }
}