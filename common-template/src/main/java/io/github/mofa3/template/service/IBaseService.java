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

import java.util.List;

import io.github.mofa3.template.model.BaseModel;

/**
 * baseService 接口，提供基础的列表查询、单条查询、保存、更新、删除（逻辑删除）方法
 *
 * @author lumoere
 * @version $Id: IBaseService.java, v 0.1 2018-06-05 下午9:45 Exp $
 */
@SuppressWarnings("unused")
public interface IBaseService<B extends BaseModel<B>> {
    /**
     * 查询数据返回列表
     *
     * @param model 对象model
     * @return 对象列表
     */
    List<B> listRecord(B model);

    /**
     * 查询
     * 返回结果只有一条，否则抛出异常
     *
     * @param model 对象model
     * @return 查询结果
     */
    B getRecord(B model);

    /**
     * 对象保存
     *
     * @param model 对象model
     * @return 保存结果（受影响行数）
     */
    int saveRecord(B model);

    /**
     * 更新对象
     *
     * @param model 对象model
     * @return 更新结果（受影响行数）
     */
    int updateRecord(B model);

    /**
     * 删除记录，逻辑删除 deleted= 1
     *
     * @param model 对象model
     * @return 删除结果
     */
    int removeRecord(B model);
}