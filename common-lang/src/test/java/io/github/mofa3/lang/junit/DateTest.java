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
package io.github.mofa3.lang.junit;

import io.github.mofa3.lang.enums.DateEnum;
import io.github.mofa3.lang.util.DateBuild;
import io.github.mofa3.lang.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

/**
 * TODO
 *
 * @author lumoere
 * @version $Id:DateTest.java,v 1.0 2020-08-10 22:14 lumoere Exp $
 */
@Slf4j
public class DateTest {
    Calendar specifiedTime = null;
    public void init(){
        specifiedTime = Calendar.getInstance();
        specifiedTime.set(2020,8,10,22,20,18);
    }

    @Test
    public void strToDateTest(){
        Assert.assertEquals("20200810", DateUtil.strToDate("20200810", DateBuild.SIMPLE_DATE));
    }

    @Test
    public void timeDifferTest(){
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.set(2020, 0, 1, 0, 0, 1);
        c2.set(2019, 0, 1, 0, 0, 0);

        System.out.println(new DateBuild(c1.getTime()).formatter());
        System.out.println(new DateBuild(c2.getTime()).formatter());
        System.out.println(DateUtil.timeDiffer(c1.getTime(), c2.getTime(), DateEnum.YEAR));
        Assert.assertEquals(1, DateUtil.timeDiffer(c1.getTime(), c2.getTime(), DateEnum.YEAR));

//        c2.set(2019, 12, 10, 22, 20, 18);
        Assert.assertEquals(12, DateUtil.timeDiffer(c1.getTime(), c2.getTime(), DateEnum.MONTH));

        c2.set(2020, 0, 26, 0, 0, 0);
        Assert.assertEquals(25, DateUtil.timeDiffer(c1.getTime(), c2.getTime(), DateEnum.MONTH));

    }

    @Test
    public void isBeforeTest(){
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.set(2020, 0, 1, 0, 0, 1);
        c2.set(2019, 0, 1, 0, 0, 0);
        Assert.assertTrue(DateUtil.isBefore(c2.getTime(), c1.getTime()));
        Assert.assertTrue(DateUtil.isAfter(c1.getTime(), c2.getTime()));

    }
}