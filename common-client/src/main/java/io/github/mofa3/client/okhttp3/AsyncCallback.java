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

import lombok.AllArgsConstructor;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;

/**
 * OkHttp 异步处理回调
 *
 * @author baizhang
 * @version: v 0.1 AsyncCallback.java, 2019-07-31 17:02 Exp $
 */
@AllArgsConstructor
public class AsyncCallback implements Callback {

    private final AsyncCallable asyncCallable;

    @Override
    public void onFailure(final Call call, final IOException e) {
        asyncCallable.onFailure(call.request(), e);
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        OkHttpResponse httpResponse = new OkHttpResponse(response);
        asyncCallable.onResponse(httpResponse);
        if (httpResponse.success()) {
            asyncCallable.onSuccess(httpResponse);
        }
    }
}