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
 * try catch无法处理，需要往上层抛的异常，需要把异常栈信息和异常内容抛出
 *
 * @author lumoere
 * @version $Id: SystemRunningException.java, v 0.1 2018-04-17 下午10:20 Exp $
 */
@SuppressWarnings("unused")
public class SystemRunningException extends UnifiedException {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -17178882746751892L;

    /**
     * 带提示内容构造函数
     *
     * @param exMsg 提示内容
     */
    public SystemRunningException(final String exMsg) {
        super(ResponseCodeEnum.SYSTEM_RUNNING_THROWABLE, exMsg);
    }

    /**
     * 带提示内容和异常信息构造函数
     *
     * @param exMsg            提示内容
     * @param contextThrowable 异常信息
     */
    public SystemRunningException(final String exMsg, final Throwable contextThrowable) {
        super(ResponseCodeEnum.SYSTEM_RUNNING_THROWABLE, contextThrowable, exMsg);
    }
}