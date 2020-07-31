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
package io.github.mofa3.cache.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RScript;
import org.redisson.api.RScript.Mode;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import io.github.mofa3.cache.service.SerialNumService;
import io.github.mofa3.lang.common.constant.MofaConstants;
import io.github.mofa3.lang.exception.BizProcessException;
import io.github.mofa3.lang.exception.SystemRunningException;
import io.github.mofa3.lang.util.DateBuild;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 流水号序号生成Service
 *
 * @author ${baizhang}
 * @version $Id: SerialNumServiceImpl.java, v 0.1 2018-05-31 下午4:28 Exp $
 */
@Slf4j
@Service
@SuppressWarnings("unused")
public class SerialNumServiceImpl implements SerialNumService {

    /**
     * 默认Lua脚本SHA
     */
    private String defaultSha;

    /**
     * 批量Lua脚本SHA
     */
    private String batchSha;

    /**
     * 单条序列号生成lua脚本 SHA 缓存key
     */
    private static final String CACHE_IDGENERATOR_DEFAULT_SHA = "__CACHE_IDGENERATOR_DEFAULT_SHA_1_0_10";

    /**
     * 批量序列号生成lua脚本 SHA 缓存key
     */
    private static final String CACHE_IDGENERATOR_BATCH_SHA = "__CACHE_IDGENERATOR_BATCH_SHA_1_0_10";

    /**
     * 缓存slot key标识
     */
     private static final String  SERIAL_CACHE_PREFIX= "{__CACHE_IDGENERATOR_}";

    /**
     * 默认，seq 部分长度 7位
     */
    private static final String DEFAULT_LEN = "%07d";

    /**
     * 短位，seq 部分长度 5位
     */
    private static final String SHORT_LEN = "%05d";

    /**
     * 元素起始值
     */
    private static final String START_INDEX = "0";

    /**
     * 单次批量获取最大数量
     */
    private static final Integer MAX_BATCH_NUM = 1000;

    private final RedissonClient redissonClient;

    public SerialNumServiceImpl(ObjectProvider<RedissonClient> redissonClientProvider) {
        this.redissonClient = redissonClientProvider.getIfUnique();
    }

    @Override
    public String getSerialNum(final String bizPrefix) {
        Assert.notNull(bizPrefix, "业务前缀代码不能为空");
        String result;
        try {
            result = getNextSerial(bizPrefix, DEFAULT_LEN);
        } catch (Exception e) {
            log.error("【获取流水号】DEFAULT_LEN getSerialNum Exception：", e);
            throw new SystemRunningException("流水号获取异常", e);
        }
        return result;

    }

    @Override
    public List<String> listSerialNum(final String bizPrefix, final int quantity) {
        Assert.notNull(bizPrefix, "业务前缀代码不能为空");
        if (quantity <= 1 || quantity > MAX_BATCH_NUM) {
            throw new BizProcessException("批量获取数量在2-1000条之间");
        }
        List<String> result;
        try {
            result = getBatchSerial(bizPrefix, DEFAULT_LEN, quantity);
        } catch (Exception e) {
            log.error("【批量获取流水号】DEFAULT_LEN listSerialNum Exception：", e);
            throw new SystemRunningException("流水号批量获取异常", e);
        }
        return result;
    }

    @Override
    public String getShortSerialNum(final String bizPrefix) {
        Assert.notNull(bizPrefix, "业务前缀代码不能为空");
        String result;
        try {
            result = getNextSerial(bizPrefix, SHORT_LEN);
        } catch (Exception e) {
            log.error("【获取流水号】SHORT_LEN getShortSerialNum Exception：", e);
            throw new SystemRunningException("短位流水号获取异常", e);
        }
        return result;
    }

    @Override
    public List<String> listShortSerialNum(final String bizPrefix, final int quantity) {
        Assert.notNull(bizPrefix, "业务前缀代码不能为空");
        if (quantity <= 1 || quantity > MAX_BATCH_NUM) {
            throw new BizProcessException("批量获取数量在2-1000条之间");
        }
        List<String> result;
        try {
            result = getBatchSerial(bizPrefix, SHORT_LEN, quantity);
        } catch (Exception e) {
            log.error("【批量获取水流号】SHORT_LEN listShortSerialNum Exception：", e);
            throw new SystemRunningException("短位流水号批量获取异常", e);
        }
        return result;
    }

