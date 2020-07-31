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
package io.github.mofa3.test.okhttp;

import okhttp3.OkHttpClient;
import io.github.mofa3.lang.exception.ThirdPartyException;

import java.util.concurrent.TimeUnit;

/**
 * 单例的HttpClient连接池
 *
 * @author ${baizhang}
 * @version $Id: HttpClientSingle.java, v 0.1 2018-04-19 下午5:57 Exp $
 */
@SuppressWarnings("unused")
public class OkHttpClientPoolSingle {
    private static volatile OkHttpClient instance = null;
    private static Integer connectTimeout_time = 10;
    private static Integer writeTimeout_time = 10;
    private static Integer readTimeout_time = 30;

    private OkHttpClientPoolSingle() {
    }

    public static OkHttpClient getInstance() {
        try {
            if (null == instance) {
                synchronized (OkHttpClientPoolSingle.class) {
                    System.out.println("synchronized");
                    if (null == instance) {
                        System.out.println("init");
                        instance = new OkHttpClient.Builder()
                                .connectTimeout(connectTimeout_time, TimeUnit.SECONDS)
                                .writeTimeout(writeTimeout_time, TimeUnit.SECONDS)
                                .readTimeout(readTimeout_time, TimeUnit.SECONDS)
                                .retryOnConnectionFailure(true)
                                .build();
                    }
                }
            }
        } catch (Exception e) {
            throw new ThirdPartyException(e.getMessage(), e.getCause());
        }
        return instance;
    }
}