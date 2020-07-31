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
package io.github.mofa3.test;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import io.github.mofa3.client.common.SSLProtocolVersion;
import io.github.mofa3.lang.exception.ThirdPartyException;
import io.github.mofa3.lang.util.Profiler;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.security.cert.CertificateException;

/**
 * TODO
 *
 * @author ${baizhang}
 * @version $Id: SSLTest.java, v 0.1 2018-04-09 下午8:16 Exp $
 */
public class SSLTest {
    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            get();
//        }

        ssl();

//        get();
    }


    public static void ssl() {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "\n"
                + "<xml>\n"
                + "   <nonce_str>ab5dd9aad28646c5a7b0ff6fc86ef968</nonce_str>\n"
                + "   <refund_desc>退款申请</refund_desc>\n"
                + "   <out_trade_no>3001201804050000000000000512</out_trade_no>\n"
                + "   <out_refund_no>30012018032700000000000000026</out_refund_no>\n"
                + "   <appid>wxf0e9f7959b59546b</appid>\n"
                + "   <refund_fee>1</refund_fee>\n"
                + "   <total_fee>1</total_fee>\n"
                + "   <sign>7EF3B88D3E4D85FE3D23126F1B9E1BA7</sign>\n"
                + "   <mch_id>1500693091</mch_id>\n"
                + "   <notify_url></notify_url>\n"
                + "</xml>\n";
        String certPath = "/Users/Shared/home/admin/cert/wechat_1500693091.p12";
        String url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
        String password = "1500693091";

        try {
            //HttpBuilder instance = HttpBuilder.custom()
            //        .pool(100, 10)
            //        .timeout(15000)
            //        .ssLpv(SSLProtocolVersion.TLSv1)
            //        .ssl(certPath, password);
            //
            //HttpConfig cfg = HttpConfig.custom().client(instance.build()).url(url).json(xml);
            //String result = HttpClientUtil.post(cfg);
            //System.out.println(result);

            String res = postWithKey(url, xml, certPath, password);
            System.out.println(res);
        } catch (ThirdPartyException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String postWithKey(String url, String xml, String keyStorePath, String mchId) throws Exception {
        Profiler.enter("往微信发起请求（带证书）!!");
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        StringBuilder res = new StringBuilder();
        FileInputStream inStream = new FileInputStream(new File(keyStorePath));
        try {
            keyStore.load(inStream, mchId.toCharArray());
        } finally {
            inStream.close();
        }

        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, mchId.toCharArray())
                .build();
        SSLConnectionSocketFactory ssLsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{SSLProtocolVersion.TLSv1.getVersion()},
                null,
                new SSLHandler());
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(ssLsf)
                .build();
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Connection", "keep-alive");
            httpPost.addHeader("Accept", "*/*");
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.addHeader("Host", "api.mch.weixin.qq.com");
            httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpPost.addHeader("Cache-Control", "max-age=0");

            StringEntity entity2 = new StringEntity(xml, Consts.UTF_8);
            httpPost.setEntity(entity2);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                    String text = "";
                    res.append(text);
                    while ((text = bufferedReader.readLine()) != null) {
                        res.append(text);
                    }
                }
                EntityUtils.consume(entity);
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
        return res.toString();
    }

    private static class SSLHandler implements X509TrustManager, HostnameVerifier {

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
                                       String authType) throws CertificateException {
        }

        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
                                       String authType) throws CertificateException {
        }

        @Override
        public boolean verify(String paramString, SSLSession paramSSLSession) {
            return true;
        }
    }

}