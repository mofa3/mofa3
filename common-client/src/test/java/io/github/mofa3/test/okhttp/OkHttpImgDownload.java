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
package io.github.mofa3.test.okhttp;

import okhttp3.logging.HttpLoggingInterceptor;
import io.github.mofa3.client.okhttp3.OkHttpRequest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * TODO
 *
 * @author baizhang
 * @version: v 0.1 OkHttpImgDownload.java, 2019-08-09 13:53 Exp $
 */
public class OkHttpImgDownload {
    public static void main(String[] args) throws IOException {
        inputStreamToFile();
//        bytesToFile();
    }

    private static void downloadImg() {
        OkHttpRequest.get("http://pic33.nipic.com/20131007/13639685_123501617185_2.jpg")
                .retry()
                .log(HttpLoggingInterceptor.Level.BODY)
                .exec()
                .toFile(new File("/Users/mc/backupSpaces/mofa3/common-client/target/download-1.jpg"));
    }

    private static void inputStreamToFile() throws IOException {
        InputStream is = OkHttpRequest.get("https://github.com").retry().log(HttpLoggingInterceptor.Level.BODY)
                .exec().toStream();
        File file = new File("/Users/mc/backupSpaces/mofa3/common-client/target/github-inputStream.html");
        BufferedInputStream in = new BufferedInputStream(is);
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        int len = -1;
        byte[] b = new byte[1024];
        while ((len = in.read(b)) != -1) {
            out.write(b, 0, len);
        }
        in.close();
        out.close();
    }


    private static void bytesToFile() throws IOException {
        byte[] bytes = OkHttpRequest.get("https://github.com").retry().log(HttpLoggingInterceptor.Level.BODY)
                .exec().toByte();
        File file = new File("/Users/mc/backupSpaces/mofa3/common-client/target/github-bytes.html");
        FileOutputStream outputFileStream = new FileOutputStream(file);
        outputFileStream.write(bytes);
    }
}