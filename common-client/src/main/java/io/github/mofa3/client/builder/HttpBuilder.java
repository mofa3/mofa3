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
package io.github.mofa3.client.builder;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import io.github.mofa3.client.common.SSLProtocolVersion;
import io.github.mofa3.client.common.Ssls;
import io.github.mofa3.lang.exception.ThirdPartyException;
import io.github.mofa3.lang.common.constant.MofaConstants;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

/**
 * HttpBuilder
 * 推荐使用{@link io.github.mofa3.client.okhttp3.OkHttpRequest}
 *
 * @author ${baizhang}
 * @version $Id: HttpBuilder.java, v 0.1 2018-04-19 下午5:44 Exp $
 */
@Deprecated
@SuppressWarnings("unused")
public class HttpBuilder extends HttpClientBuilder {

    /**
     * 是否设置了连接池
     */
    public boolean isSetPool = false;

    /**
     * ssl 协议版本，默认使用SSLv3协议
     */
    private SSLProtocolVersion sslpv = SSLProtocolVersion.SSLv3;

    /**
     * 配置ssl
     */
    private Ssls ssls = Ssls.getInstance();

    private HttpBuilder() {
    }

    public static HttpBuilder custom() {
        return new HttpBuilder();
    }

    /**
     * 设置ssl安全链接
     *
     * @return HttpBuilder
     * @throws ThirdPartyException Exception
     */
    public HttpBuilder ssl() throws ThirdPartyException {
        return (HttpBuilder) this.setSSLSocketFactory(ssls.getSslConnSocketFactory(sslpv));
    }

    ///**
    // * 设置自定义sslcontext
    // *
    // * @param keyStorePath        密钥库路径
    // * @return
    // * @throws HttpProcessException
    // */
    //public HttpBuilder ssl(String keyStorePath) throws HttpProcessException {
    //	return ssl(keyStorePath, "nopassword");
    //}

    /**
     * 设置自定义sslcontext
     *
     * @param keyStorePath 密钥库路径
     * @param keyStorepass 密钥库密码
     * @return HttpBuilder
     * @throws ThirdPartyException Exception
     */
    public HttpBuilder ssl(String keyStorePath, String keyStorepass) throws ThirdPartyException {
        return ssl();
    }

    /**
     * 设置连接池（默认开启https）
     *
     * @param maxTotal           最大连接数
     * @param defaultMaxPerRoute 每个路由默认连接数
     * @return HttpBuilder
     * @throws ThirdPartyException Exception
     */
    public HttpBuilder pool(int maxTotal, int defaultMaxPerRoute) throws ThirdPartyException {
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                .<ConnectionSocketFactory>create()
                .register(MofaConstants.HTTP, PlainConnectionSocketFactory.INSTANCE)
                .register(MofaConstants.HTTPS, SSLConnectionSocketFactory
                        .getSocketFactory()).build();
        //设置连接池大小
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        // Increase max total connection to $maxTotal
        connManager.setMaxTotal(maxTotal);
        // Increase default max connection per route to $defaultMaxPerRoute
        connManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        //connManager.setMaxPerRoute(route, max);// Increase max connections for $route(eg：localhost:80) to 50
        isSetPool = true;
        return (HttpBuilder) this.setConnectionManager(connManager);
    }

    /**
     * 设置代理
     *
     * @param hostOrIP 代理host或者ip
     * @param port     代理端口
     * @return HttpBuilder
     */
    public HttpBuilder proxy(String hostOrIP, int port) {
        // 依次是代理地址，代理端口号，协议类型
        HttpHost proxy = new HttpHost(hostOrIP, port, MofaConstants.HTTP);
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        return (HttpBuilder) this.setRoutePlanner(routePlanner);
    }

    /**
     * 重试（如果请求是幂等的，就再次尝试）
     *
     * @param tryTimes 重试次数
     * @return HttpBuilder
     */
    public HttpBuilder retry(final int tryTimes) {
        return retry(tryTimes, false);
    }

    /**
     * 重试（如果请求是幂等的，就再次尝试）
     *
     * @param tryTimes               重试次数
     * @param retryWhenInterruptedIO 连接拒绝时，是否重试
     * @return HttpBuilder
     */
    public HttpBuilder retry(final int tryTimes, final boolean retryWhenInterruptedIO) {
        // 请求重试处理
        HttpRequestRetryHandler httpRequestRetryHandler = (exception, executionCount, context) -> {
            // 如果已经重试了n次，就放弃
            if (executionCount >= tryTimes) {
                return false;
            }
            // 如果服务器丢掉了连接，那么就重试
            if (exception instanceof NoHttpResponseException) {
                return true;
            }
            // 不要重试SSL握手异常
            if (exception instanceof SSLHandshakeException) {
                return false;
            }
            // 超时
            if (exception instanceof InterruptedIOException) {
                //return false;
                return retryWhenInterruptedIO;
            }
            // 目标服务器不可达
            if (exception instanceof UnknownHostException) {
                return true;
            }
            // SSL握手异常
            if (exception instanceof SSLException) {
                return false;
            }
            // 连接超时
            if (exception instanceof ConnectTimeoutException) {
                return false;
            }
            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            // 如果请求是幂等的，就再次尝试
            return !(request instanceof HttpEntityEnclosingRequest);
        };
        this.setRetryHandler(httpRequestRetryHandler);
        return this;
    }

    /**
     * 设置ssl版本<br>
     * 如果要设置ssl版本，必须<b><font color=red>先调用此方法，再调用ssl方法</font><br>
     * 仅支持 SSLv3，TSLv1，TSLv1.1，TSLv1.2</b>
     *
     * @param ssLpv 协议类型
     * @return HttpBuilder
     */
    public HttpBuilder ssLpv(String ssLpv) {
        return ssLpv(SSLProtocolVersion.find(ssLpv));
    }

    /**
     * 设置ssl版本<br>
     * 如果要设置ssl版本，必须<b>先调用此方法，再调用ssl方法<br>
     * 仅支持 SSLv3，TSLv1，TSLv1.1，TSLv1.2</b>
     *
     * @param ssLpv 协议类型
     * @return HttpBuilder
     */
    public HttpBuilder ssLpv(SSLProtocolVersion ssLpv) {
        this.sslpv = ssLpv;
        return this;
    }
}