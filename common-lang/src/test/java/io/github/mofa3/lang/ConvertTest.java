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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;

/**
 * TODO
 *
 * @author lumoere
 * @version $Id: ConvertTest.java, v 0.1 2018-07-17 下午2:35 Exp $
 */
public class ConvertTest {
    public static void main(String[] args) {
        Person person = new Person();
        person.setName("name");
        person.setAge(13);
        person.setIdCard("521509xxxxxxxxxxxx");

        person.setListStr(Arrays.asList("1", "2", "3", "a", "b", "c"));
        Map<String, String> map = new HashMap<>();
        map.put("abc", "111");
        map.put("def", "222");
        map.put("ghi", "333");
        person.setMapStr(map);

        Address address = new Address();
        address.setCity("杭州市");
        address.setArea("余杭区");
        address.setAddr("文一西路");

        Inner inner = new Inner();
        inner.setIn("in");
        inner.setOut("out");
        address.setInner(inner);
        person.setAddress(address);


        User user = new User();
        //long start1= DateUtil.timeStampMilli();
        //for (int i = 0; i < 100; i++) {
        //    CommonConverter.convert(person, user);
        //}
        //System.out.println("AbstractBeanCopier: "+(DateUtil.timeStampMilli()- start1));
        //
        //long start2 = DateUtil.timeStampMilli();
        //for (int i = 0; i < 100; i++) {
        BeanUtils.copyProperties(person, user);
        //}
        //System.out.println("spring bean: "+(DateUtil.timeStampMilli() - start2));


        //System.out.println(JSON.toJSONString(user));
        //
        //person.setName("name1111");
        //
        //System.out.println(JSON.toJSONString(user));
        //System.out.println(JSON.toJSONString(person));
    }
}

