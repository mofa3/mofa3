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
package io.github.mofa3.cache.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import io.github.mofa3.cache.service.SerialNumService;

/**
 * Lua脚本初始化
 *
 * @author lumoere
 */
@Slf4j
@Component
@Order(99)
@SuppressWarnings("unused")
public class LuaScriptRunner implements CommandLineRunner {
    private final SerialNumService serialNumService;

    public LuaScriptRunner(ObjectProvider<SerialNumService> serialNumServiceProvider) {
        this.serialNumService = serialNumServiceProvider.getIfUnique();
    }

    @Override
    public void run(final String... args) throws Exception {
        serialNumService.loadLuaScript();
    }
}