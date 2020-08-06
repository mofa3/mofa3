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

import io.github.mofa3.client.common.HttpMethods;
import io.github.mofa3.lang.exception.ThirdPartyException;
import io.github.mofa3.lang.util.JsonUtil;
import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.CookieJar;
import okhttp3.EventListener;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.internal.http.HttpMethod;
import okhttp3.logging.HttpLoggingInterceptor;

import javax.annotation.Nullable;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * http请求对象构建，大部分是OkHttpClient{@link OkHttpClient}、HttpUrl{@link HttpUrl}做一次封装
 *
 * @author baizhang
 * @version: v 0.1 OkHttpRequest.java, 2019-07-30 19:03 Exp $
 */
public class OkHttpRequest {

    /**
     * application/json
     */
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * okhttp client singleInstance
     */
    private final OkHttpClient httpClient = OkHttpClientSingle.INSTANCE.getInstance();

    /**
     * request builder
     */
    private final Request.Builder requestBuilder;

    /**
     * url builder
     */
    private final HttpUrl.Builder uriBuilder;

    /**
     * http method
     */
    private final HttpMethods httpMethod;

    /**
     * request body
     */
    @Nullable
    private RequestBody requestBody;

    /**
     * 开启自动重定向
     */
    @Nullable
    private Boolean followRedirects;

    /**
     * 开启自动HTTPS到HTTP，以及HTTP到HTTPS的重定向
     */
    @Nullable
    private Boolean followSslRedirects;

    /**
     * 连接超时时间，单位：ms
     */
    @Nullable
    private int connectTimeout;

    /**
     * 读超时时间，单位：ms
     */
    @Nullable
    private int readTimeout;

    /**
     * 写超时时间，单位：ms
     */
    @Nullable
    private int writeTimeout;

    /**
     * http header
     */
    @Nullable
    private OkHttpHeader okHttpHeader;

    /**
     * 日志级别
     */
    @Nullable
    private HttpLoggingInterceptor.Level logLevel;

    /**
     * cookie
     */
    @Nullable
    private CookieJar cookieJar;

    /**
     * 事件监听
     */
    @Nullable
    private EventListener eventListener;

    /**
     * 拦截器
     */
    private final List<Interceptor> interceptors = new ArrayList<>();

    /**
     * 身份验证
     */
    @Nullable
    private Authenticator authenticator;

    /**
     * 代理
     */
    @Nullable
    private Proxy proxy;

    /**
     * 代理选择器
     * 指定Proxy{@link Proxy}，需要选择使用代理，选择器会返回多个代理，按顺序使用，直到连接成功
     */
    @Nullable
    private ProxySelector proxySelector;

    /**
     * 代理身份验证
     */
    @Nullable
    private Authenticator proxyAuthenticator;

    /**
     * 重试策略
     */
    @Nullable
    private RetryPolicy retryPolicy;

    /**
     * hostname 验证
     */
    @Nullable
    private HostnameVerifier hostnameVerifier;

    /**
     * SSL factory
     */
    @Nullable
    private SSLSocketFactory sslSocketFactory;

    /**
     * SSL 证书管理
     */
    @Nullable
    private X509TrustManager trustManager;

    /**
     * GET 请求
     *
     * @param uri URI
     * @return OkHttpRequest
     */
    public static OkHttpRequest get(final URI uri) {
        return get(uri.toString());
    }

    /**
     * GET 请求
     *
     * @param url url
     * @return OkHttpRequest
     */
    public static OkHttpRequest get(final String url) {
        return new OkHttpRequest(new Request.Builder(), url, HttpMethods.GET);
    }

    /**
     * POST 请求
     *
     * @param uri URI
     * @return OkHttpRequest
     */
    public static OkHttpRequest post(final URI uri) {
        return post(uri.toString());
    }

    /**
     * POST 请求
     *
     * @param url url
     * @return OkHttpRequest
     */
    public static OkHttpRequest post(final String url) {
        return new OkHttpRequest(new Request.Builder(), url, HttpMethods.POST);
    }

    /**
     * PATCH 请求
     *
     * @param uri URI
     * @return OkHttpRequest
     */
    public static OkHttpRequest patch(final URI uri) {
        return patch(uri.toString());
    }

    /**
     * PATCH 请求
     *
     * @param url url
     * @return OkHttpRequest
     */
    public static OkHttpRequest patch(final String url) {
        return new OkHttpRequest(new Request.Builder(), url, HttpMethods.PATCH);
    }

