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
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.github.mofa3.cache.properties.RedisConfigProperties;

/**
 * redisson cluster config
 * redis 服务模式为集群，初始化配置
 *
 * @author ${baizhang}
 * @version $Id: RedissonClusterConfiguration.java, v 0.1 2020-03-27 下午1:47 Exp $
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(value = RedisConfigProperties.class)
@ConditionalOnProperty(prefix = "cache.redis.", name = {"address", "password"})
@ConditionalOnExpression("'${cache.redis.model}'.equalsIgnoreCase('CLUSTER')")
public class RedissonClusterConfiguration {
    private final RedisConfigProperties redisConfigProperties;

    public RedissonClusterConfiguration(ObjectProvider<RedisConfigProperties> redisConfigPropertiesProvider) {
        this.redisConfigProperties = redisConfigPropertiesProvider.getIfUnique();
    }

    /**
     * 集群模式配置
     * 暂时不使用
     * 
     * @return
     */
    public Config config() {
        Config config = new Config();
        config.useClusterServers().setScanInterval(redisConfigProperties.getScanInterval());
        for(String address: redisConfigProperties.getAddress()){
            config.useClusterServers().addNodeAddress(address.trim());
        }
        if (StringUtils.isNotBlank(redisConfigProperties.getPassword())) {
            // 密码
            config.useClusterServers().setPassword(redisConfigProperties.getPassword());
        }
        // 最大允许重试次数
        config.useClusterServers().setRetryAttempts(redisConfigProperties.getPoolConfig().getRetryAttempts());
        // 重试间隔时间
        config.useClusterServers().setRetryInterval(redisConfigProperties.getPoolConfig().getRetryInterval());
        // 尝试成功，数据等待超时时间
        config.useClusterServers().setTimeout(redisConfigProperties.getPoolConfig().getMaxWaitTimeout());
        // 连接超时时间
        config.useClusterServers().setConnectTimeout(redisConfigProperties.getPoolConfig().getConnectTimeout());
        // 连接池大小
        config.useClusterServers().setMasterConnectionPoolSize(redisConfigProperties.getPoolConfig().getPoolSize());
        config.useClusterServers().setSlaveConnectionPoolSize(redisConfigProperties.getPoolConfig().getPoolSize());
        // 最小连接数
        config.useClusterServers().setMasterConnectionMinimumIdleSize(redisConfigProperties.getPoolConfig().getMinIdle());
        config.useClusterServers().setSlaveConnectionMinimumIdleSize(redisConfigProperties.getPoolConfig().getMinIdle());
        // 序列化指定为 JsonJacksonCodec
        config.setCodec(new JsonJacksonCodec());
        log.info("ClusterServers：{}", config.useClusterServers().getNodeAddresses());
        return config;
    }


    @Bean
    public RedissonClient redissonClient() {
        log.info("RedissonClusterConfiguration init....");
        return Redisson.create(config());
    }

}