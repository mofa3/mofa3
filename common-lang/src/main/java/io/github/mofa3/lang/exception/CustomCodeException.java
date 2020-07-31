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

/**
 * 自定义code异常信息，限于有需要调用服务判断code做处理时使用
 *
 * @author ${baizhang}
 * @version $Id: CustomCodeException.java, v 0.1 2018-07-04 下午10:31 Exp $
 */
@SuppressWarnings("unused")
public class CustomCodeException extends UnifiedException {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1711220108345357120L;

    /**
     * 自定义异常code
     *
     * @param code  code
     * @param exMsg 提示内容
     */
    public CustomCodeException(final String code, final String exMsg) {
        super(code, exMsg);
    }

    /**
     * 自定义异常code，抛出异常栈信息
     *
     * @param code             code
     * @param exMsg            提示内容
     * @param contextThrowable 异常信息
     */
    public CustomCodeException(final String code, final String exMsg,
                               final Throwable contextThrowable) {
        super(code, exMsg, contextThrowable);
    }
}