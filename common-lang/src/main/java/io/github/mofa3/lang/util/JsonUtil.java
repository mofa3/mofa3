/*
 * Copyright 2020 lujing
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.mofa3.lang.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import lombok.experimental.UtilityClass;
import io.github.mofa3.lang.exception.JsonSerializedException;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

/**
 * jackson 实现json、对象的转换操作
 *
 * @author lumoere
 * @version $Id: JsonUtil.java, v 0.1 2018-09-18 下午10:10 Exp $
 */
@UtilityClass
public class JsonUtil {
    private static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.setLocale(Locale.CHINA);
        // Include.NON_NULL 属性为NULL 不序列化
        mapper.setSerializationInclusion(Include.NON_NULL);
        SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 设置默认时区
        mapper.setTimeZone(TimeZone.getDefault());
        // 时间类型格式化
        mapper.setDateFormat(smt);
        // 允许JSON字符串包含非引号控制字符（值小于32的ASCII字符，包含制表符和换行符）
        mapper.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
        // 忽略json字符串中不识别的属性
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 忽略无法转换的对象
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    /**
     * 对象转JSON字符串
     *
     * @param obj 被转换对象
     * @return json字符串
     */
    public static <T> String beanToJson(T obj) {
        if (Objects.isNull(obj)) {
            return null;
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new JsonSerializedException(e, "Bean转Json异常");
        }
    }

    /**
     * JSON字符串转换为对象
     *
     * @param json  json字符串
     * @param claxx 转换类型
     * @param <T>   泛型类型
     * @return 转换后对象
     */
    public static <T> T jsonToBean(String json, Class<T> claxx) {
        try {
            return mapper.readValue(json, claxx);
        } catch (IOException e) {
            throw new JsonSerializedException(e, "Json字符串转Bean异常");
        }
    }

    /**
     * 对象转换map
     *
     * @param obj 对象
     * @param <T> 对象类型
     * @return map
     */
    public static <T> Map beanToMap(T obj) {
        try {
            String json = beanToJson(obj);
            return mapper.readValue(json, Map.class);
        } catch (IOException e) {
            throw new JsonSerializedException(e, "Bean转Map异常");
        }
    }

    /**
     * InputStream转Bean
     *
     * @param in    InputStream
     * @param claxx 转换类型
     * @param <T>   泛型
     * @return T
     */
    public static <T> T streamToBean(InputStream in, Class<T> claxx) {
        if (Objects.isNull(in)) {
            return null;
        }
        try {
            return mapper.readValue(in, claxx);
        } catch (IOException e) {
            throw new JsonSerializedException(e, "InputStream转Bean异常");
        }
    }

    /**
     * InputStream转Bean
     *
     * @param in            InputStream
     * @param typeReference 转换类型
     * @param <T>           泛型
     * @return T
     */
    public static <T> T streamToBean(InputStream in, TypeReference<?> typeReference) {
        if (Objects.isNull(in)) {
            return null;
        }
        try {
            return (T) mapper.readValue(in, typeReference);
        } catch (IOException e) {
            throw new JsonSerializedException(e, "InputStream转Bean异常");
        }
    }


    /**
     * stream转List<Bean>
     *
     * @param in    InputStream
     * @param claxx 转换类型
     * @param <T>   泛型
     * @return list
     */
    public static <T> List<T> streamToList(InputStream in, Class<T> claxx) {
        if (Objects.isNull(in)) {
            return Collections.emptyList();
        }
        try {
            return mapper.readValue(in, listType(claxx));
        } catch (IOException e) {
            throw new JsonSerializedException(e, "InputStream转List<Bean>异常");
        }
    }

    private static CollectionLikeType listType(Class<?> claxx) {
        return mapper.getTypeFactory().constructCollectionLikeType(List.class, claxx);
    }
}
