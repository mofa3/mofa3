## OkHttp client 使用
```java
OkHttpHeader headers = OkHttpHeader.newBuilder().contentType(HttpConstants.APP_FORM_JSON)
                .userAgent("UA")
                .accept("*/*")
                .acceptCharset("UTF-8")
                .acceptLanguage("zh_CN")
                .cacheControl("no-cache")
                .connection("keep-alive");

        // 同步调用
        String result= OkHttpRequest.get("https://github.com/")
                .log()
                .header(headers)
                .exec()
                .toStr();
        
        // 异步调用
        OkHttpRequest.get("https://github.com/")
                        .log(HttpLoggingInterceptor.Level.NONE)
                        .header(headers)
                        .connectTimeout(1000)
                        .readTimeout(1000)
                        .writeTimeout(1000)
                        .retry()
                        .async().response(response -> {
                            // 响应成功，通常用于处理http状态信息等
                        }).successful(response -> {
                            // 调用成功 TODO
                        }).failed((request, e) -> {
                            // 调用失败 TODO
                        }).exec();
```