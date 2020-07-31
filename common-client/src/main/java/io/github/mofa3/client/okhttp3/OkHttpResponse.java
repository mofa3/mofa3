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
package io.github.mofa3.client.okhttp3;

import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.Cookie;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import io.github.mofa3.lang.exception.ThirdPartyException;
import io.github.mofa3.lang.util.JsonUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

/**
 * OkHttp 请求结果响应对象
 *
 * @author baizhang
 * @version: v 0.1 OkHttpResponse.java, 2019-07-31 10:19 Exp $
 */
public class OkHttpResponse implements IOkHttpResponse {
    private final Request request;
    private final Response response;
    private final ResponseBody responseBody;

    OkHttpResponse(final Response response) {
        this.request = response.request();
        this.response = response;
        this.responseBody = response.body();
    }

    @Override
    public int httpCode() {
        return response.code();
    }

    @Override
    public boolean success() {
        return null != response && response.isSuccessful();
    }

    @Override
    public String message() {
        return response.message();
    }

    @Override
    public boolean redirect() {
        return response.isRedirect();
    }

    @Override
    public Headers headers() {
        return response.headers();
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public List<Cookie> cookies() {
        HttpUrl httpUrl = response.request().url();
        return Cookie.parseAll(httpUrl, this.headers());
    }

    @Override
    public String toStr() {
        try {
            return responseBody.string();
        } catch (IOException e) {
            throw new ThirdPartyException("以String返回异常", e);
        }
    }

    @Override
    public byte[] toByte() {
        try {
            return responseBody.bytes();
        } catch (IOException e) {
            throw new ThirdPartyException("以byte[]返回异常", e);
        }
    }

    @Override
    public InputStream toStream() {
        return responseBody.byteStream();
    }

    @Override
    public <T> T toBean(final Class<T> claxx) {
        return JsonUtil.streamToBean(this.toStream(), claxx);
    }

    @Override
    public <T> T toJsonType(final TypeReference<?> typeReference) {
        return JsonUtil.streamToBean(this.toStream(), typeReference);
    }

    @Override
    public <T> List<T> toList(final Class<T> claxx) {
        return JsonUtil.streamToList(this.toStream(), claxx);
    }

    @Override
    public void toFile(final File file) {
        try {
            Files.copy(this.toStream(), file.toPath());
        } catch (IOException e) {
            throw new ThirdPartyException("写入文件异常", e);
        }
    }

    @Override
    public Response rawResponse() {
        return response;
    }

    @Override
    public ResponseBody rawBody() {
        return responseBody;
    }
}