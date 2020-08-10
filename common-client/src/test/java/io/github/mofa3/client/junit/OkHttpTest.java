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
package io.github.mofa3.client.junit;

import io.github.mofa3.client.okhttp3.OkHttpHeader;
import io.github.mofa3.client.okhttp3.OkHttpRequest;
import io.github.mofa3.client.okhttp3.OkHttpResponse;
import io.github.mofa3.lang.common.constant.HttpConstants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * TODO
 *
 * @author lumoere
 * @version $Id:OkHttpTest.java,v 1.0 2020-08-10 21:28 lumoere Exp $
 */
@Slf4j
public class OkHttpTest {
    OkHttpHeader headers = null;
    @Before
    public void init(){
        log.info("junit init");
        headers= OkHttpHeader.newBuilder().contentType(HttpConstants.APP_FORM_URLENCODED)
                .userAgent("UA")
                .accept("*/*")
                .acceptCharset("UTF-8")
                .acceptLanguage("zh_CN")
                .cacheControl("no-cache")
                .connection("keep-alive")
                .other("request-id", "12312abc");
    }

    @Test
    public void get(){
        OkHttpResponse response= OkHttpRequest.get("https://www.baidu.com")
                .log()
                .header(headers)
                .exec();
        Assert.assertEquals(200, response.httpCode());
    }

}