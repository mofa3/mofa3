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
import lombok.Getter;

/**
 * 逻辑删除枚举
 *
 * @author lumoere
 * @version $Id: LogicDeleteEnum.java, v 0.1 2018-09-03 下午8:37 Exp $
 */
@SuppressWarnings("unused")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum LogicDeleteEnum {

    /**
     * 已删除
     */
    TRUE(1),
    /**
     * 未删除
     */
    FALSE(0);

    /**
     * 逻辑删除
     */
    @Getter
    private Integer delete;
}