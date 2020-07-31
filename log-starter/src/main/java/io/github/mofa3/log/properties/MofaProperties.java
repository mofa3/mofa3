package io.github.mofa3.log.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * mofa YML配置
 *
 * @author ${baizhang}
 * @version $Id: MofaProperties.java, v 0.1 2018-05-04 上午9:58 Exp $
 */
@Data
@ConfigurationProperties(prefix = MofaProperties.MOFA_LOG_PREFIX)
public class MofaProperties {
    public static final String MOFA_LOG_PREFIX = "mofa";

    /**
     * controller 耗时监控阈值
     */
    private Long profilerTime;
}
