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

import lombok.Data;
import lombok.EqualsAndHashCode;
import io.github.mofa3.lang.enums.ResponseCodeEnum;

/**
 * 统一异常处理类
 *
 * @author lumoere
 * @version $Id: UnifiedException.java, v 0.1 2018-04-13 下午9:16 Exp $
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UnifiedException extends RuntimeException {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1716824916931940285L;

    /**
     * 响应码枚举类
     */
    private ResponseCodeEnum responseCodeEnum;

    /**
     * 响应枚举未做覆盖，需要统一异常码后才能实现，所以预留自定义响应码，
     * 响应码区间因该在{@link ResponseCodeEnum}枚举范围内
     */
    private String code;

    /**
     * 抛出异常
     */
    private Throwable contextThrowable;

    /**
     * 只需要返回响应枚举构造函数
     * 仅做提示类返回，不跟踪异常栈信息
     *
     * @param responseCodeEnum 响应枚举
     */
    public UnifiedException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum.getDescr(), null, false, false);
        this.responseCodeEnum = responseCodeEnum;
        this.code = responseCodeEnum.getCode();
    }

    /**
     * 返回响应枚举、提示内容构造函数
     * 仅做提示类返回，不跟踪异常栈信息
     *
     * @param responseCodeEnum 响应枚举
     * @param exMsg            异常提示内容
     */
    public UnifiedException(ResponseCodeEnum responseCodeEnum, String exMsg) {
        super(exMsg, null, false, false);
        this.responseCodeEnum = responseCodeEnum;
        this.code = responseCodeEnum.getCode();
    }

    /**
     * 返回响应枚举、提示内容、异常栈信息构造函数
     *
     * @param responseCodeEnum 响应枚举
     * @param contextThrowable Throwable
     * @param exMsg            异常提示内容
     */
    public UnifiedException(ResponseCodeEnum responseCodeEnum, Throwable contextThrowable,
                            String exMsg) {
        super(exMsg, contextThrowable);
        this.responseCodeEnum = responseCodeEnum;
        this.contextThrowable = contextThrowable;
        this.code = responseCodeEnum.getCode();
    }

    /**
     * 自定义异常code，提示内容
     * 仅做提示类返回，不跟踪异常栈信息
     *
     * @param code  code
     * @param exMsg 提示内容
     */
    public UnifiedException(String code, String exMsg) {
        super(exMsg, null, false, false);
        this.code = code;
    }

    /**
     * 自定义异常code，提示内容
     * 仅做提示类返回，不跟踪异常栈信息
     *
     * @param code  code
     * @param exMsg 提示内容
     */
    public UnifiedException(String code, String exMsg, Throwable contextThrowable) {
        super(exMsg, contextThrowable);
        this.code = code;
    }

}