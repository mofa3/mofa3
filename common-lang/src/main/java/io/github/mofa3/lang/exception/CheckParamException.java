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
 * 参数校验异常，作为参数校验
 * 使用时建议把提示信息输出，告知调用者补全必须参数
 * 当前异常类仅作为参数校验失败的提示，没有必要将异常信息抛出给上层服务，所以只需要验参提示即可
 *
 * @author lumoere
 * @version $Id: CheckParamException.java, v 0.1 2018-04-13 下午9:11 Exp $
 */
@SuppressWarnings("unused")
public class CheckParamException extends UnifiedException {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1711220966142857120L;

    /**
     * 带提示内容构造函数
     * 推荐使用，但是不会把异常信息传递给上层服务，只提示定制返回内容
     * 也不会输出异常堆栈信息，只作为接口返回提示内容，不能用于需要跟踪异常栈信息的异常抛出
     *
     * @param exMsg 提示内容
     */
    public CheckParamException(final String exMsg) {
        super(ResponseCodeEnum.PARAMETER_CHECK_FAIL, exMsg);
    }

}