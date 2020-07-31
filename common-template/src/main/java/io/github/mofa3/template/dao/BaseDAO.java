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
package io.github.mofa3.template.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;
import io.github.mofa3.template.model.BaseModel;

/**
 * 基础增删改查
 *
 * @author ${baizhang}
 * @version $Id: BaseDAO.java, v 0.1 2018-08-29 下午8:41 Exp $
 */
@SuppressWarnings("unused")
public interface BaseDAO<B extends BaseModel<B>> {
    /**
     * 查询数据返回列表
     *
     * @param model 对象model
     * @return 对象列表
     * @throws DataAccessException exception
     */
    List<B> listRecord(B model) throws DataAccessException;

    /**
     * 查询
     * 返回结果只有一条，否则抛出异常
     *
     * @param model 对象model
     * @return 查询结果
     * @throws DataAccessException exception
     */
    B getRecord(B model) throws DataAccessException;

    /**
     * 对象保存
     *
     * @param model 对象model
     * @return 保存结果（受影响行数）
     * @throws DataAccessException exception
     */
    int saveRecord(B model) throws DataAccessException;

    /**
     * 更新对象
     *
     * @param model 对象model
     * @return 更新结果（受影响行数）
     * @throws DataAccessException exception
     */
    int updateRecord(B model) throws DataAccessException;

    /**
     * 删除记录
     *
     * @param model 对象model
     * @return 删除结果
     * @throws DataAccessException exception
     */
    int removeRecord(B model) throws DataAccessException;
}