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

import io.github.mofa3.lang.domain.Result;

/**
 * Controller模板接口
 *
 * @author lumoere
 * @version $Id: ControllerTemplate.java, v 0.1 2018-04-16 下午5:19 Exp $
 */
@SuppressWarnings("unused")
public interface ControllerTemplate<T> {
    /**
     * 接口执行(框架实现)
     *
     * @param action 执行的接口
     * @return 返回结果
     */
    default Result<T> execute(ControllerCallback<T> action) {
        try {
            begin();
            return doBiz(action);
        } finally {
            after();
        }
    }

    /**
     * 接口执行(业务实现)
     *
     * @param action 执行的接口
     * @return 返回结果
     */
    Result<T> doBiz(ControllerCallback<T> action);

    /**
     * 开始
     * 用于上下文初始化
     */
    default void begin() {
    }

    /**
     * 结束
     * 用于上下文清理
     */
    default void after() {
    }
}