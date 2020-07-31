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
package io.github.mofa3.db.interceptor;

import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.TransientDataAccessResourceException;
import io.github.mofa3.db.autoconfigure.MofaDbProperties;
import io.github.mofa3.db.autoconfigure.MybatisInvocationMapping;
import io.github.mofa3.lang.common.constant.MofaConstants;
import io.github.mofa3.lang.util.AlarmLog;
import io.github.mofa3.lang.util.DateBuild;
import io.github.mofa3.lang.util.DateUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Matcher;


/**
 * mybatis sql日志处理
 *
 * @author lumoere
 * @version $Id: MybatisSqlLog.java, v 0.1 2018-09-20 下午5:50 Exp $
 */
@Slf4j
public class MybatisSqlLog {
    private static final Logger SQL_DIGEST_LOGGER = LoggerFactory.getLogger("MOFA_SQL_DIGEST");

    private static final String WHERE = "WHERE";
    /**
     * 是否打印sql
     */
    private static final String SHOW_SQL_KEY = "show-sql";

    /**
     * sql打印执行耗时阈值
     */
    private static final String THRESHOLD_TIMES_KEY = "threshold-times";

    /**
     * 慢sql报警阈值
     */
    private static final String SLOW_SQL_TIMES_KEY = "slow-sql-times";

    public Object getStatement(final Invocation invocation, final Properties properties) {
        long start = DateUtil.timeStampMilli();
        checkParams(invocation);
        MybatisInvocationMapping mapping = getMappedStatement(invocation);
        try {
            return invocation.proceed();
        } catch (InvocationTargetException | IllegalAccessException e) {
            String sql = showSql(mapping.getBoundSql(), mapping.getParameter(), mapping.getConfiguration());
            String msg = Joiner.on("").join("SQL语句执行错误，","SQLID：", mapping.getSqlId(), " SQL：", sql);
            AlarmLog.sqlWarning(msg);
            throw new RuntimeException(e);
        } finally {
            long end = DateUtil.timeStampMilli();
            output(mapping, properties, (end - start));
        }
    }

    private void output(final MybatisInvocationMapping mapping, final Properties properties, final long time) {
        MofaDbProperties mofaDBProperties = getProperties(properties);
        if (mofaDBProperties.getShowSql()) {
            if (time > mofaDBProperties.getThresholdTimes()) {
                writeSql(mapping, time);
            }
            if (time > mofaDBProperties.getSlowSqlTimes()) {
                // 慢sql监控报警
                String sql = showSql(mapping.getBoundSql(), mapping.getParameter(), mapping.getConfiguration());
                String msg = Joiner.on("").join("慢SQL执行警报，耗时：", time,"ms，SQLID：", mapping.getSqlId(), " SQL：",sql);
                AlarmLog.sqlWarning(msg);
            }
        }
    }

    private MybatisInvocationMapping getMappedStatement(final Invocation invocation) {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        String sqlId = mappedStatement.getId();
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Configuration configuration = mappedStatement.getConfiguration();
        MybatisInvocationMapping mybatisInvocationMapping = new MybatisInvocationMapping();
        mybatisInvocationMapping.setParameter(parameter);
        mybatisInvocationMapping.setSqlId(sqlId);
        mybatisInvocationMapping.setBoundSql(boundSql);
        mybatisInvocationMapping.setConfiguration(configuration);
        return mybatisInvocationMapping;
    }

    /**
     * 获取配置信息，如果配置不存在，使用默认值
     *
     * @param properties
     * @return
     */
    private MofaDbProperties getProperties(final Properties properties) {
        MofaDbProperties mofaDbProperties = new MofaDbProperties();

        Boolean showSql = Boolean.valueOf(properties.getProperty(SHOW_SQL_KEY, "true"));
        mofaDbProperties.setShowSql(showSql);
        long thresholdTimes = Long.parseLong(properties.getProperty(THRESHOLD_TIMES_KEY, "1"));
        mofaDbProperties.setThresholdTimes(thresholdTimes);
        long slowSqlTimes = Long.parseLong(properties.getProperty(SLOW_SQL_TIMES_KEY, "2000"));
        mofaDbProperties.setSlowSqlTimes(slowSqlTimes);
        return mofaDbProperties;
    }


    /**
     * 更新、删除参数检查，拒绝无条件更新或者删除
     *
     * @param invocation
     */
    private void checkParams(final Invocation invocation) {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        if (SqlCommandType.DELETE.equals(mappedStatement.getSqlCommandType()) ||
                SqlCommandType.UPDATE.equals(mappedStatement.getSqlCommandType())) {
            // FIXME：emmmmm，如果，我是说如果。如果数据库字段名包含where？？？？
            if (!StringUtils.deleteWhitespace(boundSql.getSql()).toUpperCase().contains(WHERE)) {
                MybatisInvocationMapping mapping = getMappedStatement(invocation);
                String sql = showSql(mapping.getBoundSql(), mapping.getParameter(), mapping.getConfiguration());
                String msg = Joiner.on("").join("SQL语句执行拒绝，拒绝无条件更新，", "SQLID：", mapping.getSqlId(), " SQL：", sql);
                AlarmLog.sqlWarning(msg);
                throw new TransientDataAccessResourceException("拒绝无条件操作数据库");
            }
        }
    }

    /**
     * 输出日志到SQL_DIGEST_LOGGER appender
     *
     * @param time 执行耗时
     */
    private void writeSql(MybatisInvocationMapping mapping, long time) {
        String sql = showSql(mapping.getBoundSql(), mapping.getParameter(), mapping.getConfiguration());
        String str = Joiner.on("").join("Mapper ID【", mapping.getSqlId(), "】耗时：",
                time, "ms，SQL：", sql);
        SQL_DIGEST_LOGGER.info(str);
    }


    /**
     * 组装sql输出
     *
     * @param configuration mapper配置
     * @param boundSql      boundSql
     * @return sql
     */
    private static String showSql(BoundSql boundSql, Object parameterObject,
                                  Configuration configuration) {
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        if (Objects.nonNull(parameterMappings)) {
            for (ParameterMapping parameterMapping : parameterMappings) {
                if (!Objects.equals(parameterMapping.getMode(), ParameterMode.OUT)) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (Objects.isNull(parameterObject)) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else {
                        MetaObject metaObject = configuration.newMetaObject(parameterObject);
                        value = metaObject.getValue(propertyName);
                    }
                    sql = replacePlaceholder(sql, value);
                }
            }
        }
        return sql;
    }

    /**
     * 替换sql占位符参数
     *
     * @param sql           sql
     * @param propertyValue 参数值
     * @return 映射值
     */
    private static String replacePlaceholder(String sql, Object propertyValue) {
        String result;
        if (Objects.nonNull(propertyValue)) {
            if (propertyValue instanceof String) {
                result = MofaConstants.SINGLE_QUOTE + propertyValue + MofaConstants.SINGLE_QUOTE;
            } else if (propertyValue instanceof Date) {
                result = MofaConstants.SINGLE_QUOTE + new DateBuild((Date) propertyValue).formatter() + MofaConstants.SINGLE_QUOTE;
            } else {
                result = propertyValue.toString();
            }
        } else {
            result = "null";
        }
        return sql.replaceFirst("\\?", Matcher.quoteReplacement(result));
    }

}