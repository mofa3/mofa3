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
package io.github.mofa3.lang.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * 加减时间选择枚举类
 *
 * @author ${baizhang}
 * @version $Id: DateEnum.java, v 0.1 2018-05-25 下午8:19 Exp $
 */
@SuppressWarnings("unused")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum DateEnum {
    /**
     * 年
     */
    YEAR,

    /**
     * 月
     */
    MONTH,

    /**
     * 日
     */
    DAY,

    /**
     * 时
     */
    HOUR,

    /**
     * 分
     */
    MIN,

    /**
     * 秒
     */
    SEC,
    /**
     * 毫秒
     */
    MILLIS
}