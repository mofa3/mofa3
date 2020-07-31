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

package io.github.mofa3.lang.encrydecryption;

import com.sun.org.apache.xml.internal.security.algorithms.JCEMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * TODO
 *
 * @author github.com/lujun920
 */
@Slf4j
public class AesTest {


    /**
     *
     */
     private static final String  AES_CBC_PKCS7_128= "AES/CBC/PKCS5Padding";
     static {
         JCEMapper.register(AES_CBC_PKCS7_128,
                 new JCEMapper.Algorithm("AES", "AES_CBC_PKCS7_128", "BlockEncryption", 128));

     }

     public static void main(String[] args) {
        String data ="{\"settlement_code\":[\"JS19BUB14F5D8D4C\"],\"random_code\":[\"19BUB14F5D8D4C\",\"19BUAD0E89D780\"]}";
        String pwd= "0688f7a191da4fbab177fd1c8ef19901";
        String iv= "ff465fdecc764337";

        System.out.println(encryptData(pwd, data, iv));

     }

    public static String encryptData(String key, String data, String iv) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
            // 创建密码器
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS7_128);
            IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
            // 初始化
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
            return Base64Utils.encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            log.error("AES加密失败：", e);
            e.printStackTrace();
        }
        return null;
    }

}
