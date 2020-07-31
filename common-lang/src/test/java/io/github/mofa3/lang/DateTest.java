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

import io.github.mofa3.lang.util.DateBuild;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

/**
 * TODO
 *
 * @author ${baizhang}
 * @version $Id: DateTest.java, v 0.1 2018-05-25 下午2:41 Exp $
 */
public class DateTest {
    public static void main(String[] args) {
        //LocalDate today= LocalDate.now();
        //System.out.println("LocalDate: "+ today);
        //System.out.println("LocalDate year: "+today.getYear());
        //System.out.println("LocalDate month: "+ today.getMonthValue());
        //System.out.println("LocalDate day: "+ today.getDayOfMonth());
        //System.out.println("DayOfWeek: "+today.getDayOfWeek().getValue());
        //System.out.println("DayOfYear: "+today.getDayOfYear());
        //
        //System.out.println("------------------DateTimeFormatter test------------------");
        //LocalDateTime now = LocalDateTime.now();
        //String formatted = DateTimeFormatter.ISO_DATE_TIME.format(now);
        //System.out.println("ISO_DATE_TIME：" + formatted);
        //System.out.println("BASIC_ISO_DATE:" + DateTimeFormatter.BASIC_ISO_DATE.format(now));
        //System.out.println("ISO_LOCAL_DATE_TIME:" + DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(now));
        //System.out.println("ISO_LOCAL_DATE:" + DateTimeFormatter.ISO_LOCAL_DATE.format(now));
        //System.out.println("ISO_ORDINAL_DATE:" + DateTimeFormatter.ISO_ORDINAL_DATE.format(now));
        //System.out.println("ISO_WEEK_DATE:" + DateTimeFormatter.ISO_WEEK_DATE.format(now));
        //DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        //System.out.println(formatter.format(now));
        //DateTimeFormatter f = DateTimeFormatter.ofPattern("VV yyyy年MM月dd日HH时mm分ss秒");
        //System.out.println(f.format(ZonedDateTime.now()));
        //LocalDate date = LocalDate.parse("2018-05-21", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        //System.out.println(date);


        //LocalDateTime time =  LocalDateTime.now();
        //time.plusDays(3);
        ////LocalDateTime time1= time.minusDays(902);
        //System.out.println("sss"+ time);
        //
        //
        //System.out.println("--------------------------");
        //Date date= new Date();
//        System.out.println("当前时间："+DateUtil.formatterDate(date, DateUtil.DATETIME_FORMAT));
//        System.out.println("往后2年："+ DateUtil.formatterDate(DateUtil.getAddToYear(date, 2), DateUtil.DATETIME_FORMAT));
//        System.out.println("往前1年："+ DateUtil.formatterDate(DateUtil.getAddToYear(date, -1), DateUtil.DATETIME_FORMAT));
//        System.out.println("往后1月："+ DateUtil.formatterDate(DateUtil.getAddToMonth(date, 1), DateUtil.DATETIME_FORMAT));
//        System.out.println("往前1月："+ DateUtil.formatterDate(DateUtil.getAddToMonth(date, -1), DateUtil.DATETIME_FORMAT));
//        System.out.println("往后1日：" + DateUtil.formatterDate(DateUtil.getAddToDay(date, 1), DateUtil.DATETIME_FORMAT));
//        System.out.println("往前1日：" + DateUtil.formatterDate(DateUtil.getAddToDay(date, -1), DateUtil.DATETIME_FORMAT));
//
//        System.out.println("往后3小时：" + DateUtil.formatterDate(DateUtil.getAddToHour(date, 3), DateUtil.DATETIME_FORMAT));
//        System.out.println("往前1小时：" + DateUtil.formatterDate(DateUtil.getAddToHour(date, -1), DateUtil.DATETIME_FORMAT));
//
//        System.out.println("往后10分钟：" + DateUtil.formatterDate(DateUtil.getAddToMin(date, 10), DateUtil.DATETIME_FORMAT));
//        System.out.println("往前3分钟：" + DateUtil.formatterDate(DateUtil.getAddToMin(date, -3), DateUtil.DATETIME_FORMAT));
//
//        System.out.println("往后2秒：" + DateUtil.formatterDate(DateUtil.getAddToSec(date, 2), DateUtil.DATETIME_FORMAT));
//        System.out.println("往前3秒：" + DateUtil.formatterDate(DateUtil.getAddToSec(date, -3), DateUtil.DATETIME_FORMAT));


        //System.out.println("==========================");
        //System.out.println(DateUtil.timeStampMilli());
        //System.out.println(DateUtil.timeStampSecond());
        //
        //Calendar c1= Calendar.getInstance();
        //c1.set(2018, 5,12,10,35,27);
        //
        //Calendar c2= Calendar.getInstance();
        //c2.set(2018, 5,12,10,35,27);
        //
        //System.out.println(c1.getTimeInMillis());
        //System.out.println(c2.getTimeInMillis());
        //System.out.println("c1在c2之前："+ DateUtil.isBefore(c1.getTime(), c2.getTime()));
        //System.out.println("c1在c2之后："+ DateUtil.isAfter(c1.getTime(), c2.getTime()));
        //Calendar now = Calendar.getInstance();
        //System.out.println(now.getTime());
        //now.add(Calendar.MINUTE, 1);
        //System.out.println(now.getTime());
        //System.out.println(DateUtil.timeDiffer(new DateBuild().toDate(), new DateBuild().end().toDate(), DateEnum.MILLIS));
        //
        //System.out.println(DateUtil.milliSecondsLeftToday());
        //
        //System.out.println(new DateBuild().formatter(DateBuild.SIMPLE_DATETIME_FORMAT));

//        LocalDate startDate = LocalDate.of(1993, Month.OCTOBER, 19);
//        System.out.println("开始时间  : " + startDate);
//
//        LocalDate endDate = LocalDate.of(2017, Month.JUNE, 16);
//        System.out.println("结束时间 : " + endDate);
//
//        long daysDiff = ChronoUnit.DAYS.between(startDate, endDate);
//        System.out.println("两天之间的差在天数   : " + daysDiff);

//        new Date1("111").start();
//        new Date2("222").start();
//        new Date3("333").start();
//        new Date4("444").start();
//        new Date5("555").start();


        //String date= "2018-06-08 10:02:18";
        //
        //System.out.println(DateUtil.strToDate(date, DateBuild.SIMPLE_DATETIME_FORMAT));
//
//        Date date= new Date();
//        System.out.println(new DateBuild(date).addToDay(1).end().toDate());
//
//        LocalDateTime dateTime= LocalDateTime.of(2018,10,28, 0,0,0);
//
//        LocalDateTime dataTime1= dateTime.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
//        System.out.println(new DateBuild(dataTime1).formatter());
//
//        LocalDateTime dataTime2 = dateTime.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
//        System.out.println(new DateBuild(dataTime2).formatter());
//
//        LocalDateTime dataTime3 = dateTime.with(TemporalAdjusters.firstDayOfMonth());
//        System.out.println(new DateBuild(dataTime3).formatter());
//
//        LocalDateTime dataTime4 = dateTime.with(TemporalAdjusters.lastDayOfMonth());
//        System.out.println(new DateBuild(dataTime4).formatter());
//
//        System.out.println(new DateBuild(DateUtil.firstDayOfWeek(new Date())).formatter());
//        System.out.println(new DateBuild(DateUtil.lastDayOfWeek(new Date())).formatter());
//        System.out.println(new DateBuild(DateUtil.firstDayOfMonth(new Date())).formatter());
//        System.out.println(new DateBuild(DateUtil.lastDayOfMonth(new Date())).formatter());
//
//
//        System.out.println("====================================================");
//
//        LocalDateTime startTime = LocalDateTime.of(2019, 4, 17, 20, 0, 0);
//        LocalDateTime endTime = LocalDateTime.of(2019, 4, 17, 23, 59, 59);
//
//        System.out.println("相差月数：" + DateUtil.timeDiffer(new DateBuild(startTime).toDate(),
//                new DateBuild(endTime).toDate(), DateEnum.MONTH));
//        System.out.println("相差天数："+DateUtil.timeDiffer(new DateBuild(startTime).toDate(),
//                new DateBuild(endTime).toDate(), DateEnum.DAY));
//        System.out.println("相差小时数：" + DateUtil.timeDiffer(new DateBuild(startTime).toDate(),
//                new DateBuild(endTime).toDate(), DateEnum.HOUR));
//        System.out.println("相差分钟数：" + DateUtil.timeDiffer(new DateBuild(startTime).toDate(),
//                new DateBuild(endTime).toDate(), DateEnum.MIN));
//        System.out.println("相差秒数：" + DateUtil.timeDiffer(new DateBuild(startTime).toDate(),
//                new DateBuild(endTime).toDate(), DateEnum.SEC));
//        System.out.println("相差秒数：" + DateUtil.timeDiffer(new DateBuild(startTime).toDate(),
//                new DateBuild(endTime).toDate(), DateEnum.MILLIS));
//
//
//        System.out.println(new DateBuild().end().formatter());

//        System.out.println(new DateBuild().addToMonth(2).addToDay(9).formatter(DateTimeFormatter.ofPattern("yyyy-M-d")));

        String dateStr = "2019-11-21";
        String timeStr = "14:34:12";

//        System.out.println(DateUtil.strToDate(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd")));


//        LocalDateTime dateTime =  LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

//        System.out.println("dateTime: "+dateTime.toString());

        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM"));
        System.out.println("date: " + date.toString());
        LocalTime time = LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm"));
        System.out.println("time: " + time.toString());
    }
}

