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
package io.github.mofa3.cache.annotation;

import org.springframework.context.annotation.Conditional;
import io.github.mofa3.cache.condition.RedisSupportModelConditional;
import io.github.mofa3.cache.enums.RedisModelEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * redis 支持模式检查
 *
 * @author baizhang
 * @version: RedisSupportModelConditional.java, v 1.0 2020-03-27 9:37 下午 Exp $
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Conditional(RedisSupportModelConditional.class)
public @interface RedisSupportModelConditionalOnProperty {
    /**
     * 配置文件key
     *
     * @return
     */
    String key() default "";

    /**
     * 支持的模式，不符合限定值，抛出异常
     *
     * @return
     */
    RedisModelEnum[] supportModel() default {};
}