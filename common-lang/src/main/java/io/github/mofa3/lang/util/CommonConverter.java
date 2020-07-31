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

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * cglib 对象转换器
 *
 * @author ${guanzhong}
 * @version $Id: CommonConverter.java, v 0.1 2017年06月10日 下午1:00 Exp $
 */
@Slf4j
@SuppressWarnings("unused")
public class CommonConverter {
    /**
     * 转换器缓存
     */
    private static Map<String, BeanCopier> cache = new ConcurrentHashMap<>(32);

    /**
     * 转换对象
     *
     * @param source    源对象
     * @param target    目标对象
     * @param converter 转换器，默认使用cglib转换器
     */
    private static <T, E> E convert(T source, E target, Converter converter) {
        //参数校验
        if (source == null || target == null) {
            return null;
        }

        BeanCopier bc = getBeanCopier(source.getClass(), target.getClass());
        bc.copy(source, target, converter);

        return target;
    }

    /**
     * 使用DefaultConverter转换对象
     *
     * @param source 源对象
     * @param target 目标对象
     */
    public static <T, E> E convert(T source, E target) {
        return convert(source, target, CustomConverter.INSTANCE);
    }

    /**
     * 批量转换对象
     *
     * @param targetClass 源对象
     * @param sources     源转换类集合对象
     * @param targets     目标转换集合对象
     * @param converter   转换器，默认使用cglib转换器
     */
    private static <T, E> List<E> convertList(Class<E> targetClass, Collection<T> sources,
                                              Collection<E> targets,
                                              net.sf.cglib.core.Converter converter) {
        if (targetClass == null || org.springframework.util.CollectionUtils.isEmpty(sources)
                || targets == null) {
            if (targets instanceof List) {
                return (List<E>) targets;
            }
            return new ArrayList<>();
        }

        try {
            for (T source : sources) {
                E target = targetClass.newInstance();
                convert(source, target, converter);
                targets.add(target);
            }
        } catch (Exception e) {
            log.error("Objects cast exception", e);
            throw new RuntimeException(e);
        }

        if (targets instanceof List) {
            return (List<E>) targets;
        }

        return new ArrayList<>(targets);
    }

    /**
     * 使用DefaultConverter批量转换对象
     *
     * @param targetClass 源对象
     * @param sources     源转换类集合对象
     */
    public static <T, E> List<E> convertList(Class<E> targetClass, Collection<T> sources) {
        return convertList(targetClass, sources, new ArrayList<>(), CustomConverter.INSTANCE);
    }

    /**
     * 从cache中获取或创建一个BeanCopier
     *
     * @param source 源对象
     * @param target 目标对象
     * @return AbstractBeanCopier
     */
    private static BeanCopier getBeanCopier(Class<?> source, Class<?> target) {
        String key = source + "-" + target;

        BeanCopier bc = cache.get(key);
        if (bc == null) {
            bc = BeanCopier.create(source, target, true);
            cache.put(key, bc);
        }

        return bc;
    }

    /**
     * 通过反射实现属性转移，只有相同的属性才会复制，source的属性值为空的不执行复制
     * tips：需要搞清楚两个对象的差异之处，并决定是否需要使用该方法，否则会增大问题排查的难度
     *
     * @param target 目标对象
     * @param source 源对象
     */
    public static void copyProperties(Object source, Object target) {
        Field[] fields = target.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            String fieldName = field.getName();
            String firstLetter = fieldName.substring(0, 1).toUpperCase();

            String getMethodName = "get" + firstLetter + fieldName.substring(1);
            String setMethodName = "set" + firstLetter + fieldName.substring(1);

            try {

                Method getMethod = source.getClass().getMethod(getMethodName);
                Method setMethod = target.getClass().getMethod(setMethodName, field.getType());
                Object value = getMethod.invoke(source);
                if (null != value) {
                    setMethod.invoke(target, value);
                }

            } catch (Exception e) {
                log.error("属性复制异常：source:{" + source.toString()
                        + "}, target:{" + target.toString()
                        + "} error:" + e.getMessage());
            }
        }

    }
}