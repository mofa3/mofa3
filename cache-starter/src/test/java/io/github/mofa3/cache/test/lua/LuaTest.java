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
package io.github.mofa3.cache.test.lua;

import org.redisson.Redisson;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import io.github.mofa3.cache.test.RedissonConfig;
import io.github.mofa3.lang.util.DateBuild;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * TODO
 *
 * @author baizhang
 * @version: LuaTest.java, v 1.0 2020-03-26 3:24 下午 Exp $
 */
public class LuaTest {


    /**
     * 元素起始值
     */
    private static final String START_INDEX = "0";
    final static String luaString= "local biz_prefix = tostring(ARGV[1]); " +
            "local date_time = tostring(ARGV[2]); " +
            "local start_index = tonumber(ARGV[3]); " +
            "local len= tostring(ARGV[4]); " +
            "local date_time_key = redis.call('GET', KEYS[1] ); " +
            "if date_time_key ~= date_time then " +
            "    if date_time_key and (date_time - date_time_key) ~= 0 then " +
            "        redis.call('SET', KEYS[1] , date_time); " +
            "        redis.call('SET', KEYS[2] , start_index); " +
            "    elseif not date_time_key then " +
            "        redis.call('SET', KEYS[1] , date_time); " +
            "        redis.call('SET', KEYS[2] , start_index); " +
            "    end " +
            "end " +
            "local sequence; " +
            "if redis.call('GET', KEYS[2] ) == nil then " +
            "    sequence = start_index; " +
            "    redis.call('SET', KEYS[2] , start_index); " +
            "else " +
            "    sequence = tonumber(redis.call('INCRBY', KEYS[2] , 1)); " +
            "end " +
            "return biz_prefix .. date_time .. string.format(len, sequence);";


    public static void main(String[] args) {
        evalScript();
    }

   

    private static void evalScript(){
        String dateTime = DateBuild.SIMPLE_SHORT_DATE.format(LocalDateTime.now());

        RedissonClient client = Redisson.create(RedissonConfig.config());
        String value = client.getScript(StringCodec.INSTANCE).eval(RScript.Mode.READ_WRITE, luaString,
                RScript.ReturnType.VALUE,
                Arrays.asList("{__CACHE_101_5}_DATETIME", "{__CACHE_101_5}_SEQ"),
                "101", dateTime, START_INDEX, "%05d");
        System.out.println("Val：" + value);
        client.shutdown();
    }

    private static void clearScript(){
        RedissonClient client = Redisson.create(RedissonConfig.config());
        RScript rScript = client.getScript();
        rScript.scriptFlush();
    }
}