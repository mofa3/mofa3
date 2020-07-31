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
package io.github.mofa3.lang.util;

import io.github.mofa3.lang.enums.ResponseCodeEnum;
import io.github.mofa3.lang.exception.BizProcessException;
import io.github.mofa3.lang.exception.CheckParamException;
import io.github.mofa3.lang.exception.CustomCodeException;
import io.github.mofa3.lang.exception.ServiceIsNotAvailableException;
import io.github.mofa3.lang.exception.SystemRunningException;
import io.github.mofa3.lang.exception.ThirdPartyException;
import io.github.mofa3.lang.domain.ErrorContext;
import io.github.mofa3.lang.domain.Result;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 错误信息构建工具类
 *
 * @author lumoere
 * @version $Id: ErrorUtil.java, v 0.1 2018-04-16 上午11:25 Exp $
 */
@SuppressWarnings("unused")
public class ErrorUtil {

    /**
     * 构建信息上下文
     *
     * @param appName  应用名称
     * @param errorMsg 错误信息
     * @return 错误上下文
     */
    public static ErrorContext buildContext(String appName, String errorMsg) {
        ErrorContext context = new ErrorContext();
        context.setAppName(appName);
        context.setErrorMsg(errorMsg);
        try {
            context.setIp(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            context.setIp("UNKNOWNHOST");
        }
        return context;
    }

    /**
     * 构建检查参数异常返回对象
     *
     * @param result  Result对象
     * @param e       异常信息
     * @param appName appName
     * @return Result对象
     */
    public static Result buildCheckParam(Result result, CheckParamException e, String appName) {
        result.setSuccess(false);
        result.setMsg(e.getMessage());
        result.setCode(e.getResponseCodeEnum().getCode());
        return result;
    }

    /**
     * 构建业务处理失败异常返回对象
     *
     * @param result  Result
     * @param e       异常信息
     * @param appName 应用名称
     * @return Result对象
     */
    public static Result buildBizProcess(Result result, BizProcessException e, String appName) {
        result.setSuccess(false);
        result.setMsg(e.getMessage());
        result.setCode(e.getResponseCodeEnum().getCode());
        return result;
    }

    /**
     * 系统运行时异常
     *
     * @param result  Result
     * @param e       异常信息
     * @param appName 应用名称
     * @return Result对象
     */
    public static Result buildSystemRunning(Result result, SystemRunningException e,
                                            String appName) {
        result.setSuccess(false);
        result.setMsg(e.getMessage());
        result.setCode(e.getResponseCodeEnum().getCode());
        return result;
    }

    /**
     * 自定义异常code
     *
     * @param result  Result
     * @param code    code
     * @param e       异常信息
     * @param appName 应用名称
     * @return Result
     */
    public static Result buildCustomCode(Result result, String code, CustomCodeException e,
                                         String appName) {
        result.setSuccess(false);
        result.setMsg(e.getMessage());
        result.setCode(code);
        return result;
    }

    /**
     * 构建服务不可用异常返回对象
     *
     * @param result  Result
     * @param e       异常信息
     * @param appName 应用名称
     * @return Result对象
     */
    public static Result buildServiceIsNotAvailable(Result result, ServiceIsNotAvailableException e,
                                                    String appName) {
        result.setSuccess(false);
        result.setMsg(e.getMessage());
        result.setCode(e.getResponseCodeEnum().getCode());
        return result;
    }

    /**
     * 构建第三方调用异常返回对象
     *
     * @param result  Result
     * @param e       异常信息
     * @param appName 应用名称
     * @return Result对象
     */
    public static Result buildThirdParty(Result result, ThirdPartyException e, String appName) {
        result.setSuccess(false);
        result.setMsg(e.getMessage());
        result.setCode(e.getResponseCodeEnum().getCode());
        return result;
    }

    /**
     * 构建其他异常返回对象
     *
     * @param result  Result
     * @param e       异常信息
     * @param appName 应用名称
     * @return Result对象
     */
    public static Result buildThrowable(Result result, Throwable e, String appName) {
        result.setSuccess(false);
        result.setMsg("系统异常");
        result.setCode(ResponseCodeEnum.UNCLASSIFIED_THROWABLE.getCode());
        return result;
    }

}