    /**
     * PUT 请求
     *
     * @param uri URI
     * @return OkHttpRequest
     */
    public static OkHttpRequest put(final URI uri) {
        return put(uri.toString());
    }

    /**
     * PUT 请求
     *
     * @param url url
     * @return OkHttpRequest
     */
    public static OkHttpRequest put(final String url) {
        return new OkHttpRequest(new Request.Builder(), url, HttpMethods.PUT);
    }

    /**
     * DELETE 请求
     *
     * @param uri URI
     * @return OkHttpRequest
     */
    public static OkHttpRequest delete(final URI uri) {
        return delete(uri.toString());
    }

    /**
     * DELETE 请求
     *
     * @param url url
     * @return OkHttpRequest
     */
    public static OkHttpRequest delete(final String url) {
        return new OkHttpRequest(new Request.Builder(), url, HttpMethods.DELETE);
    }

    /**
     * HEAD 请求
     *
     * @param uri URI
     * @return OkHttpRequest
     */
    public static OkHttpRequest head(final URI uri) {
        return head(uri.toString());
    }

    /**
     * HEAD 请求
     *
     * @param url url
     * @return OkHttpRequest
     */
    public static OkHttpRequest head(final String url) {
        return new OkHttpRequest(new Request.Builder(), url, HttpMethods.HEAD);
    }

    /**
     * TRACE 请求
     *
     * @param uri URI
     * @return OkHttpRequest
     */
    public static OkHttpRequest trace(final URI uri) {
        return trace(uri.toString());
    }

    /**
     * TRACE 请求
     *
     * @param url url
     * @return OkHttpRequest
     */
    public static OkHttpRequest trace(final String url) {
        return new OkHttpRequest(new Request.Builder(), url, HttpMethods.TRACE);
    }

    /**
     * OPTIONS 请求
     *
     * @param uri URI
     * @return OkHttpRequest
     */
    public static OkHttpRequest options(final URI uri) {
        return options(uri.toString());
    }

    /**
     * OPTIONS 请求
     *
     * @param url url
     * @return OkHttpRequest
     */
    public static OkHttpRequest options(final String url) {
        return new OkHttpRequest(new Request.Builder(), url, HttpMethods.OPTIONS);
    }

    public OkHttpRequest query(String query) {
        this.uriBuilder.query(query);
        return this;
    }

    public OkHttpRequest encodedQuery(String encodedQuery) {
        this.uriBuilder.encodedQuery(encodedQuery);
        return this;
    }

    public OkHttpRequest addQueryParameter(String name, @Nullable Object value) {
        this.uriBuilder.addQueryParameter(name, null == value ? null : String.valueOf(value));
        return this;
    }

    public OkHttpRequest addEncodedQueryParameter(String encodedName, @Nullable Object encodedValue) {
        this.uriBuilder.addEncodedQueryParameter(encodedName, null == encodedValue ? null : String.valueOf(encodedValue));
        return this;
    }

    /**
     * 设置http header
     * {@link OkHttpHeader}
     *
     * @param okHttpHeader heder
     * @return OkHttpRequest
     */
    public OkHttpRequest header(OkHttpHeader okHttpHeader) {
        this.okHttpHeader = okHttpHeader;
        return this;
    }

    /**
     * 以form提交请求
     *
     * @param formBody
     * @return
     */
    OkHttpRequest form(FormBody formBody) {
        this.requestBody = formBody;
        return this;
    }

    OkHttpRequest multipartForm(MultipartBody multipartBody) {
        this.requestBody = multipartBody;
        return this;
    }

