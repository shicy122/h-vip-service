package com.hycan.idn.hvip.util;

import com.hycan.idn.hvip.pojo.vip.OkHttpResponseDTO;
import com.hycan.idn.tsp.common.core.exception.CommonBizException;
import com.hycan.idn.tsp.common.core.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * OK HTTP工具类
 *
 * @author shichongying
 * @datetime 2023-10-23 14:46
 */
@Slf4j
@Component
public class OkHttpUtil {

    @Resource
    private OkHttpClient okHttpClient;

    /**
     * request请求入口，请求的返回值这里处理的比较简单，如果需要响应数据，这里需要封装一个ResponseDTO
     *
     * @return 请求成功/失败
     */
    public OkHttpResponseDTO request(Request request) throws CommonBizException {
        try (Response response = okHttpClient.newCall(request).execute()) {
            int status = response.code();
            OkHttpResponseDTO okHttpResponseDTO = new OkHttpResponseDTO();
            okHttpResponseDTO.setCode(status);
            okHttpResponseDTO.setSuccess(HttpStatus.valueOf(status).is2xxSuccessful());
            if (Objects.nonNull(response.body())) {
                okHttpResponseDTO.setBody(response.body().string());
            }
            return okHttpResponseDTO;
        } catch (Exception e) {
            log.error("HTTP请求异常, URL=[{}], Method=[{}], 异常详情=[{}]",
                    request.url(), request.method(), ExceptionUtil.getExceptionCause(e));
        }
        return null;
    }
}