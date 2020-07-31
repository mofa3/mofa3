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

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import io.github.mofa3.lang.common.constant.MofaConstants;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 扩展属性转换工具类
 *
 * @author lumoere
 * @version $Id: ExtendPropUtil.java, v 0.1 2017年06月10日 下午2:10 Exp $
 */
public class ExtendPropUtil {
    /**
     * 字段之间的分隔符
     */
    private static final String SPILT = "$";

    /**
     * 字段之间的分隔符
     */
    private static final String SPILT_REPLACE = "//.";

    /**
     * 字段之间的分隔符
     */
    private static final String EQUAL_REPLACE = "//&";

    /**
     * 将map转换为字符串
     */
    public static String convert2StringFromMap(Map<String, String> extendPropMap) {

        if (CollectionUtils.isEmpty(extendPropMap)) {
            return null;
        }
        StringBuilder result = new StringBuilder();

        // 解析后的格式应该是 KEY1=VALUE1,KEY2=VALUE2,KEY3=VALUE3
        Iterator<String> ite = extendPropMap.keySet().iterator();
        while (ite.hasNext()) {
            String key = ite.next();
            String value = extendPropMap.get(key);
            if (StringUtils.indexOf(value, SPILT) != -1) {
                value = StringUtils.replace(value, SPILT, SPILT_REPLACE);
            }
            //对=进行转义
            if (StringUtils.indexOf(value, MofaConstants.EQUAL) != -1) {
                value = StringUtils.replace(value, MofaConstants.EQUAL, EQUAL_REPLACE);
            }
            value = value == null ? "" : value;
            result.append(key).append(MofaConstants.EQUAL).append(value);
            if (ite.hasNext()) {
                result.append(SPILT);
            }
        }
        return result.toString();

    }

    /**
     * 将扩展属性字符串转为map
     */
    public static Map<String, String> convert2MapFromString(String extendPropString) {

        HashMap<String, String> map = new HashMap<>(16);

        if (StringUtils.isBlank(extendPropString)) {
            return map;
        }

        String[] prop = StringUtils.split(extendPropString, SPILT);

        for (final String aProp : prop) {
            int eq = aProp.indexOf(MofaConstants.EQUAL);
            //这里原来有一个&& eq < prop[i].length() - 1,为了过滤掉"aaa="的情况
            //后来为了保留value为空的情况，不再过滤这种情况
            if (eq > 0) {
                String key = aProp.substring(0, eq);
                String value = aProp.substring(eq + 1);

                //把转义替换掉
                value = StringUtils.replace(value, SPILT_REPLACE, SPILT);
                value = StringUtils.replace(value, EQUAL_REPLACE, MofaConstants.EQUAL);

                map.put(key, value);
            }

        }
        return map;

    }

}