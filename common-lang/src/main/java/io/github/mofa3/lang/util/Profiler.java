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

import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

/**
 * 本工具是用来测试并统计线程执行时间的工具
 *
 * @author ${guanzhong}
 * @version $Id: Profiler.java, v 0.1 2017年11月28日 下午10:56 Exp $
 */
@SuppressWarnings("unused")
public final class Profiler {

    /**
     * 线程变量
     */
    private static final ThreadLocal<Entry> ENTRY_STACK = new ThreadLocal<>();

    /**
     * 开始计时
     */
    public static void start() {
        start((String) null);
    }

    /**
     * 开始计时
     *
     * @param message message 第一个entry的信息
     */
    public static void start(String message) {
        ENTRY_STACK.set(new Entry(message, null, null));
    }

    /**
     * 开始计时
     *
     * @param message message 第一个entry的信息
     */
    public static void start(Message message) {
        ENTRY_STACK.set(new Entry(message, null, null));
    }

    /**
     * 清除计时器
     * 清除以后必须再次调用start方可重新计时。
     */
    public static void reset() {
        ENTRY_STACK.remove();
    }

    /**
     * 开始一个新的entry，并计时
     *
     * @param message 新entry的信息
     */
    public static void enter(String message) {
        Entry currentEntry = getCurrentEntry();
        if (currentEntry != null) {
            currentEntry.enterSubEntry(message);
        }else{
            ENTRY_STACK.remove();
        }
    }

    /**
     * 开始一个新的entry，并计时
     *
     * @param message 新entry的信息
     */
    public static void enter(Message message) {
        Entry currentEntry = getCurrentEntry();
        if (currentEntry != null) {
            currentEntry.enterSubEntry(message);
        } else {
            ENTRY_STACK.remove();
        }
    }

    /**
     * 结束最近的一个entry，记录结束时间
     */
    public static void release() {
        Entry currentEntry = getCurrentEntry();

        if (currentEntry != null) {
            currentEntry.release();
        } else {
            ENTRY_STACK.remove();
        }
    }

    /**
     * 取得耗费的总时间
     *
     * @return 耗费的总时间，如果未开始计时，则返回-1
     */
    public static long getDuration() {
        Entry entry = ENTRY_STACK.get();
        if (entry != null) {
            return entry.getDuration();
        } else {
            ENTRY_STACK.remove();
            return -1;
        }
    }

    /**
     * 列出所有的entry
     *
     * @return 列出所有entry，并统计各自所占用的时间
     */
    public static String dump() {
        return dump("", "");
    }

    /**
     * 列出所有的entry
     *
     * @param prefix 前缀
     * @return 列出所有entry，并统计各自所占用的时间
     */
    public static String dump(String prefix) {
        return dump(prefix, prefix);
    }

    /**
     * 列出所有的entry
     *
     * @param prefix1 首行前缀
     * @param prefix2 后续行前缀
     * @return 列出所有entry，并统计各自所占用的时间
     */
    private static String dump(String prefix1, String prefix2) {
        Entry entry = ENTRY_STACK.get();

        if (entry != null) {
            return entry.toString(prefix1, prefix2);

        } else {
            ENTRY_STACK.remove();
            return StringUtils.EMPTY;

        }

    }

    /**
     * 取得第一个entry
     *
     * @return 第一个entry，如果不存在，则返回null
     */
    public static Entry getEntry() {
        return ENTRY_STACK.get();
    }

    /**
     * 取得最近的一个entry
     *
     * @return 最近的一个entry，如果不存在，则返回null
     */
    private static Entry getCurrentEntry() {
        Entry subEntry = ENTRY_STACK.get();
        Entry entry = null;

        if (subEntry != null) {
            do {
                entry = subEntry;
                subEntry = entry.getUnreleasedEntry();

            } while (subEntry != null);

        }

        return entry;

    }

    /**
     * 代表一个计时单元
     */
    public static final class Entry {
        private final List<Entry> subEntries = Lists.newArrayListWithExpectedSize(4);
        private final Object message;
        private final Entry parentEntry;
        private final Entry firstEntry;
        private final long baseTime;
        private final long startTime;
        private long endTime;

