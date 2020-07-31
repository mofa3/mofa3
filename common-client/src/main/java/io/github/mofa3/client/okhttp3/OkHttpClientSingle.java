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
package io.github.mofa3.client.okhttp3;

import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * OkHttp client枚举单例获取，初始化一些配置参数
 *
 * @author baizhang
 * @version: v 0.1 OkHttpClientSingle.java, 2019-07-31 23:07 Exp $
 */
public enum OkHttpClientSingle {
    /**
     * instance
     */
    INSTANCE;
    private OkHttpClient instance;

    /**
     * 连接超时时间，默认值 10秒
     */
    private Integer connectTimeout = 10;

    /**
     * 写超时时间，默认值 10秒
     */
    private Integer writeTimeout = 10;

    /**
     * 读超时时间，默认值 60秒
     */
    private Integer readTimeout = 60;

    OkHttpClientSingle() {
        instance = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                // 默认关闭重试，需要重试可以直接使用重试策略配置
                .retryOnConnectionFailure(false)
                .build();
    }

    public OkHttpClient getInstance() {
        return instance;
    }
}