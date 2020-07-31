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

import okhttp3.Call;
import okhttp3.Request;

import java.io.IOException;
import java.util.Deque;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 异步请求结果通知
 *
 * @author baizhang
 * @version: v 0.1 AsyncCallable.java, 2019-07-31 16:49 Exp $
 */
public class AsyncCallable {
    private final Call call;

    /**
     * 成功默认回调函数接口
     * Consumer 只接受一个参数
     */
    private final static Consumer<OkHttpResponse> DEFAULT_CONSUMER = (r) -> {
    };

    /**
     * 失败默认回调函数接口
     * 两个参数使用{@link BiConsumer}
     */
    private BiConsumer<Request, IOException> failedConsumer;
    private final static BiConsumer<Request, IOException> DEFAULT_FAIL_CONSUMER = (r, e) -> {
    };

    /**
     * 返回结果成功函数接口
     */
    private Consumer<OkHttpResponse> successConsumer;

    /**
     * 响应成功函数接口
     * 与successConsumer 的区别是：一个是服务调用并成功返回值，一个是请求服务器http成功
     */
    private Consumer<OkHttpResponse> responseConsumer;

    AsyncCallable(Call callback) {
        this.call = callback;
        this.successConsumer = DEFAULT_CONSUMER;
        this.responseConsumer = DEFAULT_CONSUMER;
        this.failedConsumer = DEFAULT_FAIL_CONSUMER;
    }

    /**
     * 请求、响应返回结果成功
     *
     * @param consumer 回调执行方法
     * @return AsyncCallable
     */
    public AsyncCallable successful(Consumer<OkHttpResponse> consumer) {
        this.successConsumer = consumer;
        return this;
    }

    /**
     * 请求、响应成功
     *
     * @param consumer 回调执行方法
     * @return AsyncCallable
     */
    public AsyncCallable response(Consumer<OkHttpResponse> consumer) {
        this.responseConsumer = consumer;
        return this;
    }

    /**
     * 请求失败
     *
     * @param consumer 回调执行方法
     * @return AsyncCallable
     */
    public AsyncCallable failed(BiConsumer<Request, IOException> consumer) {
        this.failedConsumer = consumer;
        return this;
    }

    /**
     * 执行请求
     * {@link Deque} 队列实现
     */
    public void exec() {
        call.enqueue(new AsyncCallback(this));
    }

    void onFailure(Request request, IOException e) {
        failedConsumer.accept(request, e);
    }

    void onResponse(OkHttpResponse response) {
        responseConsumer.accept(response);
    }

    void onSuccess(OkHttpResponse response) {
        successConsumer.accept(response);
    }
}