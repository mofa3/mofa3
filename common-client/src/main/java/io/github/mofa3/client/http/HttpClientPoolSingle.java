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
package io.github.mofa3.client.http;

import io.github.mofa3.client.builder.HttpBuilder;
import io.github.mofa3.lang.exception.ThirdPartyException;

/**
 * 单例的HttpClient连接池
 * <p>
 * 推荐使用{@link io.github.mofa3.client.okhttp3.OkHttpRequest}
 *
 * @author ${baizhang}
 * @version $Id: HttpClientSingle.java, v 0.1 2018-04-19 下午5:57 Exp $
 */
@Deprecated
@SuppressWarnings("unused")
public class HttpClientPoolSingle {
    private static volatile HttpBuilder instance = null;

    private HttpClientPoolSingle() {
    }

    public static HttpBuilder getInstance() {
        try {
            if (null == instance) {
                synchronized (HttpClientPoolSingle.class) {
                    if (null == instance) {
                        instance = HttpBuilder.custom().pool(100, 10);
                    }
                }
            }
        } catch (Exception e) {
            throw new ThirdPartyException(e.getMessage(), e.getCause());
        }
        return instance;
    }
}