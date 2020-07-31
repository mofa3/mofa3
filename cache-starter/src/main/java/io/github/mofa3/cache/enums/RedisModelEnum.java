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
package io.github.mofa3.cache.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * TODO
 *
 * @author baizhang
 * @version: RedisModelEnum.java, v 1.0 2020-03-30 11:16 上午 Exp $
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RedisModelEnum {
    /**
     * 集群模式
     */
    CLUSTER("cluster", "CLUSTER", "cluster mode"),

    /**
     * 单实例模式
     */
    SINGLE("single", "SINGLE", "single model"),

    /**
     * 主从模式
     */
    MASTER_SLAVE("master_slave", "MASTER_SLAVE", "master_slave model"),

    /**
     * 哨兵模式
     */
    SENTINEL("sentinel", "SENTINEL", "sentinel model"),

    /**
     * 复制模式
     */
    REPLICATED("replicated", "REPLICATED", "replicated model"),
    ;
    /**
     * 枚举编码
     */
    @Getter
    private final String code;

    /**
     * 枚举值
     */
    @Getter
    private final String value;

    /**
     * 枚举描述信息
     */
    @Getter
    private final String descr;

    public static RedisModelEnum getByCode(String code) {
        for (RedisModelEnum value : RedisModelEnum.values()) {
            if (StringUtils.equals(code, value.getCode())) {
                return value;
            }
        }
        return null;
    }
}