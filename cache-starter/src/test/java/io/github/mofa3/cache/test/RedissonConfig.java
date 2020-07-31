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
package io.github.mofa3.cache.test;

import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

/**
 * TODO
 *
 * @author baizhang
 * @version: RedissonConfig.java, v 1.0 2020-03-26 2:03 下午 Exp $
 */
public class RedissonConfig {
    public static Config config() {
        Config config = new Config();
        config.useClusterServers().setScanInterval(2000)
                .addNodeAddress("redis://111.xxx.0.xxx :6380")
                .addNodeAddress("redis://111.xxx.0.xxx :6381")
                .addNodeAddress("redis://111.xxx.0.xxx :6382")
                .addNodeAddress("redis://111.xxx.0.xxx :6383")
                .addNodeAddress("redis://111.xxx.0.xxx :6384")
                .addNodeAddress("redis://111.xxx.0.xxx :6385");
//        // 配置NAT处理网络问题
//        HashMap<String, String> natMap = new HashMap<>();
//        natMap.put("172.16.30.41", "111.xxx.0.xxx");
//        config.useClusterServers().setNatMap(natMap);

        // 最大允许重试次数
        config.useClusterServers().setRetryAttempts(3);
        // 重试间隔时间
        config.useClusterServers().setRetryInterval(100);
        // 尝试成功，数据等待超时时间
        config.useClusterServers().setTimeout(15000);
        // 连接超时时间
        config.useClusterServers().setConnectTimeout(15000);
        // 连接池大小
        config.useClusterServers().setMasterConnectionPoolSize(10);
        config.useClusterServers().setSlaveConnectionPoolSize(10);
        // 最小连接数

        config.useClusterServers().setMasterConnectionMinimumIdleSize(5);
        config.useClusterServers().setSlaveConnectionMinimumIdleSize(5);
        // 序列化指定为 JsonJacksonCodec
        config.setCodec(new JsonJacksonCodec());
        return config;
    }
}