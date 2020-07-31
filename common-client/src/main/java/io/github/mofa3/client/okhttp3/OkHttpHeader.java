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

import okhttp3.Headers;
import io.github.mofa3.lang.common.constant.MofaConstants;
import io.github.mofa3.lang.common.constant.HttpConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * http header 配置
 *
 * @author baizhang
 * @version: OkHttpHeader.java, v 0.1 2019-04-10 19:46 Exp $
 */
@SuppressWarnings("unused")
public class OkHttpHeader {
    private OkHttpHeader() {
    }

    public static OkHttpHeader newBuilder() {
        return new OkHttpHeader();
    }

    /**
     * 记录head头信息
     */
    private Map<String, String> headerMaps = new HashMap<>(24);

    /**
     * 指定客户端能够接收的内容类型
     * 例如：Accept: text/plain, text/html
     * 常量类：{@link HttpConstants}
     *
     * @param key   key
     * @param value value
     * @return OkHttpHeader
     */
    public OkHttpHeader other(String key, String value) {
        headerMaps.put(key, value);
        return this;
    }

    /**
     * 指定客户端能够接收的内容类型
     * 例如：Accept: text/plain, text/html
     * 常量类：{@link HttpConstants}
     *
     * @param accept accept
     * @return OkHttpHeader
     */
    public OkHttpHeader accept(String accept) {
        headerMaps.put(OkHttpHeader.HttpReqHead.ACCEPT, accept);
        return this;
    }

    /**
     * 浏览器可以接受的字符编码集
     * 例如：Accept-Charset: UTF-8
     * {@link MofaConstants}
     *
     * @param acceptCharset 字符集
     * @return OkHttpHeader
     */
    public OkHttpHeader acceptCharset(String acceptCharset) {
        headerMaps.put(OkHttpHeader.HttpReqHead.ACCEPT_CHARSET, acceptCharset);
        return this;
    }

    /**
     * 指定浏览器可以支持的web服务器返回内容压缩编码类型
     * 例如：Accept-Encoding: compress, gzip
     *
     * @param acceptEncoding gzip
     * @return OkHttpHeader
     */
    public OkHttpHeader acceptEncoding(String acceptEncoding) {
        headerMaps.put(OkHttpHeader.HttpReqHead.ACCEPT_ENCODING, acceptEncoding);
        return this;
    }

    /**
     * 浏览器可接受的语言
     * 例如：Accept-Language: en,zh
     *
     * @param acceptLanguage zh
     * @return OkHttpHeader
     */
    public OkHttpHeader acceptLanguage(String acceptLanguage) {
        headerMaps.put(OkHttpHeader.HttpReqHead.ACCEPT_LANGUAGE, acceptLanguage);
        return this;
    }

    /**
     * 可以请求网页实体的一个或者多个子范围字段
     * 例如：Accept-Ranges: bytes
     *
     * @param acceptRanges bytes
     * @return OkHttpHeader
     */
    public OkHttpHeader acceptRanges(String acceptRanges) {
        headerMaps.put(OkHttpHeader.HttpReqHead.ACCEPT_RANGES, acceptRanges);
        return this;
    }

    /**
     * HTTP授权的授权证书
     * 例如：Authorization: Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==
     *
     * @param authorization author
     * @return OkHttpHeader
     */
    public OkHttpHeader authorization(String authorization) {
        headerMaps.put(OkHttpHeader.HttpReqHead.AUTHORIZATION, authorization);
        return this;
    }

    /**
     * 指定请求和响应遵循的缓存机制
     * 例如：Cache-Control: no-cache
     *
     * @param cacheControl cache
     * @return OkHttpHeader
     */
    public OkHttpHeader cacheControl(String cacheControl) {
        headerMaps.put(OkHttpHeader.HttpReqHead.CACHE_CONTROL, cacheControl);
        return this;
    }

    /**
     * 表示是否需要持久连接（HTTP 1.1默认进行持久连接）
     * 例如：Connection: close 短链接； Connection: keep-alive 长连接
     * 常量类：{@link HttpConstants}
     *
     * @param connection connection type
     * @return OkHttpHeader
     */
    public OkHttpHeader connection(String connection) {
        headerMaps.put(OkHttpHeader.HttpReqHead.CONNECTION, connection);
        return this;
    }

    /**
     * HTTP请求发送时，会把保存在该请求域名下的所有cookie值一起发送给web服务器
     * 例如：Cookie: $Version=1; Skin=new;
     *
     * @param cookie cookie
     * @return OkHttpHeader
     */
    public OkHttpHeader cookie(String cookie) {
        headerMaps.put(OkHttpHeader.HttpReqHead.COOKIE, cookie);
        return this;
    }

    /**
     * 请求内容长度
     * 例如：Content-Length: 348
     *
     * @param contentLength length
     * @return OkHttpHeader
     */
    public OkHttpHeader contentLength(String contentLength) {
        headerMaps.put(OkHttpHeader.HttpReqHead.CONTENT_LENGTH, contentLength);
        return this;
    }

