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

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;

import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * HttpConfig
 *
 * @author ${baizhang}
 * @version $Id: HttpBuilder.java, v 0.1 2018-04-19 下午5:44 Exp $
 */
@SuppressWarnings("unused")
public class HttpConfig {

    private HttpConfig() {
    }

    /**
     * 获取实例
     *
     * @return HttpConfig
     */
    public static HttpConfig custom() {
        return new HttpConfig();
    }

    /**
     * HttpClient对象
     */
    private HttpClient client;

    private CloseableHttpClient httpClient;

    /**
     * Header信息
     */
    private Header[] headers;

    /**
     * 是否返回response的headers
     */
    private boolean isReturnRespHeaders;

    /**
     * 请求方法，默认为GET方法
     */
    private HttpMethods method = HttpMethods.GET;

    /**
     * 请求方法名称
     */
    private String methodName;

    /**
     * 用于cookie操作
     */
    private HttpContext context;

    /**
     * 以json格式作为输入参数
     */
    private String json;

    /**
     * 输入输出编码
     */
    private String encoding = Charset.defaultCharset().displayName();

    /**
     * 输入编码
     */
    private String inenc;

    /**
     * 输出编码
     */
    private String outenc;

    /**
     * 设置RequestConfig
     */
    private RequestConfig requestConfig;

    /**
     * 解决多线程下载时，stream被close的问题，使用需要调用remove
     */
    private static final ThreadLocal<OutputStream> OUTS = new ThreadLocal<>();

    /**
     * 解决多线程处理时，url被覆盖问题，使用需要调用remove
     */
    private static final ThreadLocal<String> URLS = new ThreadLocal<>();

    /**
     * 解决多线程处理时，url被覆盖问题
     */
    private static final ThreadLocal<Map<String, Object>> MAPS = new ThreadLocal<>();

    /**
     * HttpClient对象
     */
    public HttpConfig client(HttpClient client) {
        this.client = client;
        return this;
    }

    /**
     * 资源url
     */
    public HttpConfig url(String url) {
        URLS.set(url);
        return this;
    }

    /**
     * Header头信息
     */
    public HttpConfig headers(Header[] headers) {
        this.headers = headers;
        return this;
    }

    /**
     * Header头信息(是否返回response中的headers)
     */
    public HttpConfig headers(Header[] headers, boolean isReturnRespHeaders) {
        this.headers = headers;
        this.isReturnRespHeaders = isReturnRespHeaders;
        return this;
    }

    /**
     * 请求方法
     */
    public HttpConfig method(HttpMethods method) {
        this.method = method;
        return this;
    }

    /**
     * 请求方法
     */
    public HttpConfig methodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    /**
     * cookie操作相关
     */
    public HttpConfig context(HttpContext context) {
        this.context = context;
        return this;
    }

    /**
     * 传递参数
     */
    public HttpConfig map(Map<String, Object> map) {
        Map<String, Object> m = MAPS.get();
        if (m == null || map == null) {
            m = map;
        } else {
            m.putAll(map);
        }
        MAPS.set(m);
        return this;
    }

    /**
     * 以json格式字符串作为参数
     */
    public HttpConfig json(String json) {
        this.json = json;
        Map<String, Object> map = new HashMap<>(3);
        map.put(Utils.ENTITY_JSON, json);
        MAPS.set(map);
        return this;
    }

    /**
     * 上传文件时用到
     */
    public HttpConfig files(String[] filePaths) {
        return files(filePaths, "file");
    }

    /**
     * 上传文件时用到
     *
     * @param filePaths 待上传文件所在路径
     * @return HttpConfig
     */
    public HttpConfig files(String[] filePaths, String inputName) {
        return files(filePaths, inputName, false);
    }

    /**
     * 上传文件时用到
     *
     * @param filePaths                     待上传文件所在路径
     * @param inputName                     即file input 标签的name值，默认为file
     * @param forceRemoveContentTypeCharset 是否需要剔除参数
     * @return HttpConfig
     */
    public HttpConfig files(String[] filePaths, String inputName, boolean forceRemoveContentTypeCharset) {
        Map<String, Object> map = MAPS.get();
        if (map == null) {
            map = new HashMap<>(16);
        }
        map.put(Utils.ENTITY_MULTIPART, filePaths);
        map.put(Utils.ENTITY_MULTIPART + ".name", inputName);
        map.put(Utils.ENTITY_MULTIPART + ".rmCharset", forceRemoveContentTypeCharset);
        MAPS.set(map);
        return this;
    }

    /**
     * 输入输出编码
     */
    public HttpConfig encoding(String encoding) {
        //设置输入输出
        inenc(encoding);
        outenc(encoding);
        this.encoding = encoding;
        return this;
    }

    /**
     * 输入编码
     *
     * @param inenc 编码类型
     * @return HttpConfig
     */
    public HttpConfig inenc(String inenc) {
        this.inenc = inenc;
        return this;
    }

    /**
     * 输出编码
     *
     * @param outenc 编码类型
     * @return HttpConfig
     */
    public HttpConfig outenc(String outenc) {
        this.outenc = outenc;
        return this;
    }

    /**
     * 输出流对象
     *
     * @param out 输出对象
     * @return HttpConfig
     */
    public HttpConfig out(OutputStream out) {
        OUTS.set(out);
        return this;
    }

    /**
     * 设置超时时间
     *
     * @param timeout 单位：毫秒
     * @return HttpConfig
     */
    public HttpConfig timeout(int timeout) {
        return timeout(timeout, true);
    }

    /**
     * 设置超时时间以及是否允许网页重定向（自动跳转 302）
     *
     * @param timeout  单位：毫秒
     * @param redirect 是否允许自动跳转
     * @return HttpConfig
     */
    public HttpConfig timeout(int timeout, boolean redirect) {
        // 配置请求的超时设置
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(timeout)
                .setConnectTimeout(timeout)
                .setSocketTimeout(timeout)
                .setRedirectsEnabled(redirect)
                .build();
        return timeout(config);
    }

    /**
     * 设置代理、超时时间、允许网页重定向等
     *
     * @param requestConfig 超时时间，单位：毫秒
     * @return HttpConfig
     */
    public HttpConfig timeout(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
        return this;
    }

    /**
     * 清除ThreadLocal 数据
     */
    public void cleanThreadLocal() {
        URLS.remove();
        OUTS.remove();
        MAPS.remove();
    }

    public HttpClient client() {
        return client;
//        return httpClient;
    }

    public Header[] headers() {
        return headers;
    }

    public boolean isReturnRespHeaders() {
        return isReturnRespHeaders;
    }

    public String url() {
        return URLS.get();
    }

    public HttpMethods method() {
        return method;
    }

    public String methodName() {
        return methodName;
    }

    public HttpContext context() {
        return context;
    }

    public Map<String, Object> map() {
        return MAPS.get();
    }

    public String json() {
        return json;
    }

    public String encoding() {
        return encoding;
    }

    public String inenc() {
        return inenc == null ? encoding : inenc;
    }

    public String outenc() {
        return outenc == null ? encoding : outenc;
    }

    public OutputStream out() {
        return OUTS.get();
    }

}