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
package io.github.mofa3.client.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import io.github.mofa3.lang.exception.ThirdPartyException;
import io.github.mofa3.lang.common.constant.MofaConstants;
import io.github.mofa3.lang.common.constant.HttpConstants;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * HttpClient操作工具类
 *
 * @author ${baizhang}
 * @version $Id: HttpBuilder.java, v 0.1 2018-04-19 下午5:55 Exp $
 */
@Slf4j
@SuppressWarnings("unused")
public class Utils {

    /**
     * 字符串
     */
    public static final String ENTITY_STRING = "$ENTITY_STRING$";

    /**
     * json字符串
     */
    public static final String ENTITY_JSON = "$ENTITY_JSON$";

    /**
     * 文件
     */
    public static final String ENTITY_FILE = "$ENTITY_FILEE$";

    /**
     * byte字节
     */
    public static final String ENTITY_BYTES = "$ENTITY_BYTES$";

    /**
     * multipart，多文件
     */
    public static final String ENTITY_MULTIPART = "$ENTITY_MULTIPART$";

    /**
     * inputstream，暂无实现
     */
    public static final String ENTITY_INPUTSTREAM = "$ENTITY_INPUTSTREAM$";

    /**
     * serializable，暂无实现
     */
    public static final String ENTITY_SERIALIZABLE = "$ENTITY_SERIALIZABLE$";

    private static final List<String> SPECIAL_ENTITIY = Arrays.asList(ENTITY_STRING, ENTITY_JSON,
            ENTITY_BYTES, ENTITY_FILE, ENTITY_INPUTSTREAM, ENTITY_SERIALIZABLE, ENTITY_MULTIPART);


    /**
     * 检测url是否含有参数，如果有，则把参数加到参数列表中
     *
     * @param url  资源地址
     * @param nvps 参数列表
     * @return 返回去掉参数的url
     * @throws UnsupportedEncodingException Exception
     */
    public static String checkHasParas(String url, List<NameValuePair> nvps, String encoding)
            throws UnsupportedEncodingException {
        // 检测url中是否存在参数
        if (url.contains(MofaConstants.UNSOLVED)
                && url.indexOf(MofaConstants.UNSOLVED) < url.indexOf(MofaConstants.EQUAL)) {
            Map<String, Object> map = buildParas(url.substring(url
                    .indexOf(MofaConstants.UNSOLVED) + 1));
            map2HttpEntity(nvps, map, encoding);
            url = url.substring(0, url.indexOf(MofaConstants.UNSOLVED));
        }
        return url;
    }

    /**
     * 参数转换，将map中的参数，转到参数列表中
     *
     * @param nvps 参数列表
     * @param map  参数列表（map）
     * @throws UnsupportedEncodingException Exception
     */
    public static HttpEntity map2HttpEntity(List<NameValuePair> nvps, Map<String, Object> map,
                                            String encoding) throws UnsupportedEncodingException {
        HttpEntity entity = null;
        if (map != null && map.size() > 0) {
            boolean isSpecial = false;
            // 拼接参数
            for (Entry<String, Object> entry : map.entrySet()) {
                // 判断是否在之中
                if (SPECIAL_ENTITIY.contains(entry.getKey())) {
                    isSpecial = true;
                    switch (entry.getKey()) {
                        case ENTITY_STRING:
                            entity = getStringEntity(entry, encoding);
                            break;
                        case ENTITY_JSON:
                            entity = getJsonEntity(entry, encoding);
                            break;
                        case ENTITY_BYTES:
                            entity = getBytesEntity(entry);
                            break;
                        case ENTITY_FILE:
                            entity = getFileEntity(entry, MofaConstants.UTF_8);
                            break;
                        case ENTITY_MULTIPART:
                            entity = getMultipartFileEntity(entry, map, encoding);
                            break;
                        default:
                            nvps.add(new BasicNameValuePair(entry.getKey(),
                                    String.valueOf(entry.getValue())));
                            break;
                    }
                } else {
                    nvps.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
                }
            }
            if (!isSpecial) {
                entity = new UrlEncodedFormEntity(nvps, encoding);
            }
        }
        return entity;
    }

    /**
     * 字符串 请求参数
     *
     * @param entry    请求参数对象
     * @param encoding 请求编码
     * @return HttpEntity
     */
    private static HttpEntity getStringEntity(Entry<String, Object> entry, String encoding) {
        return new StringEntity(String.valueOf(entry.getValue()), encoding);
    }

    /**
     * json 请求参数
     *
     * @param entry    请求参数对象
     * @param encoding 请求编码
     * @return HttpEntity
     */
    private static HttpEntity getJsonEntity(Entry<String, Object> entry, String encoding) {
        HttpEntity entity = new StringEntity(String.valueOf(entry.getValue()), encoding);
        String contentType = HttpConstants.APP_FORM_JSON;
        if (encoding != null) {
            contentType += ";charset=" + encoding;
        }
        ((StringEntity) entity).setContentType(contentType);
        return entity;
    }

