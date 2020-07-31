package io.github.mofa3.test;


import org.apache.http.Header;
import io.github.mofa3.client.builder.HttpBuilder;
import io.github.mofa3.client.common.HttpConfig;
import io.github.mofa3.client.common.HttpHeader;
import io.github.mofa3.client.http.HttpClientPoolSingle;
import io.github.mofa3.client.http.HttpClientUtil;
import io.github.mofa3.lang.exception.ThirdPartyException;
import io.github.mofa3.lang.common.constant.MofaConstants;
import io.github.mofa3.lang.common.constant.HttpConstants;

/**
 * @author arron
 * @version 1.0
 * @date 2015年11月1日 下午2:23:18
 */
public class HttpClientTest {
    private static final Header[] HEADERS = HttpHeader.custom().contentType(HttpConstants.APP_FORM_URLENCODED).acceptCharset(MofaConstants.UTF_8).build();

    public static void testOne() {

        String url = "http://tool.oschina.net/";

        HttpBuilder httpBuilder = HttpClientPoolSingle.getInstance();
        HttpConfig cfg = HttpConfig.custom().headers(HEADERS).client(httpBuilder.build()).url(url);
        String respStr = send(cfg);

        System.out.println("请求结果内容长度：" + respStr);

    }

    private static String send(HttpConfig config) {
        String respStr = "";
        try {
            respStr = HttpClientUtil.post(config);
        } catch (ThirdPartyException e) {
            e.printStackTrace();
        }
        return respStr;
    }


    public static void main(String[] args) {

//        testOne();
        HttpBuilder httpBuilder = HttpClientPoolSingle.getInstance();
    }
}