        /**
         * 创建一个新的entry
         *
         * @param message     entry的信息，可以是null
         * @param parentEntry 父entry，可以是null
         * @param firstEntry  第一个entry，可以是null
         */
        Entry(final Object message, final Entry parentEntry, final Entry firstEntry) {
            this.message = message;
            this.parentEntry = parentEntry;
            this.firstEntry = ObjectUtils.defaultIfNull(firstEntry, this);
            this.startTime = System.currentTimeMillis();
            this.baseTime = (firstEntry == null) ? 0 : firstEntry.startTime;
        }

        /**
         * 取得entry的信息
         */
        String getMessage() {

            String messageString = null;

            if (message instanceof String) {
                messageString = (String) message;

            } else if (message instanceof Message) {
                Message messageObject = (Message) message;
                MessageLevel level = MessageLevel.BRIEF_MESSAGE;

                if (isReleased()) {
                    level = messageObject.getMessageLevel(this);

                }

                if (level == MessageLevel.DETAILED_MESSAGE) {
                    messageString = messageObject.getDetailedMessage();

                } else {
                    messageString = messageObject.getBriefMessage();

                }

            }

            return StringUtils.defaultIfEmpty(messageString, null);

        }

        /**
         * 取得entry相对于第一个entry的起始时间
         *
         * @return 相对起始时间
         */
        long getStartTime() {
            return (baseTime > 0) ? (startTime - baseTime) : 0;

        }

        /**
         * 取得entry相对于第一个entry的结束时间
         *
         * @return 相对结束时间，如果entry还未结束，则返回-1
         */
        public long getEndTime() {
            if (endTime < baseTime) {
                return -1;

            } else {
                return endTime - baseTime;

            }
        }

        /**
         * 取得entry持续的时间
         *
         * @return entry持续的时间，如果entry还未结束，则返回-1
         */
        long getDuration() {
            if (endTime < startTime) {
                return -1;

            } else {
                return endTime - startTime;
            }

        }

        /**
         * 取得entry自身所用的时间，即总时间减去所有子entry所用的时间
         *
         * @return entry自身所用的时间，如果entry还未结束，则返回-1
         */
        long getDurationOfSelf() {
            long duration = getDuration();

            if (duration < 0) {
                return -1;

            } else if (subEntries.isEmpty()) {
                return duration;

            } else {
                for (Object subEntry1 : subEntries) {
                    Entry subEntry = (Entry) subEntry1;
                    duration -= subEntry.getDuration();
                }

                if (duration < 0) {
                    return -1;
                } else {
                    return duration;
                }

            }

        }

        /**
         * 取得当前entry在父entry中所占的时间百分比
         *
         * @return 百分比
         */
        double getPercentage() {
            double parentDuration = 0;
            double duration = getDuration();

            if ((parentEntry != null) && parentEntry.isReleased()) {
                parentDuration = parentEntry.getDuration();
            }

            if ((duration > 0) && (parentDuration > 0)) {
                return duration / parentDuration;
            } else {
                return 0;
            }

        }

        /**
         * 取得当前entry在第一个entry中所占的时间百分比
         *
         * @return 百分比
         */
        double getPercentageOfAll() {
            double firstDuration = 0;
            double duration = getDuration();

            if ((firstEntry != null) && firstEntry.isReleased()) {
                firstDuration = firstEntry.getDuration();
            }

            if ((duration > 0) && (firstDuration > 0)) {
                return duration / firstDuration;

            } else {
                return 0;

            }

        }

        /**
         * 取得所有子entries
         *
         * @return 所有子entries的列表（不可更改）
         */
        public List getSubEntries() {
            return Collections.unmodifiableList(subEntries);
        }

        /**
         * 结束当前entry，并记录结束时间
         */
        private void release() {
            endTime = System.currentTimeMillis();
        }

        /**
         * 判断当前entry是否结束
         *
         * @return 如果entry已经结束，则返回true
         */
        private boolean isReleased() {
            return endTime > 0;
        }

