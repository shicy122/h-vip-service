package com.hycan.idn.hvip.facade.impl;

import com.hycan.idn.hvip.constants.CommonConstants;
import com.hycan.idn.hvip.constants.HttpConstants;
import com.hycan.idn.hvip.constants.AvpUriConstants;
import com.hycan.idn.hvip.constants.RedisConstants;
import com.hycan.idn.hvip.facade.IAvpRemoteFacade;
import com.hycan.idn.hvip.pojo.avp.http.req.LoginUserReqVO;
import com.hycan.idn.hvip.pojo.avp.http.req.StationMapReqVO;
import com.hycan.idn.hvip.pojo.avp.http.rsp.AvpResultRspVO;
import com.hycan.idn.hvip.pojo.avp.http.rsp.AvpTokenRspVO;
import com.hycan.idn.hvip.pojo.avp.http.rsp.StationMapRsp;
import com.hycan.idn.hvip.pojo.avp.http.rsp.StationRspVO;
import com.hycan.idn.hvip.pojo.avp.http.rsp.VehicleSecretKeyRspVO;
import com.hycan.idn.hvip.pojo.avp.http.rsp.VersionRspVO;
import com.hycan.idn.hvip.pojo.vip.OkHttpResponseDTO;
import com.hycan.idn.hvip.repository.StationBasicInfoRepository;
import com.hycan.idn.hvip.repository.VehicleBasicInfoRepository;
import com.hycan.idn.hvip.util.JsonUtil;
import com.hycan.idn.hvip.util.OkHttpUtil;
import com.hycan.idn.tsp.common.core.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * AVP HTTP接口包装实现类
 *
 * @author shichongying
 * @datetime 2023-10-20 17:40
 */
@Slf4j
@Service
public class AvpRemoteFacadeImpl implements IAvpRemoteFacade {

    private static final String AVP_RESULT_SUCCESS_STATUS = "OK";
    private static final String AVP_RESULT_SUCCESS_CODE = "10000";
    private static final Integer AVP_TOKEN_VALID_SECOND_DEFAULT = 12 * 60 * 60;
    private static final Integer AVP_VEHICLE_SECRET_VALID_HOUR_DEFAULT = 24;
    private static final Integer AVP_STATION_SECRET_VALID_HOUR_DEFAULT = 24;

    @Value("${avp.url:https://www.avp-cloud-gz.cn:8104}")
    private String endpoint;

    @Value("${avp.username:hycan-ai-parking-2023}")
    private String username;

    @Value("${avp.password:hycan-ai-parking-2023}")
    private String password;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private OkHttpUtil okHttpUtil;

    @Resource
    private StationBasicInfoRepository stationBasicInfoRepository;

    @Resource
    private VehicleBasicInfoRepository vehicleBasicInfoRepository;

    @Override
    public String getOrUpdateVehicleSecret(String vin) {
        String vehicleSecret = stringRedisTemplate.opsForValue().get(RedisConstants.AVP_VEHICLE_SECRET + vin);
        if (StringUtils.hasText(vehicleSecret)) {
            return vehicleSecret;
        }

        vehicleSecret = getVehicleSecretRemote(vin);
        if (!StringUtils.hasText(vehicleSecret)) {
            log.error("AVP接口响应车辆秘钥为空! VIN码={}", vin);
            return null;
        }
        stringRedisTemplate.opsForValue().set(RedisConstants.AVP_VEHICLE_SECRET + vin,
                vehicleSecret, AVP_VEHICLE_SECRET_VALID_HOUR_DEFAULT, TimeUnit.HOURS);
        vehicleBasicInfoRepository.updateVehicleSecret(vin, vehicleSecret);
        return vehicleSecret;

    }

    @Override
    public String getOrUpdateStationSecret(String avpStationCode) {
        String stationSecret = stringRedisTemplate.opsForValue().get(RedisConstants.AVP_STATION_SECRET + avpStationCode);
        if (StringUtils.hasText(stationSecret)) {
            return stationSecret;
        }

        stationSecret = getStationSecretRemote(avpStationCode);
        if (!StringUtils.hasText(stationSecret)) {
            log.error("AVP接口响应场站秘钥为空! AVP场站编号={}", avpStationCode);
            return null;
        }

        int result = stationBasicInfoRepository.updateStationSecret(avpStationCode, stationSecret);
        if (CommonConstants.SUCCESS_FLAG == result) {
            stringRedisTemplate.opsForValue().set(RedisConstants.AVP_STATION_SECRET + avpStationCode,
                    stationSecret, AVP_STATION_SECRET_VALID_HOUR_DEFAULT, TimeUnit.HOURS);
            return stationSecret;
        }
        return null;
    }

    @Override
    public List<StationRspVO> getStationList() {
        try {
            Request request = new Request.Builder()
                    .get()
                    .header(HttpConstants.AVP_AUTHORIZATION, HttpConstants.AVP_BEARER + getAvpAccessToken())
                    .url(endpoint + AvpUriConstants.AVP_PARKING_LIST)
                    .build();
            OkHttpResponseDTO response = okHttpUtil.request(request);
            if (Objects.isNull(response) || !response.isSuccess()) {
                log.error("AVP接口响应失败!");
                return null;
            }

            return JsonUtil.fromArray(getAvpResultData(response.getBody()), StationRspVO.class);
        } catch (Exception e) {
            log.error("获取AVP Token异常, 异常详情={}", ExceptionUtil.getBriefStackTrace(e));
        }
        return null;
    }

