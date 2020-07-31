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

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 金额计算工具类。
 *
 * @author ${guanzhong}
 * @version $Id: MultiCurrencyMoneyUtil.java, v 0.1 2018年01月05日 下午3:07 Exp $
 */
@SuppressWarnings("unused")
public class MultiCurrencyMoneyUtil {
    /**
     * 金额简单字符串正则表达式
     */
    private static final Pattern MONEY_PATTERN = Pattern
            .compile("^\\[[-]?(([1-9][0-9]*)|0)(\\.[0-9]{1,})?\\,[0-9][0-9][0-9]\\]$");

    /**
     * 金额币种默认分隔符
     */
    private static final String DEMILITER = ",";

    /**
     * 左括号
     */
    private static final String L_BRACKET = "[";

    /**
     * 右括号
     */
    private static final String R_BRACKET = "]";

    /**
     * 金额累加。
     *
     * <p>在原始金额累加（如果原始金额不为null）。
     *
     * @param origin    原始金额
     * @param addAmount 增加金额
     * @return 如果原始金额为null，返回一个新的money对象，否则返回原始金额对象的引用（有可能是null）
     */
    public static MultiCurrencyMoney addTo(MultiCurrencyMoney origin, MultiCurrencyMoney addAmount) {

        if (null == addAmount) {

            return origin;
        }

        if (null == origin) {

            origin = addAmount.clone();
            return origin;
        }

        origin.addTo(addAmount);
        return origin;
    }

    /**
     * 金额累加。
     *
     * <p>金额累加，返回新的金额对象（如果原始金额不为null）。
     *
     * @param origin    原始金额
     * @param addAmount 增加金额
     * @return 如果原始金额和增加金额都为null，则返回null，否则返回一个累加后的金额新对象。
     */
    public static MultiCurrencyMoney add(MultiCurrencyMoney origin, MultiCurrencyMoney addAmount) {

        if (null == origin) {

            return addTo(null, addAmount);
        }

        return addTo(origin.clone(), addAmount);
    }

    /**
     * 金额连续累加。
     *
     * <p>金额累加，返回新的金额对象（如果原始金额不为null）。
     *
     * @param origin     原始金额
     * @param addAmounts 增加金额
     * @return 如果原始金额和增加金额都为null，则返回null，否则返回一个累加后的金额新对象。
     */
    public static MultiCurrencyMoney addContinues(MultiCurrencyMoney origin,
                                                  MultiCurrencyMoney... addAmounts) {

        if (addAmounts.length == 1) {

            return add(origin, addAmounts[0]);
        } else {

            return addContinues(add(origin, addAmounts[0]),
                    Arrays.copyOfRange(addAmounts, 1, addAmounts.length));
        }
    }

    /**
     * 金额扣减。
     *
     * <p>在原始金额扣减（如果原始金额不为null）。
     *
     * @param origin      原始金额
     * @param minusAmount 扣减金额
     * @return 如果原始金额为null，返回一个新的money对象，否则返回原始金额对象的引用（有可能是null）
     */
    public static MultiCurrencyMoney subtractFrom(MultiCurrencyMoney origin,
                                                  MultiCurrencyMoney minusAmount) {

        if (null == minusAmount) {

            return origin;
        }

        if (null == origin) {

            origin = new MultiCurrencyMoney(minusAmount.getAmount(), minusAmount.getCurrency())
                    .multiply(-1);
            return origin;
        }

        origin.subtractFrom(minusAmount);
        return origin;
    }

    /**
     * 金额扣减。
     *
     * <p>金额扣减，返回新的金额对象（如果原始金额不为null）。
     *
     * @param origin      原始金额
     * @param minusAmount 扣减金额
     * @return 如果原始金额和增加金额都为null，则返回null，否则返回一个扣减后的金额新对象。
     */
    public static MultiCurrencyMoney subtract(MultiCurrencyMoney origin,
                                              MultiCurrencyMoney minusAmount) {

        if (null == origin) {

            return subtractFrom(null, minusAmount);
        }

        origin = origin.clone();
        return subtractFrom(origin, minusAmount);
    }

    /**
     * 金额连续扣减。
     *
     * <p>金额扣减，返回新的金额对象（如果原始金额不为null）。
     *
     * @param origin       原始金额
     * @param minusAmounts 扣减金额
     * @return 如果原始金额和增加金额都为null，则返回null，否则返回一个扣减后的金额新对象。
     */
    public static MultiCurrencyMoney subtractContinues(MultiCurrencyMoney origin,
                                                       MultiCurrencyMoney... minusAmounts) {

        if (minusAmounts.length == 1) {

            return subtract(origin, minusAmounts[0]);
        } else {

            return subtractContinues(subtract(origin, minusAmounts[0]),
                    Arrays.copyOfRange(minusAmounts, 1, minusAmounts.length));
        }
    }

    /**
     * 判断左侧金额是否大于右侧金额。
     *
     * @param left  左侧金额（为null时当做0处理）
     * @param right 右侧金额（为null时当做0处理）
     * @return 左侧大于右侧：1；左侧小于右侧：-1；左侧等于右侧：0
     */
    public static int compare(MultiCurrencyMoney left, MultiCurrencyMoney right) {

        MultiCurrencyMoney result = subtract(left, right);

        if (null == result || result.getCent() == 0) {

            return 0;
        }

        if (result.getCent() > 0) {

            return 1;
        }

        return -1;
    }

    /**
     * 输出简单格式的金额字符串
     *
     * @param money 金额对象
     * @return 类似[12.3, 840]这样的格式
     */
    public static String toStr(MultiCurrencyMoney money) {

        if (null == money) {

            return "null";
        }

        return L_BRACKET + money.getAmount() + DEMILITER + money.getCurrencyValue() + R_BRACKET;
    }

    /**
     * 从简单格式的金额字符串构建金额对象
     *
     * @param moneyStr 金额字符串表示，类似[12.3,840]这样的格式
     * @return MultiCurrencyMoney实例
     */
    public static MultiCurrencyMoney fromStr(String moneyStr) {

        if (StringUtils.isBlank(moneyStr)) {
            return null;
        }

        // 确保格式合法
        Matcher mat = MONEY_PATTERN.matcher(moneyStr);

        if (!mat.matches()) {
            throw new IllegalArgumentException();
        }

        // 解析构建金额对象
        moneyStr = StringUtils.replace(moneyStr, L_BRACKET, "");
        moneyStr = StringUtils.replace(moneyStr, R_BRACKET, "");
        String[] moneyAndCur = StringUtils.split(moneyStr, DEMILITER);

        return new MultiCurrencyMoney(moneyAndCur[0], moneyAndCur[1]);
    }

    /**
     * 判断金额是否大于0
     *
     * @param money 多币种金额
     * @return 是否大于0
     */
    public static boolean moneyGreaterThanZero(final MultiCurrencyMoney money) {

        return null != money && money.getCent() > 0;
    }

    /**
     * 从金额中解析出币种枚举值
     *
     * @param money 金额
     * @return 币种枚举
     */
    public static CurrencyEnum resolveCurrency(MultiCurrencyMoney money) {

        if (null == money) {
            return null;
        }

        return CurrencyEnum.getByCurrencyValue(money.getCurrencyValue());

    }

    /**
     * 取2个数目小的金额
     *
     * @param left  左侧金额
     * @param right 右侧金额
     * @return 数目小的金额
     */
    public static MultiCurrencyMoney findMin(MultiCurrencyMoney left, MultiCurrencyMoney right) {

        if (left == null || right == null) {
            return null;
        }

        if (left.compareTo(right) < 0) {
            return left.clone();
        }

        return right.clone();

    }
}
