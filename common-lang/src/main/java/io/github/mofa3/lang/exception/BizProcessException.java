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
 * 业务处理异常
 * 业务处理异常提示与检查参数类似，但是有区别于参数校验异常
 *
 * @author ${baizhang}
 * @version $Id: BizProcessException.java, v 0.1 2018-04-14 下午5:38 Exp $
 */
@SuppressWarnings("unused")
public class BizProcessException extends UnifiedException {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1711200865142276570L;

    /**
     * 无参构造函数
     */
    public BizProcessException() {
        super(ResponseCodeEnum.BIZ_PROCESS_FAIL);
    }

    /**
     * 带提示内容构造函数
     * 推荐使用，提示不满足业务需求的提示信息即可
     *
     * @param exMsg 提示内容
     */
    public BizProcessException(final String exMsg) {
        super(ResponseCodeEnum.BIZ_PROCESS_FAIL, exMsg);
    }

}