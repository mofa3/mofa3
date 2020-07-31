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
package io.github.mofa3.test.okhttp;

import io.github.mofa3.client.okhttp3.OkHttpHeader;
import io.github.mofa3.client.okhttp3.OkHttpRequest;
import io.github.mofa3.lang.common.constant.HttpConstants;

/**
 * TODO
 *
 * @author baizhang
 * @version: v 0.1 MofaHttpTest.java, 2019-07-31 17:49 Exp $
 */
public class MofaHttpTest {
    public static void main(String[] args) {
        OkHttpHeader headers = OkHttpHeader.newBuilder().contentType(HttpConstants.APP_FORM_URLENCODED)
                .userAgent("UA")
                .accept("*/*")
                .acceptCharset("UTF-8")
                .acceptLanguage("zh_CN")
                .cacheControl("no-cache")
                .connection("keep-alive")
//                .other("Content-Type", "application/x-www-form-urlencoded")
                .other("request-id", "request-uuid")
                .other("dealer-id", "dealer-1233333");

        // 同步
//        String result= OkHttpRequest.get("https://www.baidu.com")
//                .log()
//                .header(headers)
//                .exec()
//                .toStr();
//        System.out.println(result);


//        for (int i = 0; i < 10; i++) {
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
        OkHttpRequest.post("https://api-jiesuan.yunzhanghu.com/authentication/verify-id")
                .header(headers)
                .connectTimeout(1000)
                .readTimeout(1000)
                .writeTimeout(1000)
                .log()
                .exec()
                .toStr();
//                .async().response(response -> {
//                System.out.println(Thread.currentThread().getName()+"#"+Thread.currentThread().getId()+"--->response: " + response.httpCode());
//        }).successful(response -> {
//                System.out.println("successful: " + response.toStr());
//        }).failed((request, e) -> {
//            System.out.println("failed: " + e.getMessage());
//        }).exec();
//
//                    } catch (ThirdPartyException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//
//
//        }

//        OkHttpRequest.get("https://www.baidu.com")
//                .retry()
//                .log(HttpLoggingInterceptor.Level.BODY)
//                .header(headers)
//                .exec()
//                .toFile(new File("/Users/mc/backupSpaces/mofa3/common-client/target/123.html"));
//        System.out.println(result);

    }

}