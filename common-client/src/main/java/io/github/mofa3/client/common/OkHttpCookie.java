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
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

import java.net.CookieStore;
import java.util.List;

/**
 * TODO
 *
 * @author baizhang
 * @version: OkHttpCookie.java, v 0.1 2019-04-15 09:47 Exp $
 */
public class OkHttpCookie implements CookieJar {

    private CookieStore cookieStore;

    @Override
    public void saveFromResponse(final HttpUrl url, final List<Cookie> cookies) {

    }

    @Override
    public List<Cookie> loadForRequest(final HttpUrl url) {
        return null;
    }
}