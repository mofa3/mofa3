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
package io.github.mofa3.cache.service.impl;

import lombok.AllArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import io.github.mofa3.cache.properties.RedisConfigProperties;
import io.github.mofa3.cache.service.CacheService;

import java.util.concurrent.TimeUnit;

/**
 * redisson 缓存实现
 *
 * @author lumoere
 * @version $Id: CacheServiceImpl.java, v 0.1 2018-09-19 下午2:07 Exp $
 */
@AllArgsConstructor
@Service
public class CacheServiceImpl implements CacheService {
    private final RedisConfigProperties redisConfigProperties;
    private final RedissonClient redissonClient;

    @Override
    public <T> void set(final String key, final T value) {
        set(key, value, TimeUnit.DAYS, redisConfigProperties.getExpireTime());
    }

    @Override
    public <T> void set(final String key, final T value, final long seconds) {
        set(key, value, TimeUnit.SECONDS, seconds);
    }

    @Override
    public <T> void set(final String key, final T value, final TimeUnit timeUnit, final long times) {
        redissonClient.getBucket(key).set(value, times, timeUnit);
    }

    @Override
    public <T> T get(final String key) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    @Override
    public void delete(final String key) {
        redissonClient.getBucket(key).delete();
    }

}