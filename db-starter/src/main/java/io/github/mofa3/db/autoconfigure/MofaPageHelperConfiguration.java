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

import com.github.pagehelper.PageInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * pagehelper装载类
 * 如果不使用pagehelper starter，则初始化pagehelper
 *
 * @author baizhang
 * @version: v 0.1 MofaPageHelperConfiguration.java, 2019-10-21 8:31 下午 Exp $
 */
@Slf4j
@Configuration
@ConditionalOnBean(SqlSessionFactory.class)
@ConditionalOnMissingClass("com.github.pagehelper.autoconfigure.PageHelperProperties")
@AutoConfigureBefore(value = {MofaDbConfiguration.class})
public class MofaPageHelperConfiguration {
    private final List<SqlSessionFactory> sqlSessionFactoryList;
    public MofaPageHelperConfiguration(ObjectProvider<List<SqlSessionFactory>> sqlSessionFactoryProvider) {
        this.sqlSessionFactoryList = sqlSessionFactoryProvider.getIfUnique();
    }

    @PostConstruct
    public void addPageInterceptor() {
        log.info("miss pagehelper-boot-starter auto config,init....");
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            sqlSessionFactory.getConfiguration().addInterceptor(new PageInterceptor());
        }
    }
}