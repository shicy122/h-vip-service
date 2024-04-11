package com.hycan.idn.hvip.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.hycan.idn.tsp.common.core.exception.CommonBizException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.*;

/**
 * JSON转换工具类
 *
 * @author shichongying
 * @datetime 2023-10-23 14:46
 */
public class JsonUtil {

    private JsonUtil() {
    }

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final ObjectMapper OBJECT_MAPPER_2 = new ObjectMapper();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    //convertValue方法输入的是对象，而不是字符串
    //readValue方法输入的是字符串，而不是对象

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER_2.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER_2.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    public static byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }

    public static Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }

    public static String obj2Json(Object obj) {
        if (ObjectUtils.isEmpty(obj)) {
            return "";
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new CommonBizException(e.getMessage());
        }
    }

    public static <T> T obj2bean(Object source, Class<T> clazz) {
        if (null == source) {
            return null;
        }
        return OBJECT_MAPPER.convertValue(source, clazz);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> json2Map(String jsonString) {
        if (StringUtils.isBlank(jsonString)) {
            return Collections.emptyMap();
        }
        try {
            return OBJECT_MAPPER.readValue(jsonString, Map.class);
        } catch (Exception e) {
            throw new CommonBizException(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> bean2Map(Object obj) {
        if (obj == null) {
            return Collections.emptyMap();
        }
        return OBJECT_MAPPER.convertValue(obj, Map.class);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> bean2MapNotNull(Object obj) {
        if (obj == null) {
            return Collections.emptyMap();
        }
        return objectMapper.convertValue(obj, Map.class);
    }

    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> bean2MapListNotNull(List<Object> objList) {
        if (objList == null) {
            return Collections.emptyList();
        }
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Object obj : objList) {
            mapList.add(objectMapper.convertValue(obj, Map.class));
        }
        return mapList;
    }

    public static Map<String, Object> bean2Map(Object obj, String... disIgnoreFields) {
        Map<String, Object> objectAsMap = bean2Map(obj);
        if (disIgnoreFields == null) {
            return objectAsMap;
        }
        for (String fieldName : disIgnoreFields) {
            objectAsMap.remove(fieldName);
        }
        return objectAsMap;
    }

    public static Map<String, Object> bean2Map(Object obj, List<String> addInFields) {
        Map<String, Object> objectAsMap = bean2Map(obj);
        if (addInFields == null) {
            return objectAsMap;
        }
        Map<String, Object> result = new HashMap<>();
        for (String fieldName : addInFields) {
            if (objectAsMap.containsKey(fieldName)) {
                result.put(fieldName, objectAsMap.get(fieldName));
            }
        }
        return result;
    }

    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(jsonString, clazz);
        } catch (Exception e) {
            throw new CommonBizException(e.getMessage());
        }
    }

    public static <T> T from_Json(String jsonString, Class<T> clazz) {
        try {
            return OBJECT_MAPPER_2.readValue(jsonString, clazz);
        } catch (Exception e) {
            throw new CommonBizException(e.getMessage());
        }
    }

    public static <T> List<T> fromArray(String jsonString, Class<T> clazz) {
        if (StringUtils.isBlank(jsonString)) {
            return Collections.emptyList();
        }
        try {
            JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(List.class, clazz);
            return OBJECT_MAPPER.readValue(jsonString, javaType);
        } catch (Exception e) {
            throw new CommonBizException(e.getMessage());
        }
    }

    public static <T> List<T> from_array(String jsonString, Class<T> clazz) {
        try {
            if (StringUtils.isBlank(jsonString)) {
                return Collections.emptyList();
            }
            JavaType javaType = OBJECT_MAPPER_2.getTypeFactory().constructParametricType(List.class, clazz);
            return OBJECT_MAPPER_2.readValue(jsonString, javaType);
        } catch (Exception e) {
            throw new CommonBizException(e.getMessage());
        }
    }

    public static <R, S> Object toBeanList(List<S> sourceList, Class<R> clazz) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }
        List<R> resultList = new ArrayList<>();
        for (S s : sourceList) {
            resultList.add(obj2bean(s, clazz));
        }
        return resultList;
    }
}