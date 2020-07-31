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
package io.github.mofa3.client.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import io.github.mofa3.client.builder.HttpBuilder;
import io.github.mofa3.client.common.HttpConfig;
import io.github.mofa3.client.common.HttpMethods;
import io.github.mofa3.client.common.Utils;
import io.github.mofa3.lang.exception.ThirdPartyException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 推荐使用{@link io.github.mofa3.client.okhttp3.OkHttpRequest}
 * <p>
 * httpclient util
 *
 * @author ${baizhang}
 * @version $Id: HttpClientUtil.java, v 0.1 2017-11-15 下午3:39 Exp $
 */
@Deprecated
@Slf4j
@SuppressWarnings("unused")
public class HttpClientUtil {
    /**
     * https前缀
     */
    private static final String HTTPS_PREFIX = "https://";
    /**
     * http 协议
     */
    private static HttpClient client4Http;

    /**
     * 默认采用的https协议的HttpClient对象
     */
    private static HttpClient client4Https;

    static {
        try {
            client4Http = HttpBuilder.custom().build();
            client4Https = HttpBuilder.custom().ssl().build();
        } catch (Exception e) {
            throw new ThirdPartyException("创建https协议的HttpClient对象出错", e.getCause());
        }
    }

    /**
     * 判定是否开启连接池、及url是http还是https <br>
     * 如果已开启连接池，则自动调用build方法，从连接池中获取client对象<br>
     * 否则，直接返回相应的默认client对象<br>
     *
     * @param config 请求参数配置
     * @throws ThirdPartyException httpException
     */
    private static void create(HttpConfig config) throws ThirdPartyException {
        //如果为空，设为默认client对象
        if (config.client() == null) {
            if (config.url().toLowerCase().startsWith(HTTPS_PREFIX)) {
                config.client(client4Https);
            } else {
                config.client(client4Http);
            }
        }
    }

    /**
     * 以Get方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @return 返回处理结果
     * @throws ThirdPartyException httpException
     */
    public static String get(HttpClient client, String url, Header[] headers, HttpContext context, String encoding)
            throws ThirdPartyException {
        return get(HttpConfig.custom().client(client).url(url).headers(headers).context(context).encoding(encoding));
    }

    /**
     * 以GET方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @return 返回处理结果
     * @throws ThirdPartyException httpException
     */
    public static String get(HttpConfig config) throws ThirdPartyException {
        return send(config.method(HttpMethods.GET));
    }

    /**
     * 以POST方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param headers  请求头信息
     * @param parasMap 请求参数
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @return 返回处理结果
     * @throws ThirdPartyException httpException
     */
    public static String post(HttpClient client, String url, Header[] headers, Map<String, Object> parasMap, HttpContext context,
                              String encoding) throws ThirdPartyException {
        return post(HttpConfig.custom().client(client).url(url).headers(headers).map(parasMap).context(context).encoding(encoding));
    }

    /**
     * 以POST方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @return 返回处理结果
     * @throws ThirdPartyException httpException
     */
    public static String post(HttpConfig config) throws ThirdPartyException {
        return send(config.method(HttpMethods.POST));
    }

    /**
     * 以PATCH方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param parasMap 请求参数
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @return 返回处理结果
     * @throws ThirdPartyException httpException
     */
    public static String patch(HttpClient client, String url, Map<String, Object> parasMap, Header[] headers, HttpContext context,
                               String encoding) throws ThirdPartyException {
        return patch(HttpConfig.custom().client(client).url(url).headers(headers).map(parasMap).context(context).encoding(encoding));
    }

    /**
     * 以PATCH方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @return 返回处理结果
     * @throws ThirdPartyException httpException
     */
    public static String patch(HttpConfig config) throws ThirdPartyException {
        return send(config.method(HttpMethods.PATCH));
    }

    /**
     * 下载文件
     *
     * @param client  client对象
     * @param url     资源地址
     * @param headers 请求头信息
     * @param context http上下文，用于cookie操作
     * @param out     输出流
     * @return 返回处理结果
     * @throws ThirdPartyException httpException
     */
    public static OutputStream down(HttpClient client, String url, Header[] headers, HttpContext context, OutputStream out)
            throws ThirdPartyException {
        return down(HttpConfig.custom().client(client).url(url).headers(headers).context(context).out(out));
    }

