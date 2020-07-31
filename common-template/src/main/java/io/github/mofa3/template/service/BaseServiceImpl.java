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
package io.github.mofa3.template.service;

import org.springframework.beans.factory.annotation.Autowired;
import io.github.mofa3.lang.enums.LogicDeleteEnum;
import io.github.mofa3.lang.util.DateBuild;
import io.github.mofa3.lang.util.DateUtil;
import io.github.mofa3.template.dao.BaseDAO;
import io.github.mofa3.template.model.BaseModel;

import java.util.Date;
import java.util.List;

/**
 * baseServiceImpl 实现
 * 避免 No qualifying bean 问题，注入由子类完成
 *
 * @author ${baizhang}
 * @version $Id: BaseServiceImpl.java, v 0.1 2018-06-05 下午9:56 Exp $
 */
@SuppressWarnings("unused")
public class BaseServiceImpl<B extends BaseModel<B>> implements IBaseService<B> {
    @Autowired
    private BaseDAO<B> dao;

    @Override
    public List<B> listRecord(B model) {
        model.setDeleted(LogicDeleteEnum.FALSE.getDelete());
        return dao.listRecord(model);
    }

    @Override
    public B getRecord(B model) {
        model.setDeleted(LogicDeleteEnum.FALSE.getDelete());
        return dao.getRecord(model);
    }

    @Override
    public int saveRecord(B model) {
        Date date = new DateBuild().toDate();
        long timeStampMilli = DateUtil.timeStampMilli();
        model.setCreateTime(date);
        model.setUpdateTime(date);
        model.setGmtCreate(timeStampMilli);
        model.setGmtUpdate(timeStampMilli);
        model.setDeleted(LogicDeleteEnum.FALSE.getDelete());
        return dao.saveRecord(model);

    }

    @Override
    public int updateRecord(B model) {
        model.setUpdateTime(new DateBuild().toDate());
        model.setGmtUpdate(DateUtil.timeStampMilli());
        return dao.updateRecord(model);
    }

    @Override
    public int removeRecord(final B model) {
        model.setDeleted(LogicDeleteEnum.TRUE.getDelete());
        return dao.removeRecord(model);
    }
}