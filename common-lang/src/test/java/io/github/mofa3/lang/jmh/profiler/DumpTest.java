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

package io.github.mofa3.lang.jmh.profiler;

import io.github.mofa3.lang.util.Profiler;

/**
 * TODO
 *
 * @author github.com/lujun920
 */
public class DumpTest {

    public static void main(String[] args) {
        Profiler.start("dump test");
        Profiler.enter("start in");
        int a= 0;
        Profiler.release();
        Profiler.enter("string");
        Profiler.enter("abc");
        String abc= "abc";
        Profiler.release();
        Profiler.release();
        System.out.println(Profiler.dump());
//        Profiler.reset();
    }
}
