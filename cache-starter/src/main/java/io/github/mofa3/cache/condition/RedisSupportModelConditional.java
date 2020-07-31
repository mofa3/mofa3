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
package io.github.mofa3.cache.condition;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import io.github.mofa3.cache.annotation.RedisSupportModelConditionalOnProperty;
import io.github.mofa3.cache.enums.RedisModelEnum;

import java.util.Map;
import java.util.Objects;

/**
 * redis服务模式支持检查注解
 *
 * 一种取巧的使用方式，主要目的是明确抛出异常提示，通常情况下建议符合装配规则就初始化bean
 *
 * @author baizhang
 * @version: UnSupportConditional.java, v 1.0 2020-03-27 9:49 下午 Exp $
 */
@Slf4j
public class RedisSupportModelConditional extends SpringBootCondition {
    @Override
    public ConditionOutcome getMatchOutcome(final ConditionContext context, final AnnotatedTypeMetadata metadata) {
        Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(RedisSupportModelConditionalOnProperty.class.getName());
        if(Objects.isNull(annotationAttributes)){
            return new ConditionOutcome(false, "pass");
        }
        String key = (String) annotationAttributes.get("key");
        RedisModelEnum[] notInclude = (RedisModelEnum[]) annotationAttributes.get("supportModel");
        String value = context.getEnvironment().getProperty(key);
        for (RedisModelEnum enumValue: notInclude) {
            if (RedisModelEnum.getByCode(value)== enumValue) {
                return new ConditionOutcome(false, "pass");
            }
        }
        return new ConditionOutcome(true, "不支持该模式配置,【cache.redis.model】值为：SINGLE和CLUSTER");
    }

}