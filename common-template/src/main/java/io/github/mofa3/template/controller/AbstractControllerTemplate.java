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
package io.github.mofa3.template.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.github.mofa3.lang.common.constant.HttpConstants;
import io.github.mofa3.lang.domain.Result;
import io.github.mofa3.lang.exception.BizProcessException;
import io.github.mofa3.lang.exception.CheckParamException;
import io.github.mofa3.lang.exception.CustomCodeException;
import io.github.mofa3.lang.exception.ServiceIsNotAvailableException;
import io.github.mofa3.lang.exception.SystemRunningException;
import io.github.mofa3.lang.exception.ThirdPartyException;
import io.github.mofa3.lang.util.ErrorUtil;

/**
 * Controller操作模板
 * 需要获取应用名称，使用了Spring boot的默认配置应用名称，最好使用Spring boot项目，并有相应配置
 *
 * @author lumoere
 * @version $Id: AbstractControllerTemplate.java, v 0.1 2018-04-16 下午5:24 Exp $
 */
@Slf4j
@Component("AbstractControllerTemplate")
@SuppressWarnings({"unused", "unchecked"})
public class AbstractControllerTemplate<T> implements ControllerTemplate<T> {
    @Value("${spring.application.name}")
    private String appName;

    @Override
    public Result<T> doBiz(final ControllerCallback<T> action) {
        Result<T> result = new Result<>();
        String requestId = MDC.get(HttpConstants.REQUEST_ID);
        if(StringUtils.isNotBlank(requestId)){
            result.setRequestId(requestId);
        }
        try {
            //检查参数
            action.checkParam();
            // 构建上下文
            action.buildContext();
            // 幂等处理(决策无幂等操作)
            action.checkConcurrent();
            // 执行
            final T execute = action.execute();
            // 返回
            result.setData(execute);
            result.setSuccess(true);
            return result;
        } catch (CheckParamException ex) {
            log.error("参数校验不通过：{}", ex.getMessage());
            return ErrorUtil.buildCheckParam(result, ex, appName);
        } catch (BizProcessException ex) {
            log.error("业务处理异常：{}", ex.getMessage());
            return ErrorUtil.buildBizProcess(result, ex, appName);
        } catch (CustomCodeException ex) {
            log.error("自定义Code异常：{}", ex.getMessage());
            return ErrorUtil.buildCustomCode(result, ex.getCode(), ex, appName);
        } catch (SystemRunningException ex) {
            log.error("运行时异常：{}", ex.getMessage(), ex);
            return ErrorUtil.buildSystemRunning(result, ex, appName);
        } catch (ThirdPartyException ex) {
            log.error("第三方调用异常：{}", ex.getMessage(), ex);
            return ErrorUtil.buildThirdParty(result, ex, appName);
        } catch (ServiceIsNotAvailableException ex) {
            log.error("服务不可用：{}", ex.getMessage(), ex);
            return ErrorUtil.buildServiceIsNotAvailable(result, ex, appName);
        } catch (Throwable ex) {
            log.error("System Error：{}", ex.getMessage(), ex);
            return ErrorUtil.buildThrowable(result, ex, appName);
        }
    }
}