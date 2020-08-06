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

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import io.github.mofa3.lang.common.constant.MofaConstants;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * xml工具类
 * xml --&lt; Object
 * xml --&lt; Map
 * xml --&lt; String
 * xml --&lt; JSON
 * <p>
 * 处理xml需要特别注意XXE访问问题
 * 微信XXE漏洞修复推荐解决方案，禁用外部实体访问
 * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=23_5
 *</p>
 * @author lumoere
 * @version $Id: XmlUtil.java, v 0.1 2018-03-26 下午2:54 Exp $
 */
@Slf4j
@SuppressWarnings({"unused", "unchecked"})
public class XmlUtil {

    /**
     * InputStream 转 Map
     *
     * @param inputStream InputStream
     * @return Map
     */
    public static Map<String, Object> parseXml(InputStream inputStream) {
        try {
            SAXReader reader = new SAXReader();
            reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            Document document = reader.read(inputStream);
            Element root = document.getRootElement();
            return elementsToMap(root.elements());
        } catch (Exception e) {
            log.error("xml解析异常：", e);

        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                log.error("xml解析异常：", e);
            }
        }
        return null;
    }

    /**
     * xml Element转JSON
     *
     * @param root Element对象
     * @return JSO
     */
    public static Map<String, Object> parseXmlJson(Element root) {
        try {
            return elementsToMap(root.elements());
        } catch (Exception e) {
            log.error("xml解析异常: ", e);

        }
        return null;
    }

    /**
     * xml字符串转Map
     *
     * @param xml xml字符串
     * @return Map
     */
    public static Map<String, Object> parseXml(String xml) {
        try {
            SAXReader builder = new SAXReader();
            builder.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            Document document = builder.read(new StringReader(xml));
            Element root = document.getRootElement();
            return elementsToMap(root.elements());
        } catch (Exception e) {
            log.error("xml解析异常: ", e);
        }
        return null;
    }

    /**
     * map 转 xml 字符串
     *
     * @param data 数据对象
     * @return xml字符串
     */
    public static String toXml(Map<String, Object> data) {
        Document document = DocumentHelper.createDocument();
        Element nodeElement = document.addElement("xml");
        for (String key : data.keySet()) {
            Element keyElement = nodeElement.addElement(key);
            keyElement.setText(String.valueOf(data.get(key)));
        }
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            OutputFormat format = new OutputFormat("   ", true, MofaConstants.UTF_8);
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            return out.toString(MofaConstants.UTF_8);
        } catch (Exception e) {
            log.error("xml解析异常: ", e);
        }
        return null;
    }

    /**
     * json字符串 转为 xml字符串
     *
     * @param jsonString json字符串
     * @return xml字符串
     */
    public static String jsonToXml(String jsonString) {
        return toXml(JsonUtil.jsonToBean(jsonString, HashMap.class));
    }

    /**
     * elements to map
     *
     * @param elements xml elements
     * @return map
     */
    private static Map<String, Object> elementsToMap(List<Element> elements) {
        Map<String, Object> map = new HashMap<>(Math.round(elements.size()) + 1);
        for (Element e : elements) {
            if (e.isTextOnly()) {
                map.put(e.getName(), e.getText());
            } else {
                map.put(e.getName(), parseXmlJson(e));
            }
        }
        return map;
    }
}