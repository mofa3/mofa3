package io.github.mofa3.test.okhttp;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import io.github.mofa3.client.okhttp3.OkHttpClientSingle;
import io.github.mofa3.client.okhttp3.OkHttpHeader;
import io.github.mofa3.client.okhttp3.OkHttpRequest;
import io.github.mofa3.lang.exception.ThirdPartyException;
import io.github.mofa3.lang.common.constant.HttpConstants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * TODO
 *
 * @author baizhang
 * @version: OkHttpTest.java, v 0.1 2019-04-10 19:40 Exp $
 */
public class OkHttpTest {
    //json传输方式
    private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    //获取okHttpClient对象
    private OkHttpClient client = OkHttpClientSingle.INSTANCE.getInstance();

//    private OkHttpClient client = OkHttpClientPoolSingle.getInstance();

    /**
     * 异步发起请求
     */
    public OkHttpClient pool(String url) {


        String threadName = Thread.currentThread().getName();
        long threadId = Thread.currentThread().getId();
        //创建请求
        Request request = new Request.Builder()
                .url(url + "/" + threadName + "||" + threadId)
                .build();

        System.out.println("before: " + threadName + "||" + threadId + "===" + client.connectTimeoutMillis());

        client = client.newBuilder().connectTimeout(Thread.currentThread().getId(), TimeUnit.SECONDS).build();
//        System.out.println("after: " + client.connectTimeoutMillis());


        //异步请求
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                System.out.println(response.body().string());
//                System.out.println(threadName + "#" + threadId);
                System.out.println(Thread.currentThread().getName() + "#" + Thread.currentThread().getId() + "$$" + response.body().string());
            }
        });
        return client;
    }

    public static void main(String[] args) {
        OkHttpTest test = new OkHttpTest();
//        test.mofaOkhttp();

    }

    OkHttpHeader headers = OkHttpHeader.newBuilder().contentType(HttpConstants.APP_FORM_JSON)
            .userAgent("UA")
            .accept("*/*")
            .acceptCharset("UTF-8")
            .acceptLanguage("zh_CN")
            .cacheControl("no-cache")
            .connection("keep-alive");

    public void mofaOkhttp() {
        for (int i = 0; i < 20; i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String threadName = Thread.currentThread().getName();
                        long threadId = Thread.currentThread().getId();

                        OkHttpRequest.get("http://127.0.0.1:8200/adc" + "/" + threadName + "||" + threadId)
                                .log(HttpLoggingInterceptor.Level.NONE)
                                .header(headers)
                                .connectTimeout(1000)
//                .readTimeout(1000)
//                .writeTimeout(1000)
                                .retry()
                                .async().response(response -> {
                        }).successful(response -> {
                            System.out.println(Thread.currentThread().getName() + "#" + Thread.currentThread().getId() + "--->response: " + response.toStr());

                        }).failed((request, e) -> {
                            System.out.println("failed: " + e.getMessage());
                        }).exec();

                    } catch (ThirdPartyException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public void okHttpTest() {
        for (int i = 0; i < 50; i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OkHttpTest test = new OkHttpTest();
                        OkHttpClient client = test.pool("http://127.0.0.1:8200/adc");
//                        test.pool("https://www.baidu.com");
                        System.out.println("after: " + Thread.currentThread().getName() + "||" + Thread.currentThread().getId() + "===" + client.connectTimeoutMillis());
                    } catch (ThirdPartyException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}