class Date1 extends Thread {
    private String name;

    public Date1(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        Calendar c = Calendar.getInstance();
        c.set(2018, 5, 12, 10, 35, 25);
        System.out.println(name + " 子线程ID-" + Thread.currentThread().getId() + ":" + new DateBuild(c.getTime()).formatter());
        System.out.println(name + " 子线程ID-" + Thread.currentThread().getId() + "+5天2小时:" + new DateBuild(c.getTime()).addToDay(5).addToHour(2).formatter());
    }
}

class Date2 extends Thread {
    private String name;

    public Date2(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        Calendar c = Calendar.getInstance();
        c.set(2018, 5, 20, 20, 5, 10);
        System.out.println(name + " 子线程ID-" + Thread.currentThread().getId() + ":" + new DateBuild(c.getTime()).formatter());
        System.out.println(name + " 子线程ID-" + Thread.currentThread().getId() + "+3天1小时:" + new DateBuild(c.getTime()).addToDay(3).addToHour(1).formatter());
    }
}

class Date3 extends Thread {
    private String name;

    public Date3(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        Calendar c = Calendar.getInstance();
        c.set(2016, 11, 3, 16, 22, 10);
        System.out.println(name + " 子线程ID-" + Thread.currentThread().getId() + ":" + new DateBuild(c.getTime()).formatter());
        System.out.println(name + " 子线程ID-" + Thread.currentThread().getId() + "+1天3小时:" + new DateBuild(c.getTime()).addToDay(1).addToHour(3).formatter());
    }
}

class Date4 extends Thread {
    private String name;

    public Date4(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        Calendar c = Calendar.getInstance();
        c.set(2017, 6, 4, 16, 22, 10);
        System.out.println(name + " 子线程ID-" + Thread.currentThread().getId() + ":" + new DateBuild(c.getTime()).formatter());
        System.out.println(name + " 子线程ID-" + Thread.currentThread().getId() + "+6天4小时:" + new DateBuild(c.getTime()).addToDay(6).addToHour(4).formatter());
    }
}

class Date5 extends Thread {
    private String name;

    public Date5(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        Calendar c = Calendar.getInstance();
        c.set(2017, 9, 29, 13, 9, 10);
        System.out.println(name + " 子线程ID-" + Thread.currentThread().getId() + ":" + new DateBuild(c.getTime()).formatter());
        System.out.println(name + " 子线程ID-" + Thread.currentThread().getId() + "+4天5小时:" + new DateBuild(c.getTime()).addToDay(4).addToHour(5).formatter());
    }
}
