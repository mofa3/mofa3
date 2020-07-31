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
package io.github.mofa3.lang.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * 应用结果类
 *
 * @author ${guanzhong}
 * @version $Id: Result.java, v 0.1 2017-12-27 下午6:15 Exp $
 */
@Data
@SuppressWarnings("unused")
public class Result<T> implements Serializable {
    /**
     * 序列ID
     */
    private static final long serialVersionUID = -171900173982346605L;

    /**
     * 请求id
     */
    private String requestId;

    /**
     * 本次服务调用是否成功
     */
    private boolean success;

    /**
     * 响应码, 默认为0，表示接口未出现任何异常
     */
    private String code = "0000";

    /**
     * 如果失败，返回失败原因
     */
    private String msg;

    /**
     * 错误上下文
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ErrorContext errorContext;

    /**
     * 结果对象
     */
    private T data;

    /**
     * 默认构造方法
     */
    public Result() {
    }

    /**
     * 全参数构造方法
     *
     * @param success      返回状态
     * @param code         返回code
     * @param msg          返回消息，通常异常情况下使用
     * @param errorContext 异常信息
     * @param data         返回结果对象
     */
    public Result(final boolean success, final String code, final String msg,
                  final ErrorContext errorContext, final T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.errorContext = errorContext;
        this.data = data;
    }

    /**
     * 部分参数构造方法
     *
     * @param success      返回状态
     * @param errorContext 异常信息
     * @param data         返回对象
     */
    public Result(final boolean success, final ErrorContext errorContext, final T data) {
        this.success = success;
        this.errorContext = errorContext;
        this.data = data;
    }
}