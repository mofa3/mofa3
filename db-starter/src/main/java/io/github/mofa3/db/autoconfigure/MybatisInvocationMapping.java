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
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.Configuration;

/**
 * mybatis Invocation 拆分管理对象，方便获取sql、配置文件等信息
 * 目的主要为了方便组装获取sql对象信息，不考虑对象定义的合理性
 *
 * @author baizhang
 * @version: v 0.1 MybatisInvocationMapping.java, 2019-10-16 3:15 下午 Exp $
 */
@Data
public class MybatisInvocationMapping {
    /**
     * sql参数
     */
    private Object parameter;

    /**
     * mapper id
     */
    private String sqlId;

    /**
     * boundSql
     */
    private BoundSql boundSql;

    /**
     * configuration
     */
    private Configuration configuration;
}