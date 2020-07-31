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
package io.github.mofa3.cache.service;

import java.util.List;

/**
 * 序号生成，Lua+redis实现，每日重置从1开始
 * 默认流水号：
 * 业务前缀（3位）日期（6位）自增（7位） 共16位
 * 303 180725 0000023
 * <p>
 * 短位流水号：
 * 业务前缀（3位）日期（6位）自增（5位） 共14位
 * 303 180725 00012
 * <p>
 * 默认提供两种长度，如果位数不够，避免数据爆发增长导致自增位不够，自增位会自动升位，根据需要判断是否支持升位，数据库字段长度预留升位空间
 *
 * @author lumoere
 * @version $Id: SerialNumService.java, v 0.1 2018-05-31 下午3:38 Exp $
 */
@SuppressWarnings("unused")
public interface SerialNumService {
    /**
     * 获取流水号
     *
     * @param bizPrefix 业务前缀
     * @return 流水号
     */
    String getSerialNum(String bizPrefix);

    /**
     * 批量获取流水号，限制在2-1000之间
     *
     * @param bizPrefix 业务前缀
     * @param quantity  获取数量
     * @return 流水号集合
     */
    List<String> listSerialNum(String bizPrefix, int quantity);

    /**
     * 获取短位流水号，自增部分最大5位，确认是否足够
     *
     * @param bizPrefix 业务前缀
     * @return 流水号
     */
    String getShortSerialNum(String bizPrefix);

    /**
     * 批量获取短位流水号，限制在2-1000之间，自增部分最大5位，确认是否足够
     *
     * @param bizPrefix 业务前缀
     * @param quantity  获取数量
     * @return 流水号集合
     */
    List<String> listShortSerialNum(String bizPrefix, int quantity);

    /**
     * 初始化Lua脚本
     */
    void loadLuaScript();
}