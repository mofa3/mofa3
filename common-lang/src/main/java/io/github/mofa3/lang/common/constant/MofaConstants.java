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
package io.github.mofa3.lang.common.constant;

/**
 * 常量类
 *
 * @author lumoere
 * @version $Id: MofaConstants.java, v 0.1 2018年04月09日 下午2:25 Exp $
 */
@SuppressWarnings("unused")
public class MofaConstants {

    /**
     * 秘钥算法
     */
    public static final String AES = "AES";

    /**
     * 加解密算法/工作模式/填充方式
     */
    public static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS7Padding";

    /**
     * 加密方式 - md5
     */
    public static final String MD5 = "MD5";

    /**
     * 签名方式 - rsa
     */
    public static final String SIGN_RSA = "RSA";

    /**
     * 签名方式 - rsa2
     */
    public static final String SIGN_RSA2 = "RSA2";

    /**
     * 签名方式 - sha1
     */
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    /**
     * 签名方式 - sha256
     */
    public static final String SIGN_SHA256RSA = "SHA256WithRSA";

    /**
     * 编码方式 utf-8
     */
    public static final String UTF_8 = "UTF-8";

    /**
     * 编码方式 gbk
     */
    public static final String GBK = "GBK";

    /**
     * http
     */
    public static final String HTTP = "http";

    /**
     * https
     */
    public static final String HTTPS = "https";

    /**
     * 负无穷
     */
    public static final String NEGATIVE_INFINITY = "-∞";

    /**
     * 正无穷
     */
    public static final String POSITIVE_INFINITY = "+∞";

    /**
     * 左开区间 "("
     */
    public static final String RANGE_NOT_INCLUDE_LEFT = "(";

    /**
     * 左闭区间 "["
     */
    public static final String RANGE_INCLUDE_LEFT = "[";

    /**
     * 左闭区间 "{"
     */
    public static final String RANGE_BRACE_LEFT = "{";

    /**
     * 右开区间 ")"
     */
    public static final String RANGE_NOT_INCLUDE_RIGHT = ")";

    /**
     * 右闭区间 "]"
     */
    public static final String RANGE_INCLUDE_RIGHT = "]";

    /**
     * 右闭区间 "}"
     */
    public static final String RANGE_BRACE_RIGHT = "}";

    /**
     * 转译前缀
     */
    public static final String TRANS = "\\";

    /**
     * 斜线
     */
    public static final String SLASH = "/";

    /**
     * 未知 "NaN"
     */
    public static final String NAN = "NaN";

    /**
     * 下划线
     */
    public static final String UNDERLINE = "_";

    /**
     * 加号
     */
    public static final String PLUS = "+";

    /**
     * 减号（中划线）
     */
    public static final String SUBTRACT = "-";

    /**
     * 乘号
     */
    public static final String STAR = "*";

    /**
     * 句号
     */
    public static final String STOP = ".";

    /**
     * 井号
     */
    public static final String SHARP = "#";

    /**
     * 逗号
     */
    public static final String COMMA = ",";

    /**
     * 冒号
     */
    public static final String COLON = ":";

    /**
     * 问号
     */
    public static final String UNSOLVED = "?";

    /**
     * and
     */
    public static final String AND = "&";

    /**
     * 等号
     */
    public static final String EQUAL = "=";

    /**
     * 百分之 "%"
     */
    public static final String PERCENT = "%";

    /**
     * 空格符号
     */
    public static final String BLANK = " ";

    /**
     * 单引号
     */
    public static final String SINGLE_QUOTE = "'";

    /**
     * 双引号
     */
    public static final String DOUBLE_QUOTE = "\"";

    /**
     * 腭化符号 "~"
     */
    public static final String TILDE = "~";

    /**
     * 反句号 "`"
     */
    public static final String BACK_QUOTE = "`";


}