    /**
     * 下载文件
     *
     * @param config 请求参数配置
     * @return 返回处理结果
     * @throws ThirdPartyException httpException
     */
    public static OutputStream down(HttpConfig config) throws ThirdPartyException {
        if (config.method() == null) {
            config.method(HttpMethods.GET);
        }
        OutputStream out = fmt2Stream(execute(config), config.out());
        config.cleanThreadLocal();
        return out;
    }


    /**
     * 以HEAD方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @return 返回处理结果
     * @throws ThirdPartyException httpException
     */
    public static String head(HttpClient client, String url, Header[] headers, HttpContext context, String encoding)
            throws ThirdPartyException {
        return head(HttpConfig.custom().client(client).url(url).headers(headers).context(context).encoding(encoding));
    }

    /**
     * 以HEAD方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @return 返回处理结果
     * @throws ThirdPartyException httpException
     */
    public static String head(HttpConfig config) throws ThirdPartyException {
        return send(config.method(HttpMethods.HEAD));
    }

    /**
     * 以OPTIONS方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @return 返回处理结果
     * @throws ThirdPartyException httpException
     */
    public static String options(HttpClient client, String url, Header[] headers, HttpContext context, String encoding)
            throws ThirdPartyException {
        return options(HttpConfig.custom().client(client).url(url).headers(headers).context(context).encoding(encoding));
    }

    /**
     * 以OPTIONS方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @return 返回处理结果
     * @throws ThirdPartyException httpException
     */
    public static String options(HttpConfig config) throws ThirdPartyException {
        return send(config.method(HttpMethods.OPTIONS));
    }

    /**
     * 以TRACE方式，请求资源或服务
     *
     * @param client   client对象
     * @param url      资源地址
     * @param headers  请求头信息
     * @param context  http上下文，用于cookie操作
     * @param encoding 编码
     * @return 返回处理结果
     * @throws ThirdPartyException httpException
     */
    public static String trace(HttpClient client, String url, Header[] headers, HttpContext context, String encoding)
            throws ThirdPartyException {
        return trace(HttpConfig.custom().client(client).url(url).headers(headers).context(context).encoding(encoding));
    }

    /**
     * 以TRACE方式，请求资源或服务
     *
     * @param config 请求参数配置
     * @return 返回处理结果
     * @throws ThirdPartyException httpException
     */
    public static String trace(HttpConfig config) throws ThirdPartyException {
        return send(config.method(HttpMethods.TRACE));
    }

    /**
     * 查看资源链接情况，返回状态码
     *
     * @param client  client对象
     * @param url     资源地址
     * @param headers 请求头信息
     * @param context http上下文，用于cookie操作
     * @return 返回处理结果
     * @throws ThirdPartyException httpException
     */
    public static int status(HttpClient client, String url, Header[] headers, HttpContext context, HttpMethods method)
            throws ThirdPartyException {
        return status(HttpConfig.custom().client(client).url(url).headers(headers).context(context).method(method));
    }

    /**
     * 查看资源链接情况，返回状态码
     *
     * @param config 请求参数配置
     * @return 返回处理结果
     * @throws ThirdPartyException httpException
     */
    public static int status(HttpConfig config) throws ThirdPartyException {
        return fmt2Int(execute(config));
    }

    /**
     * 请求资源或服务
     *
     * @param config 请求参数配置
     * @return 返回处理结果
     * @throws ThirdPartyException httpException
     */
    public static String send(HttpConfig config) throws ThirdPartyException {
        String result = fmt2String(execute(config), config.outenc());
        config.cleanThreadLocal();
        return result;
    }

