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
package io.github.mofa3.client.common;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

import java.util.List;

/**
 * TODO
 *
 * @author baizhang
 * @version: OkHttpCookieStore.java, v 0.1 2019-04-15 09:49 Exp $
 */
public interface OkHttpCookieStore {
    /**
     * 为请求的url添加cookie
     *
     * @param url
     * @param cookieList
     */
    void add(HttpUrl url, List<Cookie> cookieList);

    /**
     * 获取指定请求url的cookie列表
     *
     * @param url
     * @return
     */
    List<Cookie> get(HttpUrl url);

    /**
     * 获取所有cookie列表
     *
     * @return
     */
    List<Cookie> getAll();

    /**
     * 删除指定请求url的cookie
     *
     * @param url
     * @param cookie
     * @return
     */
    boolean remove(HttpUrl url, Cookie cookie);

    /**
     * 删除所有cookie
     *
     * @return
     */
    boolean removeAll();
}