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
package io.github.mofa3.client.common;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * TODO
 *
 * @author baizhang
 * @version: v 0.1 SslContexts.java, 2019-04-19 15:42 Exp $
 */
public class SslContexts {

    /**
     * 解析SSL证书
     *
     * @param serverCerts      服务端证书
     * @param x509TrustManager 证书管理器
     * @param clientCert       客户端证书
     * @param password         客户端证书密码
     * @return
     */
    public static SslConfig analysis(InputStream[] serverCerts, X509TrustManager x509TrustManager,
                                     InputStream clientCert, String password) {
        SslConfig sslConfig = new SslConfig();
        try {
            KeyManager[] keyManagers = pkcsKeyManagers(clientCert, password);
            X509TrustManager trustManager;
            if (null != x509TrustManager) {
                trustManager = x509TrustManager;
            } else {
                trustManager = x509TrustManager(serverCerts);
                if (null == trustManager) {
                    trustManager = unSafeTrustManager;
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");
            // 用上面得到的trustManagers初始化SSLContext，这样sslContext就会信任keyStore中的证书
            sslContext.init(keyManagers, new TrustManager[]{trustManager}, new SecureRandom());

            sslConfig.setSslSocketFactory(sslContext.getSocketFactory());
            sslConfig.setX509TrustManager(trustManager);
            return sslConfig;
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException
                | IOException | UnrecoverableKeyException | KeyManagementException e) {
            throw new AssertionError(e);
        }
    }


    /**
     * 不对证书进行验证
     */
    private static X509TrustManager unSafeTrustManager = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    };

    /**
     * 根据服务端证书文件生成 X509TrustManager
     *
     * @param serverCerts
     * @return
     * @throws Exception
     */
    private static X509TrustManager x509TrustManager(InputStream... serverCerts)
            throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException {
        if (null == serverCerts || serverCerts.length == 0) {
            return null;
        }

        //创建证书工厂
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        //创建一个默认类型的KeyStore,存储我们信任的证书
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null);

        //开始处理证书
        for (int i = 0, length = serverCerts.length; i < length; i++) {
            //将证书对象作为可信证书放入到keyStore中
            keyStore.setCertificateEntry(String.valueOf(i + 1), factory.generateCertificate(serverCerts[i]));
            if (null != serverCerts[i]) {
                serverCerts[i].close();
            }
        }

        // Use it to build an X509 trust manager.
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        // 用之前的keyStore实例初始化TrustManagerFactory，这样tmf就会信任keyStore中的证书
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:" + trustManagers);
        }
        return (X509TrustManager) trustManagers[0];
    }

    /**
     * 客户端SSL证书 PKCS证书解析
     *
     * @param clientCert 客户端证书
     * @param password   证书密码
     * @return KeyManager
     * @throws KeyStoreException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws IOException
     * @throws UnrecoverableKeyException
     */
    private static KeyManager[] pkcsKeyManagers(InputStream clientCert, String password)
            throws KeyStoreException, CertificateException, NoSuchAlgorithmException,
            IOException, UnrecoverableKeyException {
        if (null == clientCert || StringUtils.isBlank(password)) {
            return null;
        }
        KeyStore clientKS = KeyStore.getInstance("PKCS12");
        clientKS.load(clientCert, password.toCharArray());
        KeyManagerFactory factory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        factory.init(clientKS, password.toCharArray());
        return factory.getKeyManagers();
    }


    /**
     * SSL config
     */
    public static final class SslConfig {
        @Setter
        @Getter
        private SSLSocketFactory sslSocketFactory;

        @Setter
        @Getter
        private X509TrustManager x509TrustManager;
    }
}