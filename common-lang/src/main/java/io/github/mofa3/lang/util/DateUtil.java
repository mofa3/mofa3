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
package io.github.mofa3.lang.util;

import lombok.Data;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.Assert;
import io.github.mofa3.lang.enums.DateEnum;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * java 8 时间日期处理，主要是考虑SimpleDateFormat实现是非线程安全，java 8 time几个关键类
 * Instant——它代表的是时间戳
 * LocalDate——不包含具体时间的日期，比如2014-01-14。它可以用来存储生日，周年纪念日，入职日期等。
 * LocalTime——它代表的是不含日期的时间
 * LocalDateTime——它包含了日期及时间，不过还是没有偏移信息或者说时区。
 * ZonedDateTime——这是一个包含时区的完整的日期时间，偏移量是以UTC/格林威治时间为基准的。
 * <p>
 * java time对时间格式要求很严格，这里时间操作方法大部分都是Date作为入参
 *
 * @author lumoere
 * @version $Id: DateUtil.java, v 0.1 2018-01-12 下午1:49 Exp $
 */
@Data
@SuppressWarnings("unused")
public class DateUtil {

    /**
     * 时间戳获取，到毫秒
     *
     * @return long
     */
    public static long timeStampMilli() {
        return Instant.now().toEpochMilli();
    }

    /**
     * 时间戳获取，到秒
     *
     * @return long
     */
    public static long timeStampSecond() {
        return Instant.now().getEpochSecond();
    }

    /**
     * 字符串转为时间
     * 格式化要求 满足 yyyy-MM-dd HH:mm:ss，匹配规则以DateTimeFormatter实例为准，LocalDateTime接收
     * 仅年月日格式化会抛出异常
     *
     * @param date      date
     * @param formatter 时间格式化类型
     * @return date
     */
    public static Date strToDate(String date, DateTimeFormatter formatter) {
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 计算两个时间差，dateEnum决定计算单位
     * 注意：如果通过#{DateBuild}获取结算时间计算当日剩余毫秒数，会出现1s的误差，原因是当日结束时间设置为23:59:59
     * 所以要求高精度的时间计算，不合适使用该方法
     * <p>
     * 很多时候，我们使用每日开始时间（yyyy-MM-dd 00:00:00）、每日结束时间（yyyy-MM-dd 23:59:59），在做时间差
     * 计算的时候，如果算日（day）差值，0点0分0秒到23点59分59秒是不足一天，不会计算在内，其计算方式是以
     * LocalDate{@link java.time.LocalDate#until(Temporal, TemporalUnit)}处理年月日，
     * LocalTime{@link java.time.LocalTime#until(Temporal, TemporalUnit)}处理时分秒
     * 在计算时间差的时候需要特别注意这个问题
     *
     * @param v1       时间1
     * @param v2       时间2
     * @param dateEnum 计算单位
     * @return 相差数
     */
    public static long timeDiffer(Date v1, Date v2, DateEnum dateEnum) {
        switch (dateEnum) {
            case YEAR:
                return ChronoUnit.YEARS.between(new DateBuild(v1).getDateTime(),
                        new DateBuild(v2).getDateTime());
            case MONTH:
                return ChronoUnit.MONTHS.between(new DateBuild(v1).getDateTime(),
                        new DateBuild(v2).getDateTime());
            case DAY:
                return ChronoUnit.DAYS.between(new DateBuild(v1).getDateTime(),
                        new DateBuild(v2).getDateTime());
            case HOUR:
                return ChronoUnit.HOURS.between(new DateBuild(v1).getDateTime(),
                        new DateBuild(v2).getDateTime());
            case MIN:
                return ChronoUnit.MINUTES.between(new DateBuild(v1).getDateTime(),
                        new DateBuild(v2).getDateTime());
            case SEC:
                return ChronoUnit.SECONDS.between(new DateBuild(v1).getDateTime(),
                        new DateBuild(v2).getDateTime());
            case MILLIS:
                return ChronoUnit.MILLIS.between(new DateBuild(v1).getDateTime(),
                        new DateBuild(v2).getDateTime());
            default:
                return -1;
        }
    }

    /**
     * 时间v1是否在时间v2之前
     * 注意：如果比较需要精确到毫秒级，设置时间到毫秒
     *
     * @param v1 date1
     * @param v2 date2
     * @return boolean
     */
    public static boolean isBefore(Date v1, Date v2) {
        Assert.notNull(v1, "v1时间比较对象不能为NULL");
        Assert.notNull(v2, "v2时间比较对象不能为NULL");
        LocalDateTime time1 = v1.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime time2 = v2.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return time1.isBefore(time2);
    }

    /**
     * 时间v1是否在时间v2之后
     * 注意：如果比较需要精确到毫秒级，设置时间到毫秒
     *
     * @param v1 date1
     * @param v2 date2
     * @return boolean
     */
    public static boolean isAfter(Date v1, Date v2) {
        Assert.notNull(v1, "v1时间比较对象不能为NULL");
        Assert.notNull(v2, "v2时间比较对象不能为NULL");
        LocalDateTime time1 = v1.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime time2 = v2.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return time1.isAfter(time2);
    }

    /**
     * 计算当天还剩下多少毫秒
     *
     * @return 计算后剩余毫秒数
     */
    public static long milliSecondsLeftToday() {
        return 86400000 - DateUtils.getFragmentInMilliseconds(Calendar.getInstance(), Calendar.DATE);
    }

    /**
     * 计算传入日期的自然周开始时间（周一日期）
     *
     * @param date date时间
     * @return date
     */
    public static Date firstDayOfWeek(Date date) {
        return new DateBuild(date.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDateTime().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)))
                .start().toDate();
    }

    /**
     * 计算传入日期的自然周结束时间（周日日期）
     *
     * @param date date时间
     * @return date
     */
    public static Date lastDayOfWeek(Date date) {
        return new DateBuild(date.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDateTime().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))).end()
                .toDate();
    }

    /**
     * 计算传入日期的自然月结束时间（月最开始一天日期，1号）
     *
     * @param date date时间
     * @return date
     */
    public static Date firstDayOfMonth(Date date) {
        return new DateBuild(date.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDateTime().with(TemporalAdjusters.firstDayOfMonth())).start().toDate();
    }

    /**
     * 计算传入日期的自然月结束时间（月最后一天日期）
     *
     * @param date date时间
     * @return date
     */
    public static Date lastDayOfMonth(Date date) {
        return new DateBuild(date.toInstant().atZone(ZoneId.systemDefault())
                .toLocalDateTime().with(TemporalAdjusters.lastDayOfMonth())).end().toDate();
    }
}