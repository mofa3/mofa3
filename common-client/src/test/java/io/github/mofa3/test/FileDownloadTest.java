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

import io.github.mofa3.client.common.HttpConfig;
import io.github.mofa3.client.http.HttpClientUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * TODO
 *
 * @author baizhang
 * @version: FileDownloadTest.java, v 0.1 2019-04-05 19:45 Exp $
 */
public class FileDownloadTest {
    public static void main(String[] args) throws FileNotFoundException {
        String imgUrl = "https://b-ssl.duitang.com/uploads/item/201505/06/20150506202306_WYEi5.jpeg";
        File file = new File("/Users/Shared/home/admin/excel/abc.jpeg");
        HttpClientUtil.down(HttpConfig.custom().url(imgUrl).out(new FileOutputStream(file)));
        if (file.exists()) {
            System.out.println("图片下载成功了！存放在：" + file.getPath());
        }


        String mp3Url = "http://win.web.rh01.sycdn.kuwo.cn/resource/n1/24/6/707126989.mp3";
        File file2 = new File("/Users/Shared/home/admin/excel/ddd.mp3");
        HttpClientUtil.down(HttpConfig.custom().url(mp3Url).out(new FileOutputStream(file2)));
        if (file.exists()) {
            System.out.println("MP3下载成功了！存放在：" + file.getPath());
        }
    }
}