    /**
     * bytes 请求参数
     *
     * @param entry 请求参数对象
     * @return HttpEntity
     */
    private static HttpEntity getBytesEntity(Entry<String, Object> entry) {
        return new ByteArrayEntity((byte[]) entry.getValue());
    }

    /**
     * 单文件 请求参数
     *
     * @param entry    请求参数对象
     * @param encoding 请求编码
     * @return HttpEntity
     */
    private static HttpEntity getFileEntity(Entry<String, Object> entry, String encoding) {
        if (File.class.isAssignableFrom(entry.getValue().getClass())) {
            return new FileEntity((File) entry.getValue(), ContentType.APPLICATION_OCTET_STREAM);
        } else if (entry.getValue().getClass() == String.class) {
            return new FileEntity(new File((String) entry.getValue()),
                    ContentType.create(HttpConstants.TEXT_PLAIN, encoding));
        } else {
            return null;
        }
    }

    /**
     * 多文件 请求参数
     * <p>
     * removeContentTypeCharset 有并发安全问题，需要改进
     *
     * @param entry    请求参数对象
     * @param map      请求参数对象
     * @param encoding 请求编码
     * @return HttpEntity
     */
    private static HttpEntity getMultipartFileEntity(Entry<String, Object> entry,
                                                     Map<String, Object> map, String encoding) {
        HttpEntity entity;
        File[] files = null;
        if (File.class.isAssignableFrom(entry.getValue().getClass().getComponentType())) {
            files = (File[]) entry.getValue();
        } else if (entry.getValue().getClass().getComponentType() == String.class) {
            String[] names = (String[]) entry.getValue();
            files = new File[names.length];
            for (int i = 0, length = names.length; i < length; i++) {
                files[i] = new File(names[i]);
            }
        }
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        // 设置请求的编码格式
        builder.setCharset(Charset.forName(encoding));
        // 设置浏览器兼容模式
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        int count = 0;
        for (File file : files) {
            // 把文件转换成流对象FileBody
            builder.addBinaryBody(String.valueOf(map.get(ENTITY_MULTIPART + ".name"))
                    + count++, file);
        }
        boolean forceRemoveContentTypeCharset = (Boolean) map.get(ENTITY_MULTIPART + ".rmCharset");
        Map<String, Object> m = new HashMap<>(16);
        m.putAll(map);
        m.remove(ENTITY_MULTIPART);
        m.remove(ENTITY_MULTIPART + ".name");
        m.remove(ENTITY_MULTIPART + ".rmCharset");
        // 发送的数据
        for (final Entry<String, Object> e : m.entrySet()) {
            builder.addTextBody(e.getKey(), String.valueOf(e.getValue()),
                    ContentType.create(HttpConstants.TEXT_PLAIN, encoding));
        }
        // 生成 HTTP POST 请求实体
        entity = builder.build();

        // 强制去除contentType中的编码设置，否则，在某些情况下会导致上传失败
        if (forceRemoveContentTypeCharset) {
            removeContentTypeCharset(encoding, entity);
        }
        return entity;
    }

    /**
     * 移除contentType的charset
     *
     * @param encoding 编码
     * @param entity   实体
     */
    private static void removeContentTypeCharset(String encoding, HttpEntity entity) {
        try {
            Class<?> clazz = entity.getClass();
            Field field = clazz.getDeclaredField("contentType");
            // 将字段的访问权限设为true：即去除private修饰符的影响
            field.setAccessible(true);
            if (Modifier.isFinal(field.getModifiers())) {
                // 去除final修饰符的影响，将字段设为可修改的
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            }
            BasicHeader o = (BasicHeader) field.get(entity);
            field.set(entity, new BasicHeader(HTTP.CONTENT_TYPE, o.getValue().replace("; charset=" + encoding, "")));

        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
            throw new ThirdPartyException(e.getMessage(), e);
        }
    }

    /**
     * 生成参数
     * 参数格式化：k1=v1&k2=v2
     *
     * @param paras 参数列表
     * @return 返回参数列表（map）
     */
    public static Map<String, Object> buildParas(String paras) {
        String[] p = paras.split(MofaConstants.AND);
        String[][] ps = new String[p.length][2];
        int pos = 0;
        for (int i = 0, length = p.length; i < length; i++) {
            pos = p[i].indexOf(MofaConstants.EQUAL);
            ps[i][0] = p[i].substring(0, pos);
            ps[i][1] = p[i].substring(pos + 1);
            pos = 0;
        }
        return buildParas(ps);
    }

    /**
     * 生成参数
     * 参数类型：{{"k1","v1"},{"k2","v2"}}
     *
     * @param paras 参数列表
     * @return 返回参数列表（map）
     */
    public static Map<String, Object> buildParas(String[][] paras) {
        // 创建参数队列
        Map<String, Object> map = new HashMap<>(paras.length + 2);
        for (String[] para : paras) {
            map.put(para[0], para[1]);
        }
        return map;
    }

}
