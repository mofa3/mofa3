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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO
 *
 * @author baizhang
 * @version: OkHttpCookieStoreImpl.java, v 0.1 2019-04-15 09:58 Exp $
 */
public class OkHttpCookieStoreImpl implements OkHttpCookieStore {
    private final Map<String, List<Cookie>> cookies = new ConcurrentHashMap<>();

    @Override
    public void add(final HttpUrl url, final List<Cookie> cookieList) {
        if (null == url) {
            throw new NullPointerException("HttpUrl不能为空");
        }
        if (cookieList.isEmpty()) {
            throw new NullPointerException("cookie不能为空");
        }
        List<Cookie> oldCookies = cookies.get(url.host());
        List<Cookie> deleteCookies = new ArrayList<>(oldCookies.size());
        for (Cookie cookie : cookieList) {
            for (Cookie oldCookie : oldCookies) {
                if (oldCookie.name().equals(cookie.name())) {
                    deleteCookies.add(oldCookie);
                }
            }
        }
        oldCookies.removeAll(deleteCookies);
        oldCookies.addAll(cookieList);
    }

    @Override
    public List<Cookie> get(final HttpUrl url) {
        if (null == url) {
            throw new NullPointerException("HttpUrl不能为空");
        }
        return cookies.computeIfAbsent(url.host(), k -> new ArrayList<>());
    }

    @Override
    public List<Cookie> getAll() {
        List<Cookie> cookieList = new ArrayList<>(16);
        for (String host : cookies.keySet()) {
            cookieList.addAll(cookies.get(host));
        }
        return cookieList;
    }

    @Override
    public boolean remove(final HttpUrl url, final Cookie cookie) {
        if (null == url) {
            throw new NullPointerException("HttpUrl不能为空");
        }
        if (null == cookie) {
            throw new NullPointerException("cookie不能为空");
        }
        return null != cookies.remove(url.host());
    }

    @Override
    public boolean removeAll() {
        cookies.clear();
        return true;
    }
}