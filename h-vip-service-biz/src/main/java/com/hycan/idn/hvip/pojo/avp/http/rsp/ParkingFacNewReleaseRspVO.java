package com.hycan.idn.hvip.pojo.avp.http.rsp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 高精地图版本
 *
 * @author shichongying
 * @datetime 2023-10-21 11:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingFacNewReleaseRspVO implements Serializable {

    private VersionRspVO newVersion;

    private Boolean haveNewVersion;

    private UrlsRspVO urls;
}
