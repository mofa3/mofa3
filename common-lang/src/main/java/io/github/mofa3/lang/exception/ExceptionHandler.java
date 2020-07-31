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

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import io.github.mofa3.lang.domain.Result;
import io.github.mofa3.lang.enums.ResponseCodeEnum;

/**
 * 非模板方式全局异常处理
 *
 * @author ${baizhang}
 * @version $Id: ExceptionHandler.java, v 0.1 2018-07-16 上午11:03 Exp $
 */
@Slf4j
@RestControllerAdvice
@SuppressWarnings("unused")
public class ExceptionHandler {

    /**
     * 参数校验
     *
     * @param ex 异常对象
     * @return Result
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(CheckParamException.class)
    public Result checkParamException(CheckParamException ex) {
        log.error("参数检查异常：", ex);
        return buildResult(ResponseCodeEnum.PARAMETER_CHECK_FAIL.getCode(), ex.getMessage());
    }

    /**
     * 业务处理
     *
     * @param ex 异常对象
     * @return Result
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(BizProcessException.class)
    public Result bizProcessException(BizProcessException ex) {
        log.error("业务处理异常：", ex);
        return buildResult(ResponseCodeEnum.BIZ_PROCESS_FAIL.getCode(), ex.getMessage());
    }

    /**
     * 自定义异常
     *
     * @param ex 异常对象
     * @return Result
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(CustomCodeException.class)
    public Result customCodeException(CustomCodeException ex) {
        log.error("自定义异常code：", ex);
        return buildResult(ex.getCode(), ex.getMessage());
    }

    /**
     * 服务不可用异常
     *
     * @param ex 异常对象
     * @return Result
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(ServiceIsNotAvailableException.class)
    public Result serviceIsNotAvailableException(ServiceIsNotAvailableException ex) {
        log.error("服务不可用异常：", ex);
        return buildResult(ResponseCodeEnum.SERVICE_IS_NOT_AVAILABLE.getCode(), ex.getMessage());
    }

    /**
     * 服务不可用异常
     *
     * @param ex 异常对象
     * @return Result
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(SystemRunningException.class)
    public Result systemRunningException(SystemRunningException ex) {
        log.error("系统运行时异常：", ex);
        return buildResult(ResponseCodeEnum.SERVICE_IS_NOT_AVAILABLE.getCode(), ex.getMessage());
    }

    /**
     * 第三方接口调用异常
     *
     * @param ex 异常对象
     * @return Result
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(ThirdPartyException.class)
    public Result thirdPartyException(ThirdPartyException ex) {
        log.error("第三方接口调用异常：", ex);
        return buildResult(ResponseCodeEnum.THIRD_PARTY_FAIL.getCode(), ex.getMessage());
    }

    /**
     * 其他异常
     *
     * @param ex 异常对象
     * @return Result
     */
    @ResponseBody
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public Result exception(Exception ex) {
        log.error("未分类异常：", ex);
        return buildResult(ResponseCodeEnum.UNCLASSIFIED_THROWABLE.getCode(), "系统异常");
    }

    /**
     * 返回对象build
     *
     * @param code 异常码
     * @param msg  异常内容
     * @return Result
     */
    private Result buildResult(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setSuccess(false);
        return result;
    }
}