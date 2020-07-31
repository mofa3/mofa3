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

/**
 * Controller模板方法回调接口
 * 幂等控制，有写入操作的情况下，要求必须实现方法细节
 *
 * @author lumoere
 * @version $Id: ControllerCallback.java, v 0.1 2018-04-16 下午5:14 Exp $
 */
public interface ControllerCallback<T> {
    /**
     * 检查参数
     */
    void checkParam();

    /**
     * 构建上下文
     * 目的：起到承上启下的作用，将外部参数转换为内部execute需要的参数
     */
    default void buildContext() {
    }

    /**
     * 幂等控制
     * 幂等是针对有写入的操作要求必须实现的方法
     */
    default void checkConcurrent() {
    }

    /**
     * 执行接口
     *
     * @return 结果
     */
    T execute();
}