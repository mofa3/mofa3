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

import lombok.Data;

import java.io.Serializable;

/**
 * 错误信息上下文
 * 原则上该类应该放error模块，由于依赖引入的层级关系，会出现嵌套依赖的情况，所以放顶层模块
 *
 * @author ${baizhang}
 * @version $Id: ErrorContext.java, v 0.1 2018-04-16 上午11:01 Exp $
 */
@Data
public class ErrorContext implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -171785465778410092L;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用服务器ip
     */
    private String ip;
}