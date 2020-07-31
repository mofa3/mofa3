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
package io.github.mofa3.lang.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * 响应码
 *
 * @author lumoere
 * @version $Id: ResponseCodeEnum.java, v 0.1 2018-04-13 下午9:19 Exp $
 */
@SuppressWarnings("unused")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResponseCodeEnum {
    /**
     * 调用成功
     */
    RESPONSE_SUCCESS("0000", "SUCCESS", "调用成功"),

    /**
     * 服务不可用
     */
    SERVICE_IS_NOT_AVAILABLE("1000", "SERVICE_IS_NOT_AVAILABLE", "服务不可用"),

    /**
     * 参数校验失败
     */
    PARAMETER_CHECK_FAIL("2001", "PARAMETER_CHECK_FAIL", "参数校验不通过"),

    /**
     * 业务处理失败
     */
    BIZ_PROCESS_FAIL("3001", "BIZ_PROCESS_FAIL", "业务处理失败"),

    /**
     * 第三方调用异常
     */
    THIRD_PARTY_FAIL("3002", "THIRD_PARTY_FAIL", "第三方调用异常"),

    /**
     * Http重试异常
     */
    HTTP_RETRY_FAIL("3003", "HTTP_RETRY_FAIL", "Http重试异常"),

    /**
     * 运行时异常
     */
    SYSTEM_RUNNING_THROWABLE("4001", "SYSTEM_RUNNING_THROWABLE", "运行时异常"),

    /**
     * JSON序列化/反序列化异常
     */
    JSON_SERIALIZED_THROWABLE("4002", "JSON_SERIALIZED_THROWABLE", "JSON序列化/反序列化异常"),

    /**
     * 未分类异常
     */
    UNCLASSIFIED_THROWABLE("9999", "UNCLASSIFIED_THROWABLE", "未分类异常"),

    ;

    /**
     * 枚举编码
     */
    @Getter
    private final String code;

    /**
     * 英文名
     */
    @Getter
    private final String englishName;


    /**
     * 枚举描述信息
     */
    @Getter
    private final String descr;

    public static ResponseCodeEnum getByEnglishName(String englishName) {
        for (ResponseCodeEnum value : ResponseCodeEnum.values()) {
            if (StringUtils.equals(englishName, value.getEnglishName())) {
                return value;
            }
        }
        return null;
    }
}