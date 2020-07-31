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
package io.github.mofa3.db.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * TODO
 *
 * @author baizhang
 * @version: v 0.1 MofaDbProperties.java, 2019-07-01 22:16 Exp $
 */
@Data
@ConfigurationProperties(prefix = MofaDbProperties.MOFA_DB_PREFIX)
public class MofaDbProperties {
    public static final String MOFA_DB_PREFIX = "mofa.db";
    /**
     * 是否输入sql到日志文件，默认true
     */
    private Boolean showSql = true;

    /**
     * sql执行耗时，执行耗时大于该值，输出到文件
     * 耗时单位：毫秒（ms）
     */
    private Long thresholdTimes = 1L;

    /**
     * 慢sql执行监控报警阈值
     * 耗时单位：毫秒（ms）
     */
    private Long slowSqlTimes = 2000L;
}