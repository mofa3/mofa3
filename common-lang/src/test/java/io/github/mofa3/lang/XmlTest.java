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

import java.text.MessageFormat;

/**
 * TODO
 *
 * @author lumoere
 * @version $Id: XmlTest.java, v 0.1 2018-03-26 下午3:12 Exp $
 */
public class XmlTest {
    public static void main(String[] args) {
//        String xml= "<xml><return_code><![CDATA[SUCCESS]]></return_code>\n"
//                + "<return_msg><![CDATA[OK]]></return_msg>\n"
//                + "<appid><![CDATA[wxc4b2442963e663d8]]></appid>\n"
//                + "<mch_id><![CDATA[1500718511]]></mch_id>\n"
//                + "<nonce_str><![CDATA[UaSrN7kUL9yIBQyG]]></nonce_str>\n"
//                + "<sign><![CDATA[9A79C11317F557DF7128C8CEBC00683F]]></sign>\n"
//                + "<result_code><![CDATA[SUCCESS]]></result_code>\n"
//                + "<out_trade_no><![CDATA[3001201808260000000000053574]]></out_trade_no>\n"
//                + "<trade_state><![CDATA[NOTPAY]]></trade_state>\n"
//                + "<trade_state_desc><![CDATA[订单未支付]]></trade_state_desc>\n"
//                + "</xml>";
//
//        Map<String, Object> object= XmlUtil.parseXmlJson(xml);
//        System.out.println(object);
//
//        String toXml= XmlUtil.toXml(object);
//
//        System.out.println(toXml);
//
//        System.out.println(JsonUtil.beanToJson(object));


        //
        //Map<String, Object> map= new HashMap<>();
        //map.put("abc", "123");
        //map.put("amount", 10);
        //
        //String xml1= XmlUtil.toXml(map);
        //System.out.println(xml1);

        //System.out.println(SequenceUtil.getSequence(EventEnum.QUERY, 5));
        //System.out.println(RandomCodeUtil.generateTextCode(RandomCodeUtil.TYPE_ALL_MIXED, 6, null));


//        System.out.println(TimeZone.getDefault());
        String aaa= "{1,number,#} [{2,number,#}ms, {5,number,##%}] - {0}";
        String message = "消息内容";
        long startTime = 1233345L;
        long duration = 123321L;
        long durationOfSelf = 100023L;
        double percent = 1.23d;
        double percentOfAll = 2.34d;
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

        System.out.println(MessageFormat.format(aaa, params));
    }
}