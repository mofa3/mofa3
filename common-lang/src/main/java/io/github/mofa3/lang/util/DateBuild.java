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

import lombok.Getter;
import lombok.Setter;
import io.github.mofa3.lang.enums.DateEnum;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时间构建工具类
 * 默认实例化当前时间，构造参数有LocalDateTime 和 Date，字符串 --&lt; Date、Date --&lt; 字符串格式化
 *
 * @author lumoere
 * @version $Id: DateBuild.java, v 0.1 2018-05-28 下午8:35 Exp $
 */
@SuppressWarnings("unused")
public class DateBuild {
    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter SIMPLE_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter SIMPLE_DATE = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter SIMPLE_SHORT_DATE = DateTimeFormatter.ofPattern("yyMMdd");

    @Setter
    @Getter
    private LocalDateTime dateTime;

    public DateBuild() {
        this(LocalDateTime.now());
    }

    public DateBuild(final LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public DateBuild(final Date date) {
        this.dateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 指定格式化日期返回
     * 当前类提供格式化格式：
     * DATETIME_FORMAT          yyyy-MM-dd HH:mm:ss
     * SIMPLE_DATETIME_FORMAT   yyyyMMddHHmmss
     * DATE_FORMAT              yyyy-MM-dd
     * SIMPLE_DATE              yyyyMMdd
     *
     * @param formatter 格式化类型
     * @return 格式化后日期字符串
     */
    public String formatter(DateTimeFormatter formatter) {
        return formatter.format(dateTime);
    }

    /**
     * 指定格式化日期返回
     * 默认式化格式：
     * DATETIME_FORMAT          yyyy-MM-dd HH:mm:ss
     *
     * @return 格式化后日期字符串
     */
    public String formatter() {
        return formatter(DATETIME_FORMAT);
    }

    /**
     * 转为Date类型
     *
     * @return Date
     */
    public Date toDate() {
        return Date.from(this.dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取指定时间的开始时间 00:00:00
     *
     * @return DateBuild
     */
    public DateBuild start() {
        dateTime = dateTime.toLocalDate().atStartOfDay();
        return this;
    }

    /**
     * 获取指定时间的结束时间 23:59:5923:59:59.999999999
     *
     * @return DateBuild
     */
    public DateBuild end() {
        dateTime = dateTime.toLocalDate().atTime(LocalTime.MAX);
        return this;
    }

    /**
     * 指定时间加减年数
     * 规则：
     * + 为正数
     * - 为负数
     *
     * @param quantity 加减数
     * @return DateBuild
     */
    public DateBuild addToYear(long quantity) {
        dateTime = plus(dateTime, quantity, DateEnum.YEAR);
        return this;
    }

    /**
     * 指定时间加减月数
     * 规则：
     * + 为正数
     * - 为负数
     *
     * @param quantity 加减数
     * @return DateBuild
     */
    public DateBuild addToMonth(long quantity) {
        dateTime = plus(dateTime, quantity, DateEnum.MONTH);
        return this;
    }

    /**
     * 指定时间加减天数
     * 规则：
     * + 为正数
     * - 为负数
     *
     * @param quantity 加减数
     * @return DateBuild
     */
    public DateBuild addToDay(long quantity) {
        dateTime = plus(dateTime, quantity, DateEnum.DAY);
        return this;
    }

    /**
     * 指定时间加减小时数
     * 规则：
     * + 为正数
     * - 为负数
     *
     * @param quantity 加减数
     * @return DateBuild
     */
    public DateBuild addToHour(long quantity) {
        dateTime = plus(dateTime, quantity, DateEnum.HOUR);
        return this;
    }

    /**
     * 指定时间加减分钟数
     * 规则：
     * + 为正数
     * - 为负数
     *
     * @param quantity 加减数
     * @return DateBuild
     */
    public DateBuild addToMin(long quantity) {
        dateTime = plus(dateTime, quantity, DateEnum.MIN);
        return this;
    }

    /**
     * 指定时间加减秒数
     * 规则：
     * + 为正数
     * - 为负数
     *
     * @param quantity 加减数
     * @return DateBuild
     */
    public DateBuild addToSec(long quantity) {
        dateTime = plus(dateTime, quantity, DateEnum.SEC);
        return this;
    }

    /**
     * 时间加减计算
     *
     * @param time     传入时间
     * @param quantity 加为正数，减为负数
     * @param dateEnum 类型
     * @return LocalDateTime
     */
    private static LocalDateTime plus(LocalDateTime time, long quantity, DateEnum dateEnum) {
        LocalDateTime newTime;
        switch (dateEnum) {
            case YEAR:
                newTime = time.plusYears(quantity);
                return newTime;
            case MONTH:
                newTime = time.plusMonths(quantity);
                return newTime;
            case DAY:
                newTime = time.plusDays(quantity);
                return newTime;
            case HOUR:
                newTime = time.plusHours(quantity);
                return newTime;
            case MIN:
                newTime = time.plusMinutes(quantity);
                return newTime;
            case SEC:
                newTime = time.plusSeconds(quantity);
                return newTime;
            default:
                return time;
        }
    }

}