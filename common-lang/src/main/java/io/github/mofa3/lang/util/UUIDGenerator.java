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

import java.util.UUID;

/**
 * UUID 工具类
 *
 * @author lumoere
 * @version $Id: UUIDGenerator.java, v 0.1 2018-07-06 上午10:42 Exp $
 */
@SuppressWarnings("unused")
public class UUIDGenerator {
    /**
     * 获取UUID，剔除 "-"
     *
     * @return uuid
     */
    public static String get() {
        return generatorToken().replace("-", "");
    }

    /**
     * 获取UUID
     *
     * @return uuid
     */
    public static String generatorToken() {
        return UUID.randomUUID().toString();
    }

}