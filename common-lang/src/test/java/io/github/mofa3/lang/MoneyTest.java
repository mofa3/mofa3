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

import java.math.BigDecimal;

import io.github.mofa3.lang.money.MultiCurrencyMoney;

/**
 * TODO
 *
 * @author lumoere
 * @version $Id: MoneyTest.java, v 0.1 2018-05-28 下午7:59 Exp $
 */
public class MoneyTest {
    public static void main(String[] args) {
        MultiCurrencyMoney money = new MultiCurrencyMoney("100000");
        BigDecimal lv = new BigDecimal("0.051");
        BigDecimal day = new BigDecimal("178");
        BigDecimal year = new BigDecimal("365");
        // 实际收益 = 本金×年化收益率×投资天数 / 365
        MultiCurrencyMoney all = money.multiply(lv).multiply(day).divide(year);
        System.out.println(all.getAmount().doubleValue());


        BigDecimal mon = new BigDecimal("100000").multiply(lv).multiply(day);
        BigDecimal res = mon.divide(year, 6, BigDecimal.ROUND_HALF_UP);
        System.out.println(res.doubleValue());
    }
}