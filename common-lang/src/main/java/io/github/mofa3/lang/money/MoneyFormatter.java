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

import org.apache.commons.lang3.StringUtils;
import io.github.mofa3.lang.common.constant.MofaConstants;

import java.io.Serializable;

import static org.apache.commons.lang3.StringUtils.contains;
import static org.apache.commons.lang3.StringUtils.indexOf;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.lastIndexOf;
import static org.apache.commons.lang3.StringUtils.replace;
import static org.apache.commons.lang3.StringUtils.replaceOnce;
import static org.apache.commons.lang3.StringUtils.substring;
import static org.apache.commons.lang3.StringUtils.trim;

/**
 * 多币种金额类展示转换/反转工具类。
 *
 * @author lumoere
 * @version $Id: MoneyFormatter.java, v 0.1 2018年01月05日 下午3:07 Exp $
 */
@SuppressWarnings("unused")
public class MoneyFormatter implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -1715640537604076L;

    /**
     * 千分位截取长度
     */
    private static final int THOUSANDTH_LENGTH = 3;

    /**
     * 默认小数点占位符
     */
    private static final String DEFAULT_DECIMAL_PLACES_SEP = ".";

    /**
     * 千分位符：逗号、点号、半角空格
     */
    private static final String[] THOUSANDTH_SEP = new String[]{",", ".", " "};

    /**
     * 小数位符：点号、逗号、空
     */
    private static final String[] DECIMAL_PLACES_SEP = new String[]{
            DEFAULT_DECIMAL_PLACES_SEP, ",", ""};

    // ~~~ 工具方法

    /**
     * 多币种金额<code>MultiCurrencyMoney</code>对象转换成标准显示字符串方法。
     *
     * <p>TODO!暂时只支持以下币种金额对象转换，未来币种待PD给出格式确认稿：
     * <li>new MultiCurrencyMoney(1000,CurrencyEnum.USD.getCurrencyValue())-> US $1,000.00</li>
     * <li>new MultiCurrencyMoney(1000,CurrencyEnum.RUB.getCurrencyValue())-> 10 000,00 руб.</li>
     * <li>new MultiCurrencyMoney(1000,CurrencyEnum.GBP.getCurrencyValue())-> ￡1,000.00</li>
     * <li>new MultiCurrencyMoney(1000,CurrencyEnum.BRL.getCurrencyValue())-> R$ 1.000,00</li>
     * <li>new MultiCurrencyMoney(1000,CurrencyEnum.CAD.getCurrencyValue())-> C$ 1,000.00</li>
     * <li>new MultiCurrencyMoney(1000,CurrencyEnum.AUD.getCurrencyValue())-> AU $1,000.00</li>
     * <li>new MultiCurrencyMoney(1000,CurrencyEnum.EUR.getCurrencyValue())-> € 1.000,00</li>
     * <li>new MultiCurrencyMoney(1000,CurrencyEnum.INR.getCurrencyValue())-> Rs. 1,000.00</li>
     * <li>new MultiCurrencyMoney(1000,CurrencyEnum.UAH.getCurrencyValue())-> 1 000,00 грн. </li>
     * <li>new MultiCurrencyMoney(1000,CurrencyEnum.JPY.getCurrencyValue())-> ￥ 1,000</li>
     * <li>new MultiCurrencyMoney(1000,CurrencyEnum.MXN.getCurrencyValue())-> 1,000.00 MXN$</li>
     * <li>new MultiCurrencyMoney(1000,CurrencyEnum.SGD.getCurrencyValue())-> 1,000.00 S$</li>
     * <li>new MultiCurrencyMoney(1000,CurrencyEnum.CHF.getCurrencyValue())-> 1,000.00 CHF</li>
     * <li>new MultiCurrencyMoney(1000,CurrencyEnum.PLN.getCurrencyValue())-> 1 000,00 PLN</li>
     *
     * @param money 多币种金额
     * @return 标准显示字符串
     */
    public static String format(MultiCurrencyMoney money) {

        if (null == money) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        String amount = String.valueOf(money.getAmount());
        CurrencyEnum currency = CurrencyEnum.getByCurrencyValue(money.getCurrencyValue());

        assert currency != null;
        switch (currency) {
            case CNY:
            case USD:
            case GBP:
            case AUD:
                sb.append(currency.getCurrencyLabel());
                sb.append(format(amount, THOUSANDTH_SEP[0], DECIMAL_PLACES_SEP[0]));
                break;

            case RUB:
            case UAH:
            case PLN:
                sb.append(format(amount, THOUSANDTH_SEP[2], DECIMAL_PLACES_SEP[1]));
                sb.append(" ");
                sb.append(currency.getCurrencyLabel());
                break;

            case BRL:
            case EUR:
                sb.append(currency.getCurrencyLabel());
                sb.append(" ");
                sb.append(format(amount, THOUSANDTH_SEP[1], DECIMAL_PLACES_SEP[1]));
                break;

            case CAD:
            case INR:
                sb.append(currency.getCurrencyLabel());
                sb.append(" ");
                sb.append(format(amount, THOUSANDTH_SEP[0], DECIMAL_PLACES_SEP[0]));
                break;

            case MXN:
            case SGD:
            case CHF:
                sb.append(format(amount, THOUSANDTH_SEP[0], DECIMAL_PLACES_SEP[0]));
                sb.append(" ");
                sb.append(currency.getCurrencyLabel());
                break;

            case JPY:
                sb.append(currency.getCurrencyLabel());
                sb.append(" ");
                sb.append(format(amount, THOUSANDTH_SEP[0], null));
                break;

            default:
                throw new RuntimeException("error format money:"
                        + MultiCurrencyMoneyUtil.toStr(money));
        }

        return sb.toString();
    }

    /**
     * 标准显示字符串转换成多币种金额<code>MultiCurrencyMoney</code>对象方法。
     *
     * <p>TODO!暂时只支持以下币种金额对象转换，未来币种待PD给出格式确认稿：
     *
     * <li>US $1,000.00 -> new MultiCurrencyMoney(1000,CurrencyEnum.USD.getCurrencyValue())</li>
     * <li>10 000,00 руб.-> new MultiCurrencyMoney(1000,CurrencyEnum.RUB.getCurrencyValue())</li>
     * <li>￡1,000.00-> new MultiCurrencyMoney(1000,CurrencyEnum.GBP.getCurrencyValue())</li>
     * <li>R$ 1.000,00-> new MultiCurrencyMoney(1000,CurrencyEnum.BRL.getCurrencyValue())</li>
     * <li>C$ 1,000.00-> new MultiCurrencyMoney(1000,CurrencyEnum.CAD.getCurrencyValue())</li>
     * <li>AU $1,000.00-> new MultiCurrencyMoney(1000,CurrencyEnum.AUD.getCurrencyValue())</li>
     * <li>€ 1.000,00-> new MultiCurrencyMoney(1000,CurrencyEnum.EUR.getCurrencyValue())</li>
     * <li>Rs. 1,000.00-> new MultiCurrencyMoney(1000,CurrencyEnum.INR.getCurrencyValue())</li>
     * <li>1 000,00 грн.-> new MultiCurrencyMoney(1000,CurrencyEnum.UAH.getCurrencyValue()) </li>
     * <li>￥ 1,000-> new MultiCurrencyMoney(1000,CurrencyEnum.JPY.getCurrencyValue())</li>
     * <li>1,000.00 MXN$-> new MultiCurrencyMoney(1000,CurrencyEnum.MXN.getCurrencyValue())</li>
     * <li>1,000.00 S$-> new MultiCurrencyMoney(1000,CurrencyEnum.SGD.getCurrencyValue())</li>
     * <li>1,000.00 CHF-> new MultiCurrencyMoney(1000,CurrencyEnum.CHF.getCurrencyValue())</li>
     * <li>1 000,00 PLN-> new MultiCurrencyMoney(1000,CurrencyEnum.PLN.getCurrencyValue())</li>
     *
     * @param moneyStr 标准显示字符串
     * @return 多币种金额
     */
    public static MultiCurrencyMoney convert(String moneyStr) {

        MultiCurrencyMoney money = null;

        if (isBlank(moneyStr)) {
            return null;
        }

        //解析后金额值
        String amount;

        //解释币种
        CurrencyEnum currency = resolveCurrency(moneyStr);

        if (null == currency) {
            throw new RuntimeException("error convert money[" + moneyStr + "].");
        }

        //剔除币种符字符
        moneyStr = replace(moneyStr, currency.getCurrencyLabel(), "");

        //剔除空格
        moneyStr = trim(moneyStr);

        switch (currency) {
            case CNY:
            case USD:
            case GBP:
            case CAD:
            case AUD:
            case INR:
            case MXN:
            case SGD:
            case CHF:
                amount = convert(moneyStr, THOUSANDTH_SEP[0], DECIMAL_PLACES_SEP[0]);
                break;

            case RUB:
            case UAH:
            case PLN:
                amount = convert(moneyStr, THOUSANDTH_SEP[2], DECIMAL_PLACES_SEP[1]);
                break;

            case BRL:
            case EUR:
                amount = convert(moneyStr, THOUSANDTH_SEP[1], DECIMAL_PLACES_SEP[1]);
                break;

            case JPY:
                amount = convert(moneyStr, THOUSANDTH_SEP[0], null);
                break;

            default:
                throw new RuntimeException("error convert money[" + moneyStr + "].");
        }

        //构造金额对象
        return new MultiCurrencyMoney(Double.parseDouble(amount), currency.getCurrencyValue());
    }

    /**
     * 以指定的千分位、小数点格式补全金额。
     *
     * @param amount           金额字符串
     * @param thousandthSep    千分位
     * @param decimalPlacesSep 小数点格式
     * @return 金额字符串
     */
    public static String format(String amount, String thousandthSep, String decimalPlacesSep) {

        //千分位前字符
        String thousandthPrefix = "";

        //包含千分位符字符
        StringBuilder thousandthStr = new StringBuilder();

        //包含小数位符字符
        String decimalPlaces = cutOffDecimalPlaces(amount);

        //某些币种不带小数位
        if (isBlank(decimalPlacesSep)) {
            decimalPlaces = "";
        }

        //截取整数位
        String integer = cutOffInteger(amount);

        if (integer.length() <= THOUSANDTH_LENGTH) {

            //拼接小数位
            if (isNotBlank(decimalPlaces)) {
                return integer + decimalPlacesSep + decimalPlaces;
            }

            return integer;
        }

        //按千分位长度取模
        int mod = integer.length() / THOUSANDTH_LENGTH;

        //截取第一笔千分位前字符
        if (mod > 0) {
            thousandthPrefix = substring(integer, 0, integer.length() - mod
                    * THOUSANDTH_LENGTH);
        }

        //截断第一笔千分位前字符
        integer = replaceOnce(integer, thousandthPrefix, null);

        //循环拼接千分位及分隔符
        while (mod > 0) {
            String tmp = substring(integer, integer.length() - THOUSANDTH_LENGTH,
                    integer.length());

            integer = substring(integer, 0, integer.length() - THOUSANDTH_LENGTH);

            thousandthStr.insert(0, thousandthSep + tmp);

            mod--;
        }

        //去除模尽的千分位前缀
        if (isBlank(thousandthPrefix) && isNotBlank(thousandthStr.toString())) {
            thousandthStr = new StringBuilder(replaceOnce(thousandthStr.toString(),
                    thousandthSep, ""));
        }

        //完整格式
        if (isNotBlank(decimalPlaces)) {
            return thousandthPrefix + thousandthStr + decimalPlacesSep + decimalPlaces;
        }

        return thousandthPrefix + thousandthStr;
    }

    /**
     * 工具千分位符、小数点位符解析金额字符串。
     *
     * @param moneyStr         金额字符串
     * @param thousandthSep    千分位符
     * @param decimalPlacesSep 小数点位符
     * @return 金额字符串
     */
    public static String convert(String moneyStr, String thousandthSep, String decimalPlacesSep) {

        //TODO: 如果千分位和小数点位符相同如何？

        //截断千分位符字符
        String thousandthStr;

        if (isNotBlank(decimalPlacesSep)) {

            thousandthStr = substring(moneyStr, 0,
                    moneyStr.lastIndexOf(decimalPlacesSep));

            //剔除千分位符
            thousandthStr = replace(thousandthStr, thousandthSep, "");
        } else {
            //剔除千分位符
            thousandthStr = replace(moneyStr, thousandthSep, "");
        }

        //截断小数位
        String decimalPlaces = "";

        //默认小数点占位符
        if (isNotBlank(decimalPlacesSep)) {

            decimalPlaces = substring(moneyStr, moneyStr.lastIndexOf(decimalPlacesSep),
                    moneyStr.length());

            decimalPlaces = replaceOnce(decimalPlaces, decimalPlacesSep,
                    DEFAULT_DECIMAL_PLACES_SEP);
        }

        return thousandthStr + decimalPlaces;
    }

    /**
     * 定位币种。
     *
     * @param moneyStr 金额字符串
     * @return 币种
     */
    public static CurrencyEnum resolveCurrency(String moneyStr) {

        if (contains(moneyStr, CurrencyEnum.USD.getCurrencyLabel())
                && indexOf(moneyStr, CurrencyEnum.USD.getCurrencyLabel()) ==
                lastIndexOf(moneyStr, CurrencyEnum.USD.getCurrencyLabel())) {
            return CurrencyEnum.USD;

        } else if (contains(moneyStr, CurrencyEnum.RUB.getCurrencyLabel())
                && indexOf(moneyStr, CurrencyEnum.RUB.getCurrencyLabel()) ==
                lastIndexOf(moneyStr, CurrencyEnum.RUB.getCurrencyLabel())) {
            return CurrencyEnum.RUB;

        } else if (contains(moneyStr, CurrencyEnum.GBP.getCurrencyLabel())
                && indexOf(moneyStr, CurrencyEnum.GBP.getCurrencyLabel()) ==
                lastIndexOf(moneyStr, CurrencyEnum.GBP.getCurrencyLabel())) {
            return CurrencyEnum.GBP;

        } else if (contains(moneyStr, CurrencyEnum.BRL.getCurrencyLabel())
                && indexOf(moneyStr, CurrencyEnum.BRL.getCurrencyLabel()) ==
                lastIndexOf(moneyStr, CurrencyEnum.BRL.getCurrencyLabel())) {
            return CurrencyEnum.BRL;

        } else if (contains(moneyStr, CurrencyEnum.CAD.getCurrencyLabel())
                && indexOf(moneyStr, CurrencyEnum.CAD.getCurrencyLabel()) ==
                lastIndexOf(moneyStr, CurrencyEnum.CAD.getCurrencyLabel())) {
            return CurrencyEnum.CAD;

        } else if (contains(moneyStr, CurrencyEnum.AUD.getCurrencyLabel())
                && indexOf(moneyStr, CurrencyEnum.AUD.getCurrencyLabel()) ==
                lastIndexOf(moneyStr, CurrencyEnum.AUD.getCurrencyLabel())) {
            return CurrencyEnum.AUD;

        } else if (contains(moneyStr, CurrencyEnum.EUR.getCurrencyLabel())
                && indexOf(moneyStr, CurrencyEnum.EUR.getCurrencyLabel()) ==
                lastIndexOf(moneyStr, CurrencyEnum.EUR.getCurrencyLabel())) {
            return CurrencyEnum.EUR;

        } else if (contains(moneyStr, CurrencyEnum.INR.getCurrencyLabel())
                && indexOf(moneyStr, CurrencyEnum.INR.getCurrencyLabel()) ==
                lastIndexOf(moneyStr, CurrencyEnum.INR.getCurrencyLabel())) {
            return CurrencyEnum.INR;

        } else if (contains(moneyStr, CurrencyEnum.UAH.getCurrencyLabel())
                && indexOf(moneyStr, CurrencyEnum.UAH.getCurrencyLabel()) ==
                lastIndexOf(moneyStr, CurrencyEnum.UAH.getCurrencyLabel())) {
            return CurrencyEnum.UAH;

        } else if (contains(moneyStr, CurrencyEnum.JPY.getCurrencyLabel())
                && indexOf(moneyStr, CurrencyEnum.JPY.getCurrencyLabel()) ==
                lastIndexOf(moneyStr, CurrencyEnum.JPY.getCurrencyLabel())) {
            return CurrencyEnum.JPY;

        } else if (contains(moneyStr, CurrencyEnum.MXN.getCurrencyLabel())
                && indexOf(moneyStr, CurrencyEnum.MXN.getCurrencyLabel()) ==
                lastIndexOf(moneyStr, CurrencyEnum.MXN.getCurrencyLabel())) {
            return CurrencyEnum.MXN;
        } else if (contains(moneyStr, CurrencyEnum.SGD.getCurrencyLabel())
                && indexOf(moneyStr, CurrencyEnum.SGD.getCurrencyLabel()) ==
                lastIndexOf(moneyStr, CurrencyEnum.SGD.getCurrencyLabel())) {
            return CurrencyEnum.SGD;
        } else if (contains(moneyStr, CurrencyEnum.CHF.getCurrencyLabel())
                && indexOf(moneyStr, CurrencyEnum.CHF.getCurrencyLabel()) ==
                lastIndexOf(moneyStr, CurrencyEnum.CHF.getCurrencyLabel())) {
            return CurrencyEnum.CHF;
        }

        return null;
    }

    /**
     * 截取小数点位。
     *
     * @param amount 金额字符串
     * @return 截取小数点的金额字符串
     */
    private static String cutOffDecimalPlaces(String amount) {
        return substring(amount, indexOf(amount, ".") + 1, amount.length());
    }

    /**
     * 截取整数位段。
     *
     * @param amount 金额字符串
     * @return 截取整数位的金额字符串
     */
    private static String cutOffInteger(String amount) {
        // BUGFIX 某些币种是没有小数点的（最小单元是元）
        if (StringUtils.contains(amount, MofaConstants.STOP)) {
            return substring(amount, 0, indexOf(amount, '.'));
        }

        return amount;
    }
}
