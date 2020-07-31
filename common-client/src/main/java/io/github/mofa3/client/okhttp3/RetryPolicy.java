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

import lombok.Getter;
import lombok.ToString;

/**
 *  请求重试策略
 *
 * @author baizhang
 * @version: RetryPolicy.java, v 0.1 2019-07-30 16:46 Exp $
 */
@Getter
@ToString
public class RetryPolicy {
    /**
     * 默认重试次数：3次
     */
    public static final int DEFAULT_RETRY_COUNT = 3;
    public static final RetryPolicy INSTANCE = new RetryPolicy();

    /**
     * 最大重试次数
     */
    private final int maxAttempts;
    /**
     * 重试时间间隔，单位毫秒
     */
    private final long sleepMillis;

    public RetryPolicy() {
        // 默认重试次数3次，间隔1s
        this(RetryPolicy.DEFAULT_RETRY_COUNT, 1000L);
    }

    public RetryPolicy(int maxAttempts, long sleepMillis) {
        this.maxAttempts = maxAttempts;
        this.sleepMillis = sleepMillis;
    }
}
