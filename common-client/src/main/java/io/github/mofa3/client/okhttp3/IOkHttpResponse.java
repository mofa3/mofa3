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
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * TODO
 *
 * @author baizhang
 * @version: v 0.1 IOkHttpResponse.java, 2019-07-30 22:46 Exp $
 */
public interface IOkHttpResponse {
    /**
     * http状态码 200 404 500...
     *
     * @return http code
     */
    int httpCode();

    /**
     * 请求是否成功
     *
     * @return request isSuccessful
     */
    boolean success();

    /**
     * http 状态码msg
     *
     * @return status msg
     */
    String message();

    /**
     * 是否重定向
     *
     * @return boolean value
     */
    boolean redirect();

    /**
     * 响应header
     *
     * @return Headers
     */
    Headers headers();

    /**
     * contentType
     *
     * @return MediaType
     */
    MediaType contentType();

    /**
     * 返回内容长度
     *
     * @return length
     */
    long contentLength();

    /**
     * cookie
     *
     * @return list cookie
     */
    List<Cookie> cookies();

    /**
     * 结果以string返回
     *
     * @return String
     */
    String toStr();

    /**
     * 结果以byte返回
     *
     * @return byte[]
     */
    byte[] toByte();

    /**
     * 结果以stream返回
     *
     * @return stream
     */
    InputStream toStream();

    /**
     * 结果以指定Bean对象返回
     *
     * @param claxx 转换类型
     * @param <T>   泛型
     * @return BeanType
     */
    <T> T toBean(Class<T> claxx);

    /**
     * 结果以jackson Type返回
     *
     * @param typeReference jackson Type
     * @param <T>           泛型
     * @return BeanType
     */
    <T> T toJsonType(TypeReference<?> typeReference);

    /**
     * 结果以List<Bean>返回
     *
     * @param claxx 转换类型
     * @param <T>   泛型
     * @return list
     */
    <T> List<T> toList(Class<T> claxx);

    /**
     * 返回结果写入文件
     * 一般用于文件下载，文件接收
     *
     * @param file file
     */
    void toFile(File file);

    /**
     * rawResponse
     *
     * @return Response
     */
    Response rawResponse();

    /**
     * rawBody
     *
     * @return ResponseBody
     */
    ResponseBody rawBody();
}