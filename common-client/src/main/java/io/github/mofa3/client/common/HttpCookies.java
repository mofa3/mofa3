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

import lombok.Data;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;

/**
 * HttpCookies
 *
 * @author ${baizhang}
 * @version $Id: HttpBuilder.java, v 0.1 2018-04-19 下午5:44 Exp $
 */
@Data
@SuppressWarnings("unused")
public class HttpCookies {

    /**
     * 使用httpContext，用于设置和携带Cookie
     */
    private HttpClientContext context;

    /**
     * 储存Cookie
     */
    private CookieStore cookieStore;

    public static HttpCookies custom() {
        return new HttpCookies();
    }

    private HttpCookies() {
        this.context = new HttpClientContext();
        this.cookieStore = new BasicCookieStore();
        this.context.setCookieStore(cookieStore);
    }

}