        /**
         * 创建一个新的子entry
         *
         * @param message message 子entry的信息
         */
        private void enterSubEntry(Object message) {
            Entry subEntry = new Entry(message, this, firstEntry);
            subEntries.add(subEntry);
        }

        /**
         * 取得未结束的子entry
         *
         * @return 未结束的子entry，如果没有子entry，或所有entry均已结束，则返回null
         */
        private Entry getUnreleasedEntry() {
            Entry subEntry = null;

            if (!subEntries.isEmpty()) {
                subEntry = subEntries.get(subEntries.size() - 1);
                if (subEntry.isReleased()) {
                    subEntry = null;
                }
            }

            return subEntry;

        }

        /**
         * 将entry转换成字符串的表示
         *
         * @return 字符串表示的entry
         */
        @Override
        public String toString() {
            return toString("", "");

        }

        /**
         * 将entry转换成字符串的表示
         *
         * @param prefix1 首行前缀
         * @param prefix2 后续行前缀
         * @return 字符串表示的entry
         */
        private String toString(String prefix1, String prefix2) {
            StringBuffer buffer = new StringBuffer();
            toString(buffer, prefix1, prefix2);
            return buffer.toString();

        }

        /**
         * 将entry转换成字符串的表示
         *
         * @param buffer  字符串buffer
         * @param prefix1 首行前缀
         * @param prefix2 后续行前缀
         */
        private void toString(StringBuffer buffer, String prefix1, String prefix2) {
            buffer.append(prefix1);
            String message = getMessage();
            long startTime = getStartTime();
            long duration = getDuration();
            long durationOfSelf = getDurationOfSelf();
            double percent = getPercentage();
            double percentOfAll = getPercentageOfAll();
            Object[] params = new Object[]{
                    // {0} - entry信息
                    message,
                    // {1} - 起始时间
                    startTime,
                    // {2} - 持续总时间
                    duration,
                    // {3} - 自身消耗的时间
                    durationOfSelf,
                    // {4} - 在父entry中所占的时间比例
                    percent,
                    // {5} - 在总时间中所占的时间比例
                    percentOfAll
            };
            StringBuilder sb = new StringBuilder("{1,number,#} ");
            if (isReleased()) {
                sb.append("[{2,number,#}ms");
                if ((durationOfSelf > 0) && (durationOfSelf != duration)) {
                    sb.append(" ({3,number,#}ms)");
                }
                if (percent > 0) {
                    sb.append(", {4,number,##%}");
                }
                if (percentOfAll > 0) {
                    sb.append(", {5,number,##%}");
                }
                sb.append("]");
            } else {
                sb.append("[UNRELEASED]");
            }
            if (message != null) {
                sb.append(" - {0}");
            }
            buffer.append(MessageFormat.format(sb.toString(), params));
            for (int i = 0, size = subEntries.size(); i < size; i++) {
                Entry subEntry = subEntries.get(i);
                buffer.append('\n');
                if (i == (subEntries.size() - 1)) {
                    // 最后一项
                    subEntry.toString(buffer, prefix2 + "`---", prefix2 + "    ");
                } else if (i == 0) {
                    // 第一项
                    subEntry.toString(buffer, prefix2 + "+---", prefix2 + "|   ");
                } else {
                    // 中间项
                    subEntry.toString(buffer, prefix2 + "+---", prefix2 + "|   ");
                }
            }
        }
    }

    /**
     * 显示消息的级别 枚举
     */
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    enum MessageLevel {
        /**
         * 无信息
         */
        NO_MESSAGE,
        /**
         * 简要信息
         */
        BRIEF_MESSAGE,
        /**
         * 详细信息
         */
        DETAILED_MESSAGE,
        ;
    }

    /**
     * 代表一个profiler entry的详细信息。
     */
    interface Message {

        /**
         * 获取信息级别
         *
         * @param entry Entry
         * @return 消息级别
         */
        MessageLevel getMessageLevel(Entry entry);

        /**
         * 获取简要信息
         *
         * @return 简要消息
         */
        String getBriefMessage();

        /**
         * 获取详细信息
         *
         * @return 详细信息
         */
        String getDetailedMessage();

    }

}



