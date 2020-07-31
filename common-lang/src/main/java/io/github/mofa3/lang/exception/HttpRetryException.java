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
package io.github.mofa3.lang.exception;


import io.github.mofa3.lang.enums.ResponseCodeEnum;

/**
 * Http重试异常
 *
 * @author baizhang
 * @version: v 0.1 HttpRetryException.java, 2019-07-30 17:02 Exp $
 */
@SuppressWarnings("unused")
public class HttpRetryException extends UnifiedException {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -17118904983992570L;

    /**
     * 带提示内容，异常信息构造函数
     *
     * @param contextThrowable 异常信息
     * @param exMsg            提示内容
     */
    public HttpRetryException(final Throwable contextThrowable, final String exMsg) {
        super(ResponseCodeEnum.HTTP_RETRY_FAIL, contextThrowable, exMsg);
    }
}