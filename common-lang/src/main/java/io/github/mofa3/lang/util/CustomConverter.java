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

import java.util.Map;

/**
 * 自定义转换器，针对Cglib不能转换的类型手动处理
 *
 * @author ${guanzhong}
 * @version $Id: CustomConverter.java, v 0.1 2017年06月10日 下午2:09 Exp $
 */
public class CustomConverter implements net.sf.cglib.core.Converter {

    /**
     * the instance
     */
    public static final CustomConverter INSTANCE = new CustomConverter();

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Object convert(Object value, Class target, Object context) {
        if (value == null) {
            return null;
        }

        // String --> MAP
        if (String.class.isInstance(value) && Map.class.isAssignableFrom(target)) {
            return ExtendPropUtil.convert2MapFromString((String) value);
        }

        // MAP --> String
        if (Map.class.isInstance(value) && String.class.isAssignableFrom(target)) {
            return ExtendPropUtil.convert2StringFromMap((Map) value);

        }

        // String --> Enum
        if (String.class.isInstance(value) && Enum.class.isAssignableFrom(target)) {
            return Enum.valueOf(target, (String) value);
        }

        //Enum --> String
        if (Enum.class.isInstance(value) && target.isAssignableFrom(String.class)) {
            return ((Enum) value).name();
        }

        // Integer --> Boolean
        boolean isInt = (Integer.class.isInstance(value) || int.class.isInstance(value))
                && (target.isAssignableFrom(Boolean.class) || target.isAssignableFrom(boolean.class));
        if (isInt) {
            return (Integer) value > 0;
        }

        // Boolean --> Integer
        boolean isBool = (Boolean.class.isInstance(value) || boolean.class.isInstance(value))
                && (target.isAssignableFrom(Integer.class) || target.isAssignableFrom(int.class));
        if (isBool) {
            return (Boolean) value ? 1 : 0;
        }

        return value;
    }
}