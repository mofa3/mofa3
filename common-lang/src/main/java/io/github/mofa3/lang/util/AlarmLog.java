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

import com.google.common.base.Joiner;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import io.github.mofa3.lang.common.constant.MofaConstants;

/**
 * 监控报警工具类，配合运维监控报警策略使用
 *
 * @author baizhang
 * @version: v 0.1 AlarmLog.java, 2019-10-16 3:47 下午 Exp $
 */
@Slf4j(topic = "ALARM_WARNING")
@UtilityClass
public class AlarmLog {

    /**
     * 分隔符
     */
    private static final String SPLIT_SYMBOL = "``";

    /**
     * sql执行异常报警
     */
    private static final String ALARM_EXEC_SQL_WARNING = "AlarmExecSqlWarning";

    /**
     * 普通自定义提示报警
     */
    private static final String ALARM_GENERAL_WARNING = "AlarmGeneralWarning";


    /**
     * sql执行异常
     *
     * @param msg
     */
    public static void sqlWarning(final String msg) {
        String output = Joiner.on("").join(ALARM_EXEC_SQL_WARNING, MofaConstants.BLANK, SPLIT_SYMBOL, msg, SPLIT_SYMBOL);
        log.warn(output);
    }

    public static void generalWarning(final String msg) {
        String output = Joiner.on("").join(ALARM_GENERAL_WARNING, MofaConstants.BLANK, SPLIT_SYMBOL, msg, SPLIT_SYMBOL);
        log.warn(output);
    }
}