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

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import io.github.mofa3.lang.exception.ThirdPartyException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * Http SSL配置
 *
 * @author ${baizhang}
 * @version $Id: HttpBuilder.java, v 0.1 2018-04-19 下午5:49 Exp $
 */
@SuppressWarnings("unused")
public class Ssls {

    private static final SslHandler SIMPLE_VERIFIER = new SslHandler();
    private static SSLSocketFactory sslFactory;
    private static SSLConnectionSocketFactory sslConnFactory;
    private static Ssls sslUtil = new Ssls();
    private SSLContext sc;

    public static Ssls getInstance() {
        return sslUtil;
    }

    public static Ssls custom() {
        return new Ssls();
    }


    /**
     * 信任主机
     *
     * @return
     */
    public static HostnameVerifier getVerifier() {
        return SIMPLE_VERIFIER;
    }

    public synchronized SSLSocketFactory getSSLSF(SSLProtocolVersion sslpv) throws ThirdPartyException {
        if (sslFactory != null) {
            return sslFactory;
        }
        try {
            SSLContext sc = getSSLContext(sslpv);
            sc.init(null, new TrustManager[]{SIMPLE_VERIFIER}, null);
            sslFactory = sc.getSocketFactory();
        } catch (KeyManagementException e) {
            throw new ThirdPartyException("getSSLContext Exception", e);
        }
        return sslFactory;
    }

    public SSLContext getSSLContext(SSLProtocolVersion sslpv) throws ThirdPartyException {
        try {
            if (sc == null) {
                sc = SSLContext.getInstance(sslpv.getVersion());
            }
            return sc;
        } catch (NoSuchAlgorithmException e) {
            throw new ThirdPartyException("getSSLContext Exception", e);
        }
    }


    public SSLConnectionSocketFactory getSslConnSocketFactory(SSLProtocolVersion ssLpv) {
        if (sslConnFactory == null) {
            synchronized (this) {
                if (sslConnFactory == null) {
                    try {
                        sc = SSLContext.getInstance(ssLpv.getVersion());
                        sc.init(null, new TrustManager[]{SIMPLE_VERIFIER}, new java.security.SecureRandom());
                    } catch (Exception e) {
                        throw new ThirdPartyException(e.getMessage(), e.getCause());
                    }
                    sslConnFactory = new SSLConnectionSocketFactory(sc, SIMPLE_VERIFIER);
                }
            }
        }
        return sslConnFactory;

    }

    /**
     * double check locking
     *
     * @param ssLpv
     * @return
     * @throws ThirdPartyException
     */
    public SSLConnectionSocketFactory getSslConnSocketFactory(SSLProtocolVersion ssLpv, SSLContext sc) {
        if (sslConnFactory == null) {
            synchronized (this) {
                if (sslConnFactory == null) {
                    try {
                        sc.init(null, new TrustManager[]{SIMPLE_VERIFIER}, new java.security.SecureRandom());
                    } catch (KeyManagementException e) {
                        throw new ThirdPartyException(e.getMessage(), e.getCause());
                    }
                    sslConnFactory = new SSLConnectionSocketFactory(sc, new String[]{ssLpv.getVersion()},
                            null, SIMPLE_VERIFIER);
                }
            }
        }
        return sslConnFactory;
    }


    /**
     * 重写X509TrustManager类的三个方法,信任服务器证书
     */
    private static class SslHandler implements X509TrustManager, HostnameVerifier {

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

    //public Ssls customSSL(String keyStorePath, String keyStorepass) throws HttpProcessException {
    //    FileInputStream inStream = null;
    //    try {
    //        //KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
    //        //inStream = new FileInputStream(new File(keyStorePath));
    //        //trustStore.load(inStream, keyStorepass.toCharArray());
    //        //// 相信自己的CA和所有自签名的证书
    //        //sc = SslContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
    //
    //        KeyStore keyStore = KeyStore.getInstance("PKCS12");
    //        char[] password= keyStorepass.toCharArray();
    //        inStream = new FileInputStream(new File(keyStorePath));
    //        keyStore.load(inStream, password);
    //        sc= SslContexts.custom().loadKeyMaterial(keyStore, password).build();
    //
    //
    //    } catch (IOException e) {
    //        throw new HttpProcessException(e);
    //    } catch (CertificateException e) {
    //        throw new HttpProcessException(e);
    //    } catch (NoSuchAlgorithmException e) {
    //        throw new HttpProcessException(e);
    //    } catch (KeyStoreException e) {
    //        throw new HttpProcessException(e);
    //    } catch (KeyManagementException e) {
    //        throw new HttpProcessException(e);
    //    } catch (UnrecoverableKeyException e) {
    //        e.printStackTrace();
    //    } finally {
    //        try {
    //            inStream.close();
    //        } catch (IOException e) {
    //            throw new HttpProcessException(e);
    //        }
    //
    //    }
    //    return this;
    //}
    //
    //public SslContexts getSSLContext(SSLProtocolVersion sslpv) throws HttpProcessException {
    //    try {
    //        if (sc == null) {
    //            sc = SslContexts.getInstance(sslpv.getName());
    //        }
    //        return sc;
    //    } catch (NoSuchAlgorithmException e) {
    //        throw new HttpProcessException(e);
    //    }
    //}

}