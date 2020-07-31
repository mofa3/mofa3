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
package io.github.mofa3.lang.example;

import java.util.concurrent.TimeUnit;

/**
 * TODO
 *
 * @author baizhang
 * @version: VolatileInteger.java, v 1.0 2020-03-24 5:03 下午 Exp $
 */
public class VolatileInteger {
    private static boolean flag = false;
    private static volatile int i = 0;
    public static void main(String[] args) {
        new Thread(() -> {
//            System.out.println("inner thread");
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                flag = true;
                System.out.println("flag 被修改成 true");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        while (!flag) {
            i++;
//            System.out.println("flag："+ flag);
        }
        System.out.println("程序结束,i=" + i);

    }
}