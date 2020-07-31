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
 * 服务不可用异常，目前用于网关层，熔断异常返回
 *
 * @author ${baizhang}
 * @version $Id: ServiceIsNotAvailableException.java, v 0.1 2018-04-14 下午5:30 Exp $
 */
@SuppressWarnings("unused")
public class ServiceIsNotAvailableException extends UnifiedException {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1711228976142888760L;

    /**
     * 无参构造函数
     * 如果没有特殊的异常提示，直接返回服务不可用即可
     */
    public ServiceIsNotAvailableException() {
        super(ResponseCodeEnum.SERVICE_IS_NOT_AVAILABLE);
    }

    /**
     * 带提示内容构造函数
     *
     * @param exMsg 提示内容
     */
    public ServiceIsNotAvailableException(final String exMsg) {
        super(ResponseCodeEnum.SERVICE_IS_NOT_AVAILABLE, exMsg);
    }

    /**
     * 带提示内容，异常信息构造函数
     *
     * @param contextThrowable 异常信息
     * @param exMsg            提示内容
     */
    public ServiceIsNotAvailableException(final Throwable contextThrowable, final String exMsg) {
        super(ResponseCodeEnum.SERVICE_IS_NOT_AVAILABLE, contextThrowable, exMsg);
    }
}