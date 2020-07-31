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
package io.github.mofa3.template.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * baseModel
 * 常用字段放入base
 * <p>
 * 使用offset pageSize参数作为分页参数，分页优先使用优先使用pageNo，在pageNo和offset同时传入的情况下
 * 查询分页以pageNo为准
 *
 * @author ${baizhang}
 * @version $Id: BaseModel.java, v 0.1 2018-06-05 下午9:41 Exp $
 */
@Data
@SuppressWarnings("unused")
public class BaseModel<B extends BaseModel<B>> implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1711899675342850998L;

    /**
     * 逻辑删除（1.true 0.false default 0）
     */
    private Integer deleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间戳（毫秒）
     */
    private Long gmtCreate;

    /**
     * 更新时间戳（毫秒）
     */
    private Long gmtUpdate;

    /**
     * 分页pageNo
     */
    @JsonIgnore
    private Integer pageNo;

    /**
     * 分页offset
     */
    @JsonIgnore
    private Integer offset;

    /**
     * 每页显示条数
     */
    @JsonIgnore
    private Integer pageSize = 20;


}