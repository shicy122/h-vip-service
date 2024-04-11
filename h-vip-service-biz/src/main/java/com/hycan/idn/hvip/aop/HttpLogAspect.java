package com.hycan.idn.hvip.aop;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.hycan.idn.tsp.common.core.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日志切面
 * @author fay
 */
@Slf4j
@Aspect
@Component
public class HttpLogAspect {

    @Around("@annotation(com.hycan.idn.hvip.aop.HttpLog)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        // 打印请求信息
        writeRequest(joinPoint);
        // 执行目标方法
        Object result = joinPoint.proceed();
        // 打印响应信息
        writeResponse(joinPoint, result, startTime);
        return result;
    }

    private void writeRequest(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(attributes)) {
            return;
        }
        HttpServletRequest request = attributes.getRequest();
        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();
        // 获取类名和方法名
        String className = joinPoint.getTarget().getClass().getName();

        // 获取匿名化处理的字段
        List<String> anonymousFields = getAnonymousFields(joinPoint);
        String methodName = joinPoint.getSignature().getName();
        String body = null;
        if (!HttpMethod.GET.matches(methodName)) {
            body = getRequestBody(request);
            // 匿名化处理响应结果中的指定字段
            for (String anonymousField : anonymousFields) {
                body = anonymizeResponseField(body, anonymousField);
            }
        }

        // 打印请求信息
        if (StringUtils.isNotBlank(body)) {
            log.info("Request - Class: {}, Method: {}, IP: {}, URI: {}, Parameters: {}, Body: {}",
                    className, methodName, ip, uri, getRequestParams(joinPoint, anonymousFields), body);
        } else {
            log.info("Request - Class: {}, Method: {}, IP: {}, URI: {}, Parameters: {}",
                    className, methodName, ip, uri, getRequestParams(joinPoint, anonymousFields));
        }
    }

    private void writeResponse(ProceedingJoinPoint joinPoint, Object result, long startTime) {
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        // 序列化响应结果
        String responseString = serialize(result);
        // 获取匿名化处理的字段
        List<String> anonymousFields = getAnonymousFields(joinPoint);
        // 匿名化处理响应结果中的指定字段
        for (String anonymousField : anonymousFields) {
            responseString = anonymizeResponseField(responseString, anonymousField);
        }
        // 获取类名和方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        // 打印响应信息
        log.info("Response - Class: {}, Method: {}, Result: {}, Elapsed Time: {}ms", className, methodName,
                responseString, elapsedTime);
    }

    public String getRequestBody(HttpServletRequest request) {
        try (InputStream inputStream = request.getInputStream()){
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder body = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
            return body.toString();
        } catch (IOException e) {
            log.error("i/o stream throw exception = {}", ExceptionUtil.getBriefStackTrace(e));
        }
        return null;
    }

    private String getRequestParams(ProceedingJoinPoint joinPoint, List<String> anonymousFields) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String[] parameterNames = methodSignature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        StringBuilder params = new StringBuilder();
        for (int i = 0; i < parameterNames.length; i++) {
            String paramName = parameterNames[i];
            Object paramValue = args[i];
            // 忽略部分参数的匿名化处理
            if (paramValue instanceof ServletRequest || paramValue instanceof ServletResponse
                    || paramValue instanceof MultipartFile) {
                continue;
            }
            String paramValueString = serialize(paramValue);
            if (anonymousFields.contains(paramName)) {
                paramValueString = anonymizeValue(paramValueString);
            }
            params.append(paramName).append("=").append(paramValueString).append("&");
        }
        return params.toString();
    }

    private String serialize(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // 使用自定义的字符串序列化器
            SimpleModule module = new SimpleModule();
            module.addSerializer(String.class, new LargeStringSerializer());
            objectMapper.registerModule(module);
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize object {}", ExceptionUtil.getBriefStackTrace(e));
        }
        return "";
    }

    private List<String> getAnonymousFields(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        HttpLog annotation = method.getAnnotation(HttpLog.class);
        return Arrays.asList(annotation.anonymous());
    }

    private String anonymizeResponseField(String str, String fieldName) {
        String fieldRegex = "(\"" + fieldName + "\"[\\s]*:[\\s]*)(\"[^\"]+\")";
        Pattern pattern = Pattern.compile(fieldRegex);
        Matcher matcher = pattern.matcher(str);
        StringBuffer stringBuffer = new StringBuffer();
        while (matcher.find()) {
            String fieldValue = matcher.group(2);
            String anonymizedValue = anonymizeValue(fieldValue);
            matcher.appendReplacement(stringBuffer, matcher.group(1) + anonymizedValue);
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    private String anonymizeValue(String fieldValue) {
        String value = fieldValue.replaceAll("\"", ""); // 去除字段值中的引号
        String anonymizedValue;
        if (value.length() >= 12) {
            anonymizedValue = "\"" + value.substring(0, 4) + "****" + value.substring(value.length() - 4) + "\"";
        } else if (value.length() >= 8) {
            anonymizedValue = "\"" + value.substring(0, 2) + "****" + value.substring(value.length() - 2) + "\"";
        } else {
            anonymizedValue = "\"****\"";
        }
        return anonymizedValue;
    }

    /**
     * 自定义字符串序列化器
     * maxStrLen 序列化最大字符串长度，默认500，超过的序列化替换为...
     * liveStrLen 序列化保留的字符数，默认0不保留
     */
    private static class LargeStringSerializer extends JsonSerializer<String> {

        private final int maxStrLen;
        private final int liveStrLen;

        public LargeStringSerializer() {
            this(500, 0);
        }

        public LargeStringSerializer (int maxStrLen, int liveStrLen) {
            this.maxStrLen = maxStrLen;
            this.liveStrLen = liveStrLen;
        }

        @Override
        public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            // 超长字符串序列化省略，替换为...
            if (s.length() > maxStrLen && s.length() > liveStrLen) {
                s = s.substring(0, liveStrLen) + "...";
            }
            jsonGenerator.writeString(s);
        }
    }
}