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
package io.github.mofa3.lang;

import java.net.UnknownHostException;

import io.github.mofa3.lang.util.RandomCodeUtil;

/**
 * TODO
 *
 * @author lumoere
 * @version $Id: Md5Test.java, v 0.1 2018-03-26 上午10:25 Exp $
 */
public class Md5Test {
    public static void main(String[] args) throws UnknownHostException {

        //System.out.println(Md5Util.getEncryption("123ABC"));
        //
        //System.out.println(InetAddress.getLocalHost().getHostAddress());

        //String aa= new String();
        //byte[] b= aa.getBytes();

        //List<Object> list =  new ArrayList<>();
        //list.add(null);
        //
        //List<Object> ss= (List<Object>) Optional.fromNullable(list);
        //
        //System.out.println(new String((byte[]) list.get(0)));

        //System.out.println(SequenceUtil.getSequence(ModuleEnum.MEMBER, DateTimeFormatter.ofPattern("yyMMdd"),"", 1));

        System.out.println(RandomCodeUtil.generateTextCode(RandomCodeUtil.TYPE_ALL_MIXED, 6, null));

    }


}