    public OkHttpRequest body(RequestBody requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public OkHttpRequest body(final MediaType mediaType, final String body) {
        this.requestBody= RequestBody.Companion.create(body, mediaType);
        return this;
    }

    /**
     * http 请求、响应日志，如果使用，默认全部输出
     * 日志示例参考{@link HttpLoggingInterceptor.Level}枚举值BODY的注释
     *
     * @return OkHttpRequest
     */
    public OkHttpRequest log() {
        this.logLevel = HttpLoggingInterceptor.Level.BODY;
        return this;
    }

    /**
     * http 请求、响应日志，指定输出
     * 日志示例参考{@link HttpLoggingInterceptor.Level}
     *
     * @param logLevel 日志输出级别
     * @return OkHttpRequest
     */
    public OkHttpRequest log(HttpLoggingInterceptor.Level logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    /**
     * 开启自动重定向
     *
     * @param followRedirects 是否开启重定向
     * @return OkHttpRequest
     */
    public OkHttpRequest followRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
        return this;
    }

    /**
     * 开启自动HTTPS到HTTP，以及HTTP到HTTPS的重定向
     *
     * @param followSslRedirects 是否开启https、http的相互重定向
     * @return OkHttpRequest
     */
    public OkHttpRequest followSslRedirects(boolean followSslRedirects) {
        this.followSslRedirects = followSslRedirects;
        return this;
    }

    /**
     * 身份验证
     *
     * @param authenticator Authenticator
     * @return OkHttpRequest
     */
    public OkHttpRequest authenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
        return this;
    }

    /**
     * 自定义EventListener监听处理
     *
     * @param eventListener EventListener
     * @return OkHttpRequest
     */
    public OkHttpRequest eventListener(EventListener eventListener) {
        this.eventListener = eventListener;
        return this;
    }

    /**
     * 增加自定义拦截器
     * 自定义拦截器实现Interceptor即可
     *
     * @param interceptor 拦截器对象
     * @return OkHttpRequest
     */
    public OkHttpRequest interceptor(Interceptor interceptor) {
        this.interceptors.add(interceptor);
        return this;
    }

    /**
     * 设置cookie
     *
     * @param cookieJar cookie
     * @return OkHttpRequest
     */
    public OkHttpRequest cookieManager(CookieJar cookieJar) {
        this.cookieJar = cookieJar;
        return this;
    }

    /**
     * 连接超时配置
     *
     * @param connectTimeout connectTimeout单位：毫秒 ms
     * @return OkHttpRequest
     */
    public OkHttpRequest connectTimeout(final int connectTimeout) {
        this.connectTimeout = connectTimeout;
        return this;
    }