    @Override
    public void loadLuaScript() {
        log.info("Lua script init....");
        try {
            String[] script = {"redis-serial-batch-script.lua", "redis-serial-default-script.lua"};
            for (String file : script) {
                InputStream is = SerialNumServiceImpl.class.getResourceAsStream("/script/" + file);
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String readLine;
                StringBuilder sb = new StringBuilder();
                while ((readLine = br.readLine()) != null) {
                    sb.append(readLine).append("\r\n");
                }
                br.close();
                is.close();

                RScript rScript = redissonClient.getScript();
                if (StringUtils.contains(file, "default")) {
                    String sha = rScript.scriptLoad(sb.toString());
                    this.defaultSha = sha;
                    RBucket<String> bucket = redissonClient.getBucket(CACHE_IDGENERATOR_DEFAULT_SHA);
                    bucket.set(sha);
                }
                if (StringUtils.contains(file, "batch")) {
                    String sha = rScript.scriptLoad(sb.toString());
                    this.batchSha = sha;
                    RBucket<String> bucket = redissonClient.getBucket(CACHE_IDGENERATOR_BATCH_SHA);
                    bucket.set(sha);
                }
            }
        } catch (Exception e) {
            log.error("【加载Lua脚本】解析异常：", e);
            e.printStackTrace();
        }
    }

    private String getNextSerial(String bizPrefix, String seqLen) {
        checkAndReloadScript();
        String dateTime = DateBuild.SIMPLE_SHORT_DATE.format(LocalDateTime.now());
        String slotKey = SERIAL_CACHE_PREFIX + bizPrefix + seqLen;
        return redissonClient.getScript(new StringCodec()).evalSha(Mode.READ_WRITE, getDefaultSha(),
                RScript.ReturnType.VALUE,
                Arrays.asList(slotKey + "_DATETIME", slotKey + "_SEQ"),
                bizPrefix, dateTime, START_INDEX, seqLen);
    }

    private List<String> getBatchSerial(String bizPrefix, String seqLen, int quantity) {
        checkAndReloadScript();
        String dateTime = DateBuild.SIMPLE_SHORT_DATE.format(LocalDateTime.now());
        String slotKey= SERIAL_CACHE_PREFIX + bizPrefix + seqLen;
        String result = redissonClient
                .getScript(new StringCodec()).evalSha(Mode.READ_WRITE, getBatchSha(),
                        RScript.ReturnType.VALUE,
                        Arrays.asList(slotKey+"_DATETIME",slotKey+"_SEQ"),
                        bizPrefix, dateTime, START_INDEX, seqLen, String.valueOf(quantity));
        return Arrays.asList(result.split(MofaConstants.COMMA));
    }

    /**
     * 获取缓存中default lua SHA 值
     *
     * @return defaultSha
     */
    private String getDefaultSha() {
        if (StringUtils.isBlank(defaultSha)) {
            log.info("缓存获取defaultSha");
            RBucket<String> bucket = redissonClient.getBucket(CACHE_IDGENERATOR_DEFAULT_SHA);
            defaultSha = bucket.get();
        }
        return defaultSha;
    }

    /**
     * 获取缓存中batchSha lua SHA 值
     *
     * @return batchSha
     */
    private String getBatchSha() {
        if (StringUtils.isBlank(batchSha)) {
            log.info("缓存获取batchSha");
            RBucket<String> bucket = redissonClient.getBucket(CACHE_IDGENERATOR_BATCH_SHA);
            batchSha = bucket.get();
        }
        return batchSha;
    }

    /**
     * 检查并重新载入脚本，如果redis 执行了 FLUSH，命令不能正常执行
     * 检查脚本是否存在，不存在重新载入，FLUSH会清除所有脚本，所以只需要检查一个即可确认是否被清除
     */
    private void checkAndReloadScript() {
        RBucket<String> bucket = redissonClient.getBucket(CACHE_IDGENERATOR_DEFAULT_SHA);
        RScript rScript = redissonClient.getScript();
        boolean checkResult = rScript.scriptExists(bucket.get()).get(0);
        if (!checkResult) {
            loadLuaScript();
        }
    }
}