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
package io.github.mofa3.lang.money;

import java.math.BigDecimal;

/**
 * 检查数学运算中的溢出
 *
 * @author ${guanzhong}
 * @version $Id: CheckOverflow.java, v 0.1 2018年01月05日 下午3:07 Exp $
 */
@SuppressWarnings("unused")
public class CheckOverflow {

    /**
     * long 最大值
     */
    private static final BigDecimal MAX_LONG = new BigDecimal(Long.MAX_VALUE);
    /**
     * long 最小值
     */
    private static final BigDecimal MIN_LONG = new BigDecimal(Long.MIN_VALUE);

    /**
     * 溢出异常抛出
     */
    private static void checkNoOverflow(boolean b) {
        if (!b) {
            throw new ArithmeticException("overflow");
        }
    }

    /**
     * 检查decimal是否溢出 long
     *
     * @return long值
     */
    public static long bigDecimalChecked(BigDecimal a) {
        if (a.compareTo(MAX_LONG) > 0 || a.compareTo(MIN_LONG) < 0) {
            throw new ArithmeticException("overflow");
        }
        return a.longValue();
    }

    /**
     * 带溢出检查的double乘法 , 注意此方法最后检查是否超过Long
     */
    public static double doubleCheckedMultiply(double a, long cent) {
        double result = a * cent;
        if (result > Long.MAX_VALUE || result < Long.MIN_VALUE) {
            throw new ArithmeticException("overflow");
        }

        return result;
    }

    /**
     * 带溢出检查的int加法
     */
    public static int intCheckedAdd(int a, int b) {
        long result = (long) a + b;
        checkNoOverflow(result == (int) result);
        return (int) result;
    }

    /**
     * 带溢出检查的int减法
     */
    public static int intCheckedSubtract(int a, int b) {
        long result = (long) a - b;
        checkNoOverflow(result == (int) result);
        return (int) result;

    }

    /**
     * 带溢出检查的int乘法
     */
    public static int intCheckedMultiply(int a, int b) {
        long result = (long) a * b;
        checkNoOverflow(result == (int) result);
        return (int) result;
    }

    /**
     * 带溢出检查的long加法
     */
    public static long longCheckedAdd(long a, long b) {
        long result = a + b;
        checkNoOverflow((a ^ b) < 0 | (a ^ result) >= 0);
        return result;
    }

    /**
     * 带溢出检查的long减法
     */
    public static long longCheckedSubtract(long a, long b) {
        long result = a - b;
        checkNoOverflow((a ^ b) >= 0 | (a ^ result) >= 0);
        return result;
    }

    /**
     * 带溢出检查的long乘法
     */
    public static long longCheckedMultiply(long a, long b) {
        // Hacker's Delight, Section 2-12
        int leadingZeros = Long.numberOfLeadingZeros(a) + Long.numberOfLeadingZeros(~a)
                + Long.numberOfLeadingZeros(b) + Long.numberOfLeadingZeros(~b);
        /*
         * If leadingZeros > Long.SIZE + 1 it's definitely fine, if it's < Long.SIZE it's definitely
         * bad. We do the leadingZeros check to avoid the division below if at all possible.
         *
         * Otherwise, if b == Long.MIN_VALUE, then the only allowed values of a are 0 and 1. We take
         * care of all a < 0 with their own check, because in particular, the case a == -1 will
         * incorrectly pass the division check below.
         *
         * In all other cases, we check that either a is 0 or the result is consistent with division.
         */
        if (leadingZeros > Long.SIZE + 1) {
            return a * b;
        }
        checkNoOverflow(leadingZeros >= Long.SIZE);
        checkNoOverflow(a >= 0 | b != Long.MIN_VALUE);
        long result = a * b;
        checkNoOverflow(a == 0 || result / a == b);
        return result;
    }

}
