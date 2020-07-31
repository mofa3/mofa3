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
 * 第三方调用异常
 * 第三方调用异常，非正常返回等提示信息，建议带上异常信息抛出，方便问题排查
 *
 * @author ${baizhang}
 * @version $Id: ThirdPartyException.java, v 0.1 2018-04-14 下午6:20 Exp $
 */
@SuppressWarnings("unused")
public class ThirdPartyException extends UnifiedException {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -171785465142244532L;

    /**
     * 无参构造函数
     */
    public ThirdPartyException() {
        super(ResponseCodeEnum.THIRD_PARTY_FAIL);
    }

    /**
     * 带提示内容构造函数
     *
     * @param exMsg 提示内容
     */
    public ThirdPartyException(final String exMsg) {
        super(ResponseCodeEnum.THIRD_PARTY_FAIL, exMsg);
    }

    /**
     * 带提示内容和异常信息构造函数
     *
     * @param exMsg            提示内容
     * @param contextThrowable 异常信息
     */
    public ThirdPartyException(final String exMsg, final Throwable contextThrowable) {
        super(ResponseCodeEnum.THIRD_PARTY_FAIL, contextThrowable, exMsg);
    }
}