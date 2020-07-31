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

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import io.github.mofa3.lang.common.constant.MofaConstants;

import java.io.Serializable;

/**
 * 金额区间，配置格式：[0,+)，(10000,50000)
 *
 * @author ${guanzhong}
 * @version $Id: AmountRange.java, v 0.1 2018年01月05日 下午3:07 Exp $
 */
@SuppressWarnings("unused")
@Data
public class AmountRange implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1716115814063676194L;

    private static final int RANGE_VALUE = 2;

    /**
     * 支付额度区间-左闭合
     */
    private static final char PAY_EXTENT_LEFT_CLOSE = '[';

    /**
     * 支付额度区间-左开
     */
    private static final char PAY_EXTENT_LEFT_OPEN = '(';

    /**
     * 支付额度区间-右闭合
     */
    private static final char PAY_EXTENT_RIGHT_CLOSE = ']';

    /**
     * 支付额度区间-右开
     */
    private static final char PAY_EXTENT_RIGHT_OPEN = ')';

    /**
     * 额度下限
     */
    private MultiCurrencyMoney from = new MultiCurrencyMoney(-1);

    /**
     * 额度上限
     */
    private MultiCurrencyMoney to = new MultiCurrencyMoney(-1);

    /**
     * 是否包含额度下限
     */
    private boolean isFromClose = false;

    /**
     * 是否包含额度上限
     */
    private boolean isToClose = false;

    /**
     * 判断参数金额是否超出额度区间。
     * <br><p>如果币种不同，则认为没超出额度区间。</p>
     *
     * @param amount 进行比较的金额
     * @return 是否超出区间，true为超出，false为不超出
     */
    public boolean isOverRange(MultiCurrencyMoney amount) {
        return !satisfiedExtent(amount);
    }

    /**
     * 判断参数金额是否超出额度区间下限。
     * <br><p>如果币种不同，则认为没超出额度区间。</p>
     *
     * @param amount 进行比较的金额
     * @return 是否超出区间下限，true为超出，false为不超出
     */
    public boolean isOverRangeBegin(MultiCurrencyMoney amount) {
        return amount.getCurrency() == amount.getCurrency()
                && (!isFromClose || !from.equals(amount))
                && ((MultiCurrencyMoneyUtil.compare(amount, from) <= 0));

    }

    /**
     * 判断参数金额是否在额度区间内。
     * <br><p>如果币种不同，则认为不满足额度区间。</p>
     *
     * @param amount 进行比较的金额
     * @return 是否在额度区间内，true为满足，false为不满足
     */
    public boolean satisfiedExtent(MultiCurrencyMoney amount) {
        return (amount.getCurrency() == from.getCurrency())
                && (amount.getCurrency() == to.getCurrency())
                && (isFromClose && from.equals(amount) || isToClose && to.equals(amount)
                || (MultiCurrencyMoneyUtil.compare(amount, from) > 0)
                && (MultiCurrencyMoneyUtil.compare(amount, to) < 0));

    }

    /**
     * 获得由区间表达式定义的区间.
     *
     * @param amountExtentExp 区间表达式
     * @param channelCurrency 渠道币种
     * @return amountExtent 区间对象
     */
    public static AmountRange getAmountExtent(String amountExtentExp, String channelCurrency) {

        //参数校验
        if (StringUtils.isBlank(amountExtentExp)) {
            return null;
        }

        //构造支付额度区间
        AmountRange amountExtent = new AmountRange();

        //去空格
        amountExtentExp = amountExtentExp.trim();

        //获取左边符号
        char beginChar = amountExtentExp.charAt(0);

        //获取右边符号
        char endChar = amountExtentExp.charAt(amountExtentExp.length() - 1);

        //设置左边表达式闭合情况
        if (beginChar == PAY_EXTENT_LEFT_CLOSE) {

            //左闭合 
            amountExtent.isFromClose = true;
        } else if (beginChar == PAY_EXTENT_LEFT_OPEN) {

            //左开 
            amountExtent.isFromClose = false;

        } else {
            return null;
        }

        //设置右边表达式闭合情况
        if (endChar == PAY_EXTENT_RIGHT_CLOSE) {

            //右闭合
            amountExtent.isToClose = true;

        } else if (endChar == PAY_EXTENT_RIGHT_OPEN) {

            //右开
            amountExtent.isToClose = false;

        } else {
            return null;
        }

        //去除区间标识
        String dataStr = amountExtentExp.substring(1, amountExtentExp.length() - 1);
        String[] datas = dataStr.split(",");

        //区间表达式有误
        if (datas.length != RANGE_VALUE) {
            return null;
        }

        try {
            //获取左数据
            amountExtent.from = new MultiCurrencyMoney(datas[0].trim(), channelCurrency);

            // + 表示正无穷大
            if (StringUtils.equalsIgnoreCase(MofaConstants.PLUS, datas[1])) {
                amountExtent.to = new MultiCurrencyMoney(-1, channelCurrency);
                amountExtent.to.setCent(Long.MAX_VALUE);
            } else {
                amountExtent.to = new MultiCurrencyMoney(datas[1].trim(), channelCurrency);
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return amountExtent;
    }

    /**
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "" + (isFromClose ? PAY_EXTENT_LEFT_CLOSE : PAY_EXTENT_LEFT_OPEN) + from.getAmount()
                + "," + (to.getCent() == Long.MAX_VALUE ? "+" : to.getAmount())
                + (isToClose ? PAY_EXTENT_RIGHT_CLOSE : PAY_EXTENT_RIGHT_OPEN)
                + to.getCurrencyCode();
    }
}