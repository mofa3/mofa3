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
package io.github.mofa3.lang.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mofa3.lang.util.JsonUtil;

public class JacksonJsonTest {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        try {

            /** json字符串转换为java对象 */

            // json中的对象个数比java对象的属性个数少
            //JSONObject json1 = new JSONObject();
            //json1.put("name", "anne");
            //json1.put("age", 15);
            //String d1 = json1.toString();
            //Student s1 = mapper.readValue(d1, Student.class);
            //System.out.println(s1.toString());
            //
            //// json中出现java对象中没有的属性
            //JSONObject json2 = new JSONObject();
            //json2.put("name", "anne");
            //json2.put("age", 15);
            //json2.put("sex", "boy");
            //String d2 = json1.toString();
            //Student s2 = mapper.readValue(d2, Student.class);
            //System.out.println(s2.toString());

            /** java对象转换为json字符串 */
            Student s3 = new Student();
            s3.setAge(12);
            s3.setHobby("sport");
            s3.setName("anne");
            String d3 = JsonUtil.beanToJson(s3);
            System.out.println(d3);


            String json = "{\"name\":\"anne\",\"age\":12,\"hobby\":\"sport\"}";
            Student student = JsonUtil.jsonToBean(json, Student.class);
            System.out.println(student.toString());

            Map<String, Object> objectMap = JsonUtil.jsonToBean(json, HashMap.class);
            System.out.println(objectMap);


            System.out.println(JsonUtil.beanToMap(s3));

            System.out.println(Math.round(12 / 0.75f + 1));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static String beanToJson(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <T> T jsonToBean(String json, Class<T> claxx) {
        ObjectMapper mapper = new ObjectMapper();
        T t = null;
        try {
            t = mapper.readValue(json, claxx);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }


}

@JsonIgnoreProperties(ignoreUnknown = true)
class Student {
    private String name;
    private int age;
    private String hobby;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String toString() {
        return "name: " + name + ", age: " + age + ", hobby: " + hobby;
    }
}