    /**
     * 请求的与实体对应的MIME信息
     * 例如：Content-Type: application/x-www-form-urlencoded
     * 常量类：{@link HttpConstants}
     *
     * @param contentType contentType
     * @return OkHttpHeader
     */
    public OkHttpHeader contentType(String contentType) {
        headerMaps.put(OkHttpHeader.HttpReqHead.CONTENT_TYPE, contentType);
        return this;
    }

    /**
     * 请求发送的日期和时间
     * 例如：Date: Tue, 15 Nov 2010 08:12:31 GMT，时间戳，字符串时间等
     *
     * @param date datetime
     * @return OkHttpHeader
     */
    public OkHttpHeader date(String date) {
        headerMaps.put(OkHttpHeader.HttpReqHead.DATE, date);
        return this;
    }

    /**
     * 请求的特定的服务器行为
     * 例如：Expect: 100-continue
     *
     * @param expect expct
     * @return OkHttpHeader
     */
    public OkHttpHeader expect(String expect) {
        headerMaps.put(OkHttpHeader.HttpReqHead.EXPECT, expect);
        return this;
    }

    /**
     * 发出请求的用户的Email
     * 例如：From: user@email.com
     *
     * @param from 表单
     * @return OkHttpHeader
     */
    public OkHttpHeader from(String from) {
        headerMaps.put(OkHttpHeader.HttpReqHead.FROM, from);
        return this;
    }

    /**
     * 指定请求的服务器的域名和端口号
     * 例如：Host: github.com
     *
     * @param host host
     * @return OkHttpHeader
     */
    public OkHttpHeader host(String host) {
        headerMaps.put(OkHttpHeader.HttpReqHead.HOST, host);
        return this;
    }

    /**
     * 只有请求内容与实体相匹配才有效
     * 例如：If-Match: “737060cd8c284d8af7ad3082f209582d”
     *
     * @param ifMatch match
     * @return OkHttpHeader
     */
    public OkHttpHeader ifMatch(String ifMatch) {
        headerMaps.put(OkHttpHeader.HttpReqHead.IF_MATCH, ifMatch);
        return this;
    }

    /**
     * 如果请求的部分在指定时间之后被修改则请求成功，未被修改则返回304代码
     * 例如：If-Modified-Since: Sat, 29 Oct 2010 19:43:31 GMT
     *
     * @param ifModifiedSince ModifiedSince
     * @return OkHttpHeader
     */
    public OkHttpHeader ifModifiedSince(String ifModifiedSince) {
        headerMaps.put(OkHttpHeader.HttpReqHead.IF_MODIFIED_SINCE, ifModifiedSince);
        return this;
    }

    /**
     * 如果内容未改变返回304代码，参数为服务器先前发送的Etag，与服务器回应的Etag比较判断是否改变
     * 例如：If-None-Match: “737060cd8c284d8af7ad3082f209582d”
     *
     * @param ifNoneMatch noneMatch
     * @return OkHttpHeader
     */
    public OkHttpHeader ifNoneMatch(String ifNoneMatch) {
        headerMaps.put(OkHttpHeader.HttpReqHead.IF_NONE_MATCH, ifNoneMatch);
        return this;
    }

    /**
     * User-Agent的内容包含发出请求的用户信息
     *
     * @param userAgent userAgent
     * @return OkHttpHeader
     */
    public OkHttpHeader userAgent(String userAgent) {
        headerMaps.put(OkHttpHeader.HttpReqHead.USER_AGENT, userAgent);
        return this;
    }

    /**
     * 关于消息实体的警告信息
     *
     * @param warning warning
     * @return OkHttpHeader
     */
    public OkHttpHeader warning(String warning) {
        headerMaps.put(OkHttpHeader.HttpReqHead.WARNING, warning);
        return this;
    }

    /**
     * 设置此HTTP连接的持续时间（超时时间）
     * 例如：Keep-Alive: 300
     *
     * @param keepAlive keepAlive
     * @return OkHttpHeader
     */
    public OkHttpHeader keepAlive(String keepAlive) {
        headerMaps.put(OkHttpHeader.HttpReqHead.KEEP_ALIVE, keepAlive);
        return this;
    }

    /**
     * trace Id
     *
     * @param traceId traceId
     * @return OkHttpHeader
     */
    public OkHttpHeader traceId(String traceId) {
        headerMaps.put(OkHttpHeader.HttpReqHead.TRACE_ID, traceId);
        return this;
    }

    public String accept() {
        return get(OkHttpHeader.HttpReqHead.ACCEPT);
    }

    public String acceptCharset() {
        return get(OkHttpHeader.HttpReqHead.ACCEPT_CHARSET);
    }

    public String acceptEncoding() {
        return get(OkHttpHeader.HttpReqHead.ACCEPT_ENCODING);
    }

    public String acceptLanguage() {
        return get(OkHttpHeader.HttpReqHead.ACCEPT_LANGUAGE);
    }

    public String acceptRanges() {
        return get(OkHttpHeader.HttpReqHead.ACCEPT_RANGES);
    }

    public String authorization() {
        return get(OkHttpHeader.HttpReqHead.AUTHORIZATION);
    }