    @Override
    public StationMapRsp getStationMap(List<VersionRspVO> parkingFacVersionList) {
        if (CollectionUtils.isEmpty(parkingFacVersionList)) {
            return null;
        }

        try {
            RequestBody body = RequestBody.create(HttpConstants.JSON, JsonUtil.obj2Json(StationMapReqVO.of(parkingFacVersionList)));
            Request request = new Request.Builder()
                    .post(body)
                    .header(HttpConstants.AVP_AUTHORIZATION, HttpConstants.AVP_BEARER + getAvpAccessToken())
                    .url(endpoint + AvpUriConstants.AVP_PARKING_MAP)
                    .build();
            OkHttpResponseDTO response = okHttpUtil.request(request);
            if (Objects.isNull(response) || !response.isSuccess()) {
                log.error("AVP接口响应失败!");
                return null;
            }

            return JsonUtil.fromJson(getAvpResultData(response.getBody()), StationMapRsp.class);
        } catch (Exception e) {
            log.error("获取AVP Token异常, 异常详情={}", ExceptionUtil.getBriefStackTrace(e));
        }
        return null;
    }

    private String getAvpAccessToken() {
        try {
            String accessToken = stringRedisTemplate.opsForValue().get(RedisConstants.AVP_TOKEN);
            if (StringUtils.hasText(accessToken)) {
                return accessToken;
            }

            RequestBody body = RequestBody.create(HttpConstants.JSON, JsonUtil.obj2Json(LoginUserReqVO.of(username, password)));
            Request request = new Request.Builder()
                    .post(body)
                    .url(endpoint + AvpUriConstants.AVP_TOKEN)
                    .build();
            OkHttpResponseDTO response = okHttpUtil.request(request);
            if (Objects.isNull(response) || !response.isSuccess()) {
                log.error("AVP接口响应失败!");
                return null;
            }

            AvpTokenRspVO avpTokenRspVO = JsonUtil.fromJson(getAvpResultData(response.getBody()), AvpTokenRspVO.class);
            if (Objects.isNull(avpTokenRspVO) || !StringUtils.hasText(avpTokenRspVO.getAccessToken())) {
                log.error("AVP接口响应Token为空!");
                return null;
            }

            accessToken = avpTokenRspVO.getAccessToken();
            int expiresIn = avpTokenRspVO.getExpiresIn();
            if (avpTokenRspVO.getExpiresIn() <= 0) {
                expiresIn = AVP_TOKEN_VALID_SECOND_DEFAULT;
            }

            stringRedisTemplate.opsForValue().set(RedisConstants.AVP_TOKEN, accessToken, expiresIn, TimeUnit.SECONDS);
            return accessToken;
        } catch (Exception e) {
            log.error("获取AVP Token异常, 异常详情={}", ExceptionUtil.getBriefStackTrace(e));
        }
        return null;
    }

    private String getStationSecretRemote(String avpStationCode) {
        try {
            Request request = new Request.Builder()
                    .get()
                    .header(HttpConstants.AVP_AUTHORIZATION, HttpConstants.AVP_BEARER + getAvpAccessToken())
                    .url(endpoint + AvpUriConstants.AVP_PARKING_KEY + avpStationCode)
                    .build();
            OkHttpResponseDTO response = okHttpUtil.request(request);
            if (Objects.isNull(response) || !response.isSuccess()) {
                log.error("AVP接口响应失败!");
                return null;
            }

            return JsonUtil.fromJson(getAvpResultData(response.getBody()), String.class);
        } catch (Exception e) {
            log.error("获取AVP Token异常, 异常详情={}", ExceptionUtil.getBriefStackTrace(e));
        }
        return null;
    }

    private String getVehicleSecretRemote(String vin) {
        try {
            Request request = new Request.Builder()
                    .get()
                    .header(HttpConstants.AVP_AUTHORIZATION, HttpConstants.AVP_BEARER + getAvpAccessToken())
                    .url(endpoint + AvpUriConstants.AVP_UMS_DEVICE + vin)
                    .build();
            OkHttpResponseDTO response = okHttpUtil.request(request);
            if (Objects.isNull(response) || !response.isSuccess()) {
                log.error("AVP接口响应失败!");
                return null;
            }

            VehicleSecretKeyRspVO vehicleSecretKeyRspVO = JsonUtil.fromJson(getAvpResultData(response.getBody()), VehicleSecretKeyRspVO.class);
            if (Objects.isNull(vehicleSecretKeyRspVO)) {
                log.error("AVP接口响应车辆秘钥为空! VIN码={}", vin);
                return null;
            }

            return vehicleSecretKeyRspVO.getDeviceToken();
        } catch (Exception e) {
            log.error("获取AVP Token异常, 异常详情={}", ExceptionUtil.getBriefStackTrace(e));
        }
        return null;
    }

    private String getAvpResultData(String result) {
        try {
            AvpResultRspVO avpResultRspVO = JsonUtil.fromJson(result, AvpResultRspVO.class);
            if (Objects.nonNull(avpResultRspVO) && Objects.nonNull(avpResultRspVO.getData())
                    && AVP_RESULT_SUCCESS_STATUS.equals(avpResultRspVO.getStatus())
                    && AVP_RESULT_SUCCESS_CODE.equals(avpResultRspVO.getCode())) {
                return JsonUtil.obj2Json(avpResultRspVO.getData());
            }
            log.error("AVP响应失败, 响应结果={}", avpResultRspVO);
            return null;
        } catch (Exception e) {
            log.error("AVP响应数据格式错误, 响应结果={}, 异常原因={}", result, ExceptionUtil.getBriefStackTrace(e));
        }
        return null;
    }
}

