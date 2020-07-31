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
package io.github.mofa3.lang.common.constant;

/**
 * Http常量类
 *
 * @author lumoere
 * @version $Id: HttpConstants.java, v 0.1 2017-11-16 下午4:53 Exp $
 */
@SuppressWarnings("unused")
public class HttpConstants {
    /**
     * 表单方式提交数据，数据格式为被编码为名称/值对
     * 例如：id=123&name=java
     * 是标准的编码格式
     */
    public static final String APP_FORM_URLENCODED = "application/x-www-form-urlencoded";

    /**
     * 数据被编码成一条消息，页面上一个控件对应消息中的一部分
     * 通常作为上传文件使用
     */
    public static final String APP_FORM_DATA = "multipart/form-data";

    /**
     * 数据被编码成json格式提交
     */
    public static final String APP_FORM_JSON = "application/json";

    /**
     * 数据以纯文本方式编码，不包含任何控件或格式字符
     */
    public static final String TEXT_PLAIN = "text/plain";

    /**
     * 数据以html方式编码，能正确的被浏览器识别
     */
    public static final String TEXT_HTML = "text/html";

    /**
     * 数据以xml方式编码
     */
    public static final String TEXT_XML = "text/xml";

    /**
     * 数据以json方式编码
     */
    public static final String TEXT_JSON = "text/json";

    /**
     * 链接关闭
     */
    public static final String CONN_CLOSE = "close";

    /**
     * 持久连接、连接重用，http1.0默认关闭
     */
    public static final String KEEP_ALIVE = "Keep-Alive";

    /**
     * 100-continue 协议
     * 使用HTTP/1.1协议的curl，发送一个请求，在post数据量超过1K，
     * 100 Continue请求一次没发送完，需要继续发送，会做如下两个步骤：
     * 1、发送一个请求, 包含一个Expect:100-continue, 询问Server使用愿意接受数据
     * 2、接收到Server返回的100-continue应答以后, 才把数据POST给Server
     * <p>
     * 对HTTP/1.1客户端的要求：
     * 1、如果客户端在发送请求体之前，想等待服务器返回100状态码，那么客户端必须要发送一个Expect请求头信息，
     * 即：”100-continue”请求头信息；
     * 2、如果一个客户端不打算发送请求体的时候，一定不要使用“100-continue”发送Expect的请求头信息；
     */
    public static final String EXPECT_CONTINUE = "100-continue";


    /**
     * X-Forwarded-For
     */
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";

    /**
     * Content-Length
     */
    public static final String CONTENT_LENGTH = "Content-Length";

    /**
     * Content-Type
     */
    public static final String CONTENT_TYPE = "Content-Type";

    /**
     * Connection
     */
    public static final String CONNECTION = "Connection";

    /**
     * request id
     */
    public static final String REQUEST_ID = "x_request_id";
}