    /**
     * 请求资源或服务
     *
     * @param config HttpConfig对象
     * @return HttpResponse
     * @throws ThirdPartyException httpException
     */
    private static HttpResponse execute(HttpConfig config) throws ThirdPartyException {
        //获取链接
        create(config);
        HttpResponse resp = null;
        try {
            //创建请求对象
            HttpRequestBase request = getRequest(config.url(), config.method());

            //设置header信息
            request.setHeaders(config.headers());

            //判断是否支持设置entity(仅HttpPost、HttpPut、HttpPatch支持)
            if (HttpEntityEnclosingRequestBase.class.isAssignableFrom(request.getClass())) {
                List<NameValuePair> nvps = new ArrayList<>();
                //检测url中是否存在参数，只有get请求，才自动截取url中的参数，post等其他方式，不再截取
                if (request.getClass() == HttpGet.class) {
                    //检测url中是否存在参数
                    config.url(Utils.checkHasParas(config.url(), nvps, config.inenc()));
                }

                //装填参数
                HttpEntity entity = Utils.map2HttpEntity(nvps, config.map(), config.inenc());

                //设置参数到请求对象中
                ((HttpEntityEnclosingRequestBase) request).setEntity(entity);

            }
            //执行请求操作，并拿到结果（同步阻塞）
            resp = config.context() == null
                    ? config.client().execute(request)
                    : config.client().execute(request, config.context());

            if (config.isReturnRespHeaders()) {
                //获取所有response的header信息
                config.headers(resp.getAllHeaders());
            }
            //获取结果实体
            return resp;

        } catch (IOException e) {
            log.error("Http请求失败：{}", e.getMessage());
            throw new ThirdPartyException(e.getMessage(), e.getCause());
        }
    }

    /**
     * 转化为字符串
     *
     * @param resp     响应对象
     * @param encoding 编码
     * @return 返回处理结果
     * @throws ThirdPartyException httpException
     */
    private static String fmt2String(HttpResponse resp, String encoding) throws ThirdPartyException {
        String body;
        try {
            if (resp.getEntity() != null) {
                // 按指定编码转换结果实体为String类型
                body = EntityUtils.toString(resp.getEntity(), encoding);
            } else {//有可能是head请求
                body = resp.getStatusLine().toString();
            }
            EntityUtils.consume(resp.getEntity());
        } catch (IOException e) {
            throw new ThirdPartyException(e.getMessage(), e.getCause());
        } finally {
            close(resp);
        }
        return body;
    }

    /**
     * 转化为数字
     *
     * @param resp 响应对象
     * @return 返回处理结果
     * @throws ThirdPartyException httpException
     */
    private static int fmt2Int(HttpResponse resp) throws ThirdPartyException {
        int statusCode;
        try {
            statusCode = resp.getStatusLine().getStatusCode();
            EntityUtils.consume(resp.getEntity());
        } catch (IOException e) {
            throw new ThirdPartyException(e.getMessage(), e.getCause());
        } finally {
            close(resp);
        }
        return statusCode;
    }

    /**
     * 转化为流
     *
     * @param resp 响应对象
     * @param out  输出流
     * @return OutputStream
     * @throws ThirdPartyException httpException
     */
    public static OutputStream fmt2Stream(HttpResponse resp, OutputStream out) throws ThirdPartyException {
        try {
            resp.getEntity().writeTo(out);
            EntityUtils.consume(resp.getEntity());
        } catch (IOException e) {
            throw new ThirdPartyException(e.getMessage(), e.getCause());
        } finally {
            close(resp);
        }
        return out;
    }

    /**
     * 根据请求方法名，获取request对象
     *
     * @param url    资源地址
     * @param method 请求方式
     * @return request对象
     */
    private static HttpRequestBase getRequest(String url, HttpMethods method) {
        HttpRequestBase request;
        switch (method.getCode()) {
            // HttpGet
            case 0:
                request = new HttpGet(url);
                break;
            // HttpPost
            case 1:
                request = new HttpPost(url);
                break;
            // HttpHead
            case 2:
                request = new HttpHead(url);
                break;
            // HttpPut
            case 3:
                request = new HttpPut(url);
                break;
            // HttpDelete
            case 4:
                request = new HttpDelete(url);
                break;
            // HttpTrace
            case 5:
                request = new HttpTrace(url);
                break;
            // HttpPatch
            case 6:
                request = new HttpPatch(url);
                break;
            // HttpOptions
            case 7:
                request = new HttpOptions(url);
                break;
            default:
                request = new HttpPost(url);
                break;
        }
        return request;
    }

    /**
     * 尝试关闭response
     *
     * @param resp HttpResponse对象
     */
    private static void close(HttpResponse resp) {
        try {
            if (resp == null) {
                return;
            }
            // 如果CloseableHttpResponse 是resp的父类，则支持关闭
            if (CloseableHttpResponse.class.isAssignableFrom(resp.getClass())) {
                ((CloseableHttpResponse) resp).close();
            }
        } catch (IOException e) {
            log.error("关闭Response异常：{}", e.getMessage());
            e.printStackTrace();
        }
    }

}