    /**
     * 读超时时间
     * 文件流，大数据返回超时，可适当延长
     *
     * @param readTimeout readTimeout单位：毫秒 ms
     * @return OkHttpRequest
     */
    public OkHttpRequest readTimeout(final int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    /**
     * 写超时时间
     *
     * @param writeTimeout writeTimeout单位：毫秒 ms
     * @return OkHttpRequest
     */
    public OkHttpRequest writeTimeout(final int writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }

    /**
     * 配置代理
     *
     * @param address 代理域名和端口
     * @return OkHttpRequest
     */
    public OkHttpRequest proxy(final InetSocketAddress address) {
        this.proxy = new Proxy(Proxy.Type.HTTP, address);
        return this;
    }

    /**
     * 代理选择器
     *
     * @param proxySelector 代理
     * @return OkHttpRequest
     */
    public OkHttpRequest proxySelector(final ProxySelector proxySelector) {
        this.proxySelector = proxySelector;
        return this;
    }

    /**
     * 代理用户校验
     *
     * @param proxyAuthenticator 代理用户校验
     * @return OkHttpRequest
     */
    public OkHttpRequest proxyAuthenticator(final Authenticator proxyAuthenticator) {
        this.proxyAuthenticator = proxyAuthenticator;
        return this;
    }

    /**
     * 开启重试策略，默认策略，重试三次
     *
     * @return OkHttpRequest
     */
    public OkHttpRequest retry() {
        this.retryPolicy = RetryPolicy.INSTANCE;
        return this;
    }

    /**
     * 开启重试策略
     *
     * @param maxAttempts 重试次数
     * @param sleepMillis 失败重试时间间隔，单位：毫秒 ms
     * @return OkHttpRequest
     */
    public OkHttpRequest retry(int maxAttempts, long sleepMillis) {
        this.retryPolicy = new RetryPolicy(maxAttempts, sleepMillis);
        return this;
    }

    /**
     * 认证配置
     *
     * @param hostnameVerifier HostnameVerifier
     * @return OkHttpRequest
     */
    public OkHttpRequest hostnameVerifier(HostnameVerifier hostnameVerifier) {
        this.hostnameVerifier = hostnameVerifier;
        return this;
    }

    /**
     * SSL 请求配置
     *
     * @param sslSocketFactory SSLFactory
     * @param trustManager     证书类型
     * @return OkHttpRequest
     */
    public OkHttpRequest sslSocketFactory(SSLSocketFactory sslSocketFactory, X509TrustManager trustManager) {
        this.sslSocketFactory = sslSocketFactory;
        this.trustManager = trustManager;
        return this;
    }

    /**
     * 对象以json参数请求
     *
     * @param body 请求对象
     * @return OkHttpRequest
     */
    public OkHttpRequest json(Object body) {
        return body(MEDIA_TYPE_JSON, JsonUtil.beanToJson(body));
    }

    /**
     * 以json字符串请求
     *
     * @param json json字符串
     * @return OkHttpRequest
     */
    public OkHttpRequest json(String json) {
        return body(MEDIA_TYPE_JSON, json);
    }

    private OkHttpRequest(final Request.Builder requestBuilder, String url, HttpMethods httpMethod) {
        HttpUrl httpUrl = HttpUrl.parse(url);
        if (null == httpUrl) {
            throw new IllegalArgumentException(String.format("url解析错误：%s：「%s」",
                    httpMethod.getName(), url));
        }
        this.requestBuilder = requestBuilder;
        this.uriBuilder = httpUrl.newBuilder();
        this.httpMethod = httpMethod;
    }

    /**
     * 参数构建
     *
     * @param client okhttp client
     * @return Call
     */
    private Call buildHttpClient(final OkHttpClient client) {

        OkHttpClient.Builder builder = client.newBuilder();
        if (null != retryPolicy) {
            builder.addInterceptor(new RetryInterceptor(retryPolicy));
        }
        if (null != hostnameVerifier) {
            builder.hostnameVerifier(hostnameVerifier);
        }
        if (null != trustManager && null != sslSocketFactory) {
            builder.sslSocketFactory(sslSocketFactory, trustManager);
        }

        if (connectTimeout > 0) {
            builder.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
        }
        if (readTimeout > 0) {
            builder.readTimeout(readTimeout, TimeUnit.MILLISECONDS);
        }
        if (writeTimeout > 0) {
            builder.writeTimeout(writeTimeout, TimeUnit.MILLISECONDS);
        }

        if (null != hostnameVerifier) {
            builder.hostnameVerifier(hostnameVerifier);
        }
        if (null != eventListener) {
            builder.eventListener(eventListener);
        }
        if (!interceptors.isEmpty()) {
            builder.interceptors().addAll(interceptors);
        }

        if (null != proxy) {
            builder.proxy(proxy);
        }
        if (null != proxySelector) {
            builder.proxySelector(proxySelector);
        }
        if (null != proxyAuthenticator) {
            builder.proxyAuthenticator(proxyAuthenticator);
        }
        if (null != authenticator) {
            builder.authenticator(authenticator);
        }

        if (null != followRedirects) {
            builder.followRedirects(followRedirects);
        }
        if (null != followSslRedirects) {
            builder.followSslRedirects(followSslRedirects);
        }
        if (null != cookieJar) {
            builder.cookieJar(cookieJar);
        }
        Request request;
        if (null != logLevel) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(logLevel);
            builder.addInterceptor(loggingInterceptor);
        } else {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            logLevel = HttpLoggingInterceptor.Level.NONE;
            loggingInterceptor.setLevel(logLevel);
            builder.addInterceptor(loggingInterceptor);
        }

        requestBuilder.url(uriBuilder.build());
        if (null != okHttpHeader) {
            requestBuilder.headers(okHttpHeader.build());
        }
        if (null == requestBody && HttpMethod.requiresRequestBody(httpMethod.getName())) {
            request = requestBuilder.method(httpMethod.getName(), emptyBody()).build();
        } else {
            request = requestBuilder.method(httpMethod.getName(), requestBody).build();
        }

        return builder.build().newCall(request);
    }

    /**
     * 同步请求
     *
     * @return OkHttpResponse
     */
    public OkHttpResponse exec() {
        Call callable = buildHttpClient(httpClient);
        try {
            return new OkHttpResponse(callable.execute());
        } catch (IOException e) {
            throw new ThirdPartyException("OkHttp同步请求执行异常", e);
        }
    }

    /**
     * 异步请求
     *
     * @return AsyncCallable
     */
    public AsyncCallable async() {
        Call asyncCall = buildHttpClient(httpClient);
        return new AsyncCallable(asyncCall);
    }

    /**
     * 空请求体
     *
     * @return RequestBody
     */
    private static RequestBody emptyBody() {
        return RequestBody.create(null, new byte[0]);
    }
}