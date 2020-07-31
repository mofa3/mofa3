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
package io.github.mofa3.cache.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.github.mofa3.cache.annotation.RedisSupportModelConditionalOnProperty;
import io.github.mofa3.cache.enums.RedisModelEnum;
import io.github.mofa3.cache.properties.RedisConfigProperties;

/**
 * 检查redis服务模式是否支持，不支持则初始化bean throw异常提示
 *
 * @author baizhang
 * @version: RedissonUnSupportModelConfig.java, v 1.0 2020-03-27 7:38 下午 Exp $
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(value = RedisConfigProperties.class)
@ConditionalOnProperty(prefix = "cache.redis.", name = {"address", "password"})
@RedisSupportModelConditionalOnProperty(key= "cache.redis.model", supportModel = {RedisModelEnum.CLUSTER, RedisModelEnum.SINGLE})
public class RedissonUnSupportModelConfig {
    @Bean
    public RedissonClient redissonClient() {
        throw new ExceptionInInitializerError("redisson model不支持该模式配置，【cache.redis.model】值为：single或者cluster");
    }
}