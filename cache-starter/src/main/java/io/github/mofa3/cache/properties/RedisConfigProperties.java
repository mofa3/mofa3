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
package io.github.mofa3.cache.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.List;

/**
 * redis 配置属性类
 *
 * @author lumoere
 * @version $Id: RedisConfigProperties.java, v 0.1 2018-04-19 下午3:17 Exp $
 */
@Data
@ConfigurationProperties(prefix = RedisConfigProperties.MOFA_CACHE_REDIS_PREFIX)
public class RedisConfigProperties {

    /**
     * Cluster mode
     */
    public static final String MODEL_CLUSTER = "CLUSTER";

    /**
     * Replicated mode
     */
    public static final String MODEL_REPLICATED = "REPLICATED";

    /**
     * Single instance mode
     */
    public static final String MODEL_SINGLE = "SINGLE";

    /**
     * Master slave mode
     */
    public static final String MODEL_MASTER_SLAVE = "MASTER_SLAVE";

    /**
     * Sentinel mode
     */
    public static final String MODEL_SENTINEL = "SENTINEL";


    public static final String MOFA_CACHE_REDIS_PREFIX = "cache.redis";

    /**
     * redis地址：redis://域名:端口
     * 单实例redis配置，如果redis本身已经做过代理，集群版也使用这个配置（例如阿里云集群版本身已经做过代理，如果未给出集群其他服务地址）
     */
    @Value("#{'${cache.redis.address:redis://127.0.0.1:6379}'.split(',')}")
    private List<String> address;

    /**
     * 主从模式slave配置，address默认为master
     */
    @Value("#{'${cache.redis.slave-address:redis://127.0.0.1:6379}'.split(',')}")
    private List<String> slaveAddress;

    /**
     * redis 服务模式
     *
     * Cluster mode
     * Replicated mode
     * Single instance mode
     * Sentinel mode
     * Master slave mode
     * Proxy mode - This feature available only in Redisson PRO edition.
     */
    private String model;

    /**
     * 集群扫描时间间隔（单位：毫秒）
     * 生效模式：
     * Replicated model
     * Cluster mode
     */
    private int scanInterval = 3000;

    /**
     * 密码
     */
    private String password;

    /**
     * key过期时间（单位：天）
     */
    private Integer expireTime;

    /**
     * 连接池对象
     */
    private PoolConfig poolConfig;

    /**
     * 连接池配置
     */
    @Data
    public static class PoolConfig {
        /**
         * 最小空闲连接数
         */
        private Integer minIdle;

        /**
         * 连接池大小
         */
        private Integer poolSize;

        /**
         * 连接超时时间
         */
        private Integer connectTimeout;

        /**
         * 命令等待超时时间，从命令发送成功开始计时
         */
        private Integer maxWaitTimeout;

        /**
         * 命令失败重试次数
         */
        private Integer retryAttempts;

        /**
         * 命令失败重试时间间隔
         */
        private Integer retryInterval;

    }
}