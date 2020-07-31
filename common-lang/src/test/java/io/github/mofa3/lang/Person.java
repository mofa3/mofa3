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

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class Person {
    private String name;
    private int age;
    private String idCard;

    private List<String> listStr;
    private Map<String, String> mapStr;
    private Address address;
}