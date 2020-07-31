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

import okhttp3.MediaType;
import io.github.mofa3.lang.common.constant.HttpConstants;

/**
 * TODO
 *
 * @author baizhang
 * @version: v 0.1 MediaTypeTest.java, 2019-07-31 17:29 Exp $
 */
public class MediaTypeTest {
    public static void main(String[] args) {
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        MediaType JSON = MediaType.parse(HttpConstants.APP_FORM_JSON);

        System.out.println(JSON.toString());
    }
}