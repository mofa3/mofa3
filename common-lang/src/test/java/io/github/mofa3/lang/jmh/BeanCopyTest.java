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
package io.github.mofa3.lang.jmh;

import io.github.mofa3.lang.util.JsonUtil;

import java.util.Date;

/**
 * TODO
 *
 * @author baizhang
 * @version: v 0.1 BeanCopyTest.java, 2019-07-29 17:01 Exp $
 */
public class BeanCopyTest {


    public static void main(String[] args) {
//        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

//        CmsUser user = new CmsUser();
//        user.setUserName("baizhang");
//        user.setUserId(10000);
//        user.setCreateTime(new Date());
//        user.setPassword("123456");


//        mapperFactory.classMap(CmsUser.class, CmsUser1.class).exclude("abc").byDefault().register();
//
//
//        BoundMapperFacade<CmsUser, CmsUser1>
//                boundMapper = mapperFactory.getMapperFacade(CmsUser.class, CmsUser1.class);
//
////        MapperFacade mapper = mapperFactory.getMapperFacade();
//        CmsUser1 cmsUser1 = boundMapper.map(user);

//        CmsUser1 cmsUser1 = new CmsUser1();
//        BeanConvert.convert(user, cmsUser1);
//        System.out.println(JsonUtil.beanToJson(cmsUser1));


        CmsUser user = new CmsUser();
        user.setUserName("baizhang");
        user.setUserId(10000);
        user.setCreateTime(new Date());
        user.setPassword("123456");

        CmsUser1 user1 = new CmsUser1();

        FastCopy.copySynonyms(user, user1);


        System.out.println(JsonUtil.beanToJson(user1));
    }
}