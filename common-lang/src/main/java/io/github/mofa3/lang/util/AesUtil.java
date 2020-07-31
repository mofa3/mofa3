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
package io.github.mofa3.lang.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;
import io.github.mofa3.lang.common.constant.MofaConstants;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES 加解密工具类
 *
 * @author ${baizhang}
 * @version $Id: AesUtil.java, v 0.1 2018-08-16 下午7:54 Exp $
 */
@Slf4j
@SuppressWarnings("unused")
public class AesUtil {

    /**
     * AES 加密
     * 默认实现：
     * PKCS7Padding填充
     * MD5 秘钥key
     *
     * @param key  秘钥key
     * @param data 加密字符串
     * @return 加密后base64字符串
     */
    public static String encryptData(String key, String data) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(Md5Util.getEncryption(key).toLowerCase()
                .getBytes(), MofaConstants.AES);
        try {
            // 创建密码器
            Cipher cipher = Cipher.getInstance(MofaConstants.ALGORITHM_MODE_PADDING, "BC");
            // 初始化
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            return encryptData(secretKeySpec, cipher, data);
        } catch (Exception e) {
            log.error("AES加密失败：", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES 加密
     *
     * @param key    秘钥key
     * @param cipher 密码器
     * @param data   加密字符串
     * @return 加密后base64字符串
     */
    public static String encryptData(SecretKeySpec key, Cipher cipher, String data) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return Base64Utils.encodeToString(cipher.doFinal(data.getBytes()));
        } catch (Exception e) {
            log.error("AES加密失败：", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES 解密
     * 默认实现：
     * PKCS7Padding填充
     * MD5 秘钥key
     *
     * @param key        秘钥key
     * @param base64Data base64字符串
     * @return 解密后字符串
     */
    public static String decryptData(String key, String base64Data) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(Md5Util.getEncryption(key).toLowerCase()
                .getBytes(), MofaConstants.AES);
        try {
            Cipher cipher = Cipher.getInstance(MofaConstants.ALGORITHM_MODE_PADDING, "BC");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return decryptData(secretKeySpec, cipher, base64Data);
        } catch (Exception e) {
            log.error("AES解密失败：", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES 解密
     *
     * @param key        秘钥key
     * @param cipher     密码器
     * @param base64Data 解密字符串
     * @return 解密后字符串
     */
    public static String decryptData(SecretKeySpec key, Cipher cipher, String base64Data) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64Utils.decodeFromString(base64Data)));
        } catch (Exception e) {
            log.error("AES解密失败：", e);
            e.printStackTrace();
        }
        return null;
    }


}