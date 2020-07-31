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

import io.github.mofa3.db.interceptor.SqlExecutorInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;

/**
 * 自动装配
 *
 * @author baizhang
 * @version: v 0.1 MofaDBConfiguration.java, 2019-07-01 22:10 Exp $
 */
@Slf4j
@Configuration
@ConditionalOnBean(SqlSessionFactory.class)
@EnableConfigurationProperties(MofaDbProperties.class)
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class MofaDbConfiguration {
    private final List<SqlSessionFactory> sqlSessionFactoryList;

    public MofaDbConfiguration(ObjectProvider<List<SqlSessionFactory>> sqlSessionFactoryProvider) {
        this.sqlSessionFactoryList = sqlSessionFactoryProvider.getIfUnique();
    }

    @Bean
    @ConfigurationProperties(prefix = MofaDbProperties.MOFA_DB_PREFIX)
    public Properties mofaDBProperties() {
        return new Properties();
    }

    @PostConstruct
    public void addPageInterceptor() {
        log.info("MofaDbConfiguration init....");
        Properties properties = new Properties();
        properties.putAll(mofaDBProperties());
        SqlExecutorInterceptor queryInterceptor = new SqlExecutorInterceptor();
        queryInterceptor.setProperties(properties);
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(queryInterceptor);
        }
    }
}