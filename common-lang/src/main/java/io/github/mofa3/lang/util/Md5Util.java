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

import io.github.mofa3.lang.common.constant.MofaConstants;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * MD5工具类
 *
 * @author ${baizhang}
 * @version $Id: MD5Encryption.java, v 0.1 2017-11-29 下午2:27 Exp $
 */
@SuppressWarnings("unused")
public class Md5Util {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'};

    private Md5Util() {

    }

    public static String getEncryption(String originString) {
        try {
            byte[] btInput = originString.getBytes(StandardCharsets.UTF_8);
            MessageDigest mdInst = MessageDigest.getInstance(MofaConstants.MD5);
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = HEX_DIGITS[byte0 >>> 4 & 0xf];
                str[k++] = HEX_DIGITS[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}