    public String cacheControl() {
        return get(OkHttpHeader.HttpReqHead.CACHE_CONTROL);
    }

    public String connection() {
        return get(OkHttpHeader.HttpReqHead.CONNECTION);
    }

    public String cookie() {
        return get(OkHttpHeader.HttpReqHead.COOKIE);
    }

    public String contentLength() {
        return get(OkHttpHeader.HttpReqHead.CONTENT_LENGTH);
    }

    public String contentType() {
        return get(OkHttpHeader.HttpReqHead.CONTENT_TYPE);
    }

    public String date() {
        return get(OkHttpHeader.HttpReqHead.DATE);
    }

    public String expect() {
        return get(OkHttpHeader.HttpReqHead.EXPECT);
    }

    public String from() {
        return get(OkHttpHeader.HttpReqHead.FROM);
    }

    public String host() {
        return get(OkHttpHeader.HttpReqHead.HOST);
    }

    public String ifMatch() {
        return get(OkHttpHeader.HttpReqHead.IF_MATCH);
    }

    public String ifModifiedSince() {
        return get(OkHttpHeader.HttpReqHead.IF_MODIFIED_SINCE);
    }

    public String ifNoneMatch() {
        return get(OkHttpHeader.HttpReqHead.IF_NONE_MATCH);
    }

    public String ifUnmodifiedSince() {
        return get(OkHttpHeader.HttpReqHead.IF_UNMODIFIED_SINCE);
    }

    public String maxForwards() {
        return get(OkHttpHeader.HttpReqHead.MAX_FORWARDS);
    }

    public String upgrade() {
        return get(OkHttpHeader.HttpReqHead.UPGRADE);
    }

    public String userAgent() {
        return get(OkHttpHeader.HttpReqHead.USER_AGENT);
    }

    public String warning() {
        return get(OkHttpHeader.HttpReqHead.WARNING);
    }

    public String keepAlive() {
        return get(OkHttpHeader.HttpReqHead.KEEP_ALIVE);
    }

    public String traceId() {
        return get(OkHttpHeader.HttpReqHead.TRACE_ID);
    }

    /**
     * 获取head信息
     *
     * @return http header
     */
    private String get(String headName) {
        if (headerMaps.containsKey(headName)) {
            return headerMaps.get(headName);
        }
        return null;
    }

    /**
     * 返回header头信息
     *
     * @return Header[]
     */
    public Headers build() {
        return Headers.of(headerMaps);
    }

    /**
     * Http header可配置信息常量
     */
    private static class HttpReqHead {
        private static final String ACCEPT = "Accept";
        private static final String ACCEPT_CHARSET = "Accept-Charset";
        private static final String ACCEPT_ENCODING = "Accept-Encoding";
        private static final String ACCEPT_LANGUAGE = "Accept-Language";
        private static final String ACCEPT_RANGES = "Accept-Ranges";
        private static final String AUTHORIZATION = "Authorization";
        private static final String CACHE_CONTROL = "Cache-Control";
        private static final String CONNECTION = "Connection";
        private static final String COOKIE = "Cookie";
        private static final String CONTENT_LENGTH = "Content-Length";
        private static final String CONTENT_TYPE = "Content-Type";
        private static final String DATE = "Date";
        private static final String EXPECT = "Expect";
        private static final String FROM = "From";
        private static final String HOST = "Host";
        private static final String IF_MATCH = "If-Match ";
        private static final String IF_MODIFIED_SINCE = "If-Modified-Since";
        private static final String IF_NONE_MATCH = "If-None-Match";
        private static final String IF_UNMODIFIED_SINCE = "If-Unmodified-Since";
        private static final String KEEP_ALIVE = "Keep-Alive";
        private static final String MAX_FORWARDS = "Max-Forwards";
        private static final String UPGRADE = "Upgrade";
        private static final String USER_AGENT = "User-Agent";
        private static final String WARNING = "Warning";
        private static final String TRACE_ID = "traceId";
    }

//    public static void main(String[] args) {
//        Headers headers=OkHttpHeader.newBuilder()
//                .accept("*/*")
//                .acceptCharset("UTF-8")
//                .acceptEncoding("compress, gzip")
//                .acceptLanguage("zh_CN")
//                .acceptRanges("bytes")
//                .authorization("Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==")
//                .cacheControl("no-cache")
//                .connection("keep-alive")
//                .cookie("$Version=1; Skin=new;")
//                .contentLength("1024")
//                .contentType("application/x-www-form-urlencoded")
//                .date("Tue, 15 Nov 2010 08:12:31 GMT")
//                .expect("100-continue")
//                .from("user@dian.so")
//                .host("dian.so")
//                .ifMatch("If-Match: 737060cd8c284d8af7ad3082f209582d")
//                .ifModifiedSince("Sat, 29 Oct 2010 19:43:31 GMT")
//                .ifNoneMatch("If-None-Match: 737060cd8c284d8af7ad3082f209582d")
//                .userAgent("UA")
//                .warning("warning")
//                .build();
//        System.out.println(headers.toString());
//    }

}