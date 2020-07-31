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

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import io.github.mofa3.lang.exception.HttpRetryException;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 重试拦截器
 *
 * @author baizhang
 * @version: v 0.1 RetryInterceptor.java, 2019-07-30 16:58 Exp $
 */
@Slf4j
public class RetryInterceptor implements Interceptor {
    private final RetryPolicy retryPolicy;

    public RetryInterceptor(RetryPolicy retryPolicy) {
        this.retryPolicy = retryPolicy;
    }

    @Override
    public Response intercept(final Chain chain) throws IOException {
        Request request = chain.request();
        return exceptionRetry(retryPolicy, () -> chain.proceed(request));
    }

    /**
     * 异常重试
     *
     * @param retryPolicy   重试策略
     * @param retryCallable 回调
     * @param <T>           泛型
     * @return T
     */
    private static <T> T exceptionRetry(RetryPolicy retryPolicy, Callable<T> retryCallable) {
        Throwable lastException = null;
        try {
            for (int i = 0, retryCount = retryPolicy.getMaxAttempts(); i < retryCount; i++) {
                try {
                    return retryCallable.call();
                } catch (Throwable e) {
                    lastException = e;
                }
                log.info("OkHttp请求第{}次，共{}次", (i + 1), retryCount);
                TimeUnit.MILLISECONDS.sleep(retryPolicy.getSleepMillis());
            }
        } catch (Throwable e) {
            throw new HttpRetryException(e, "HTTP请求重试异常");
        }
        throw new HttpRetryException(lastException, "HTTP重试次数用尽，请求失败");
    }
}