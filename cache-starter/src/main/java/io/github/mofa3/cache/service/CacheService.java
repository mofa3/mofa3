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
package io.github.mofa3.cache.service;

import java.util.concurrent.TimeUnit;

/**
 * 缓存操作service类
 * 缓存使用过期策略，默认以配置文件缓存过期时间
 * redis.cache.expire-time配置
 *
 * @author ${baizhang}
 * @version $Id: CacheService.java, v 0.1 2018-09-19 下午1:30 Exp $
 */
@SuppressWarnings("unused")
public interface CacheService {

    /**
     * redis set，默认超时时间为配置文件设置
     *
     * @param key   缓存key
     * @param value 缓存对象
     * @param <T>   缓存对象泛型
     */
    <T> void set(String key, T value);

    /**
     * redis set，手动设置超时时间，单位：秒
     *
     * @param key     缓存key
     * @param value   缓存对象
     * @param seconds 超时时间，单位 秒
     * @param <T>     泛型类型
     */
    <T> void set(String key, T value, long seconds);

    /**
     * redis set，自定义超时时间
     *
     * @param key      缓存key
     * @param value    缓存对象
     * @param timeUnit 超时时间单位
     * @param times    超时时间
     * @param <T>      泛型类型
     */
    <T> void set(String key, T value, TimeUnit timeUnit, long times);

    /**
     * 获取缓存对象
     *
     * @param key 缓存key
     * @param <T> 对象类型
     * @return 缓存对象
     */
    <T> T get(String key);

    /**
     * 删除缓存
     *
     * @param key 缓存key
     */
    void delete(String key);
}