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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * SSL协议版本支持枚举类 (SSLv3, TLSv1, TLSv1.1, TLSv1.2)
 *
 * @author baizhang
 * @version: SSLProtocolVersion.java, v 0.1 2019-04-05 18:52 Exp $
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SSLProtocolVersion {
    /**
     * SSL
     */
    SSL("SSL"),

    /**
     * TLSv1"
     */
    TLSv1("TLSv1"),

    /**
     * TLSv1.1
     */
    TLSv1_1("TLSv1.1"),

    /**
     * TLSv1.2
     */
    TLSv1_2("TLSv1.2"),

    /**
     * SSLv3
     */
    SSLv3("SSLv3"),
    ;

    /**
     * http ssl 协议版本
     */
    @Getter
    private String version;

    public static SSLProtocolVersion find(String version) {
        for (SSLProtocolVersion pv : SSLProtocolVersion.values()) {
            if (pv.getVersion().toUpperCase().equals(version.toUpperCase())) {
                return pv;
            }
        }
        throw new RuntimeException("未支持ssl版本：" + version);
    }
}