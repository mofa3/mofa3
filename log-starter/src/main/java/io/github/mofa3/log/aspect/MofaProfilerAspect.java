package io.github.mofa3.log.aspect;

import io.github.mofa3.log.properties.MofaProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import io.github.mofa3.lang.common.constant.HttpConstants;
import io.github.mofa3.lang.util.DateUtil;
import io.github.mofa3.lang.util.Profiler;
import io.github.mofa3.lang.util.RandomCodeUtil;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 性能日志Profiler Aspect
 *
 * @author lumoere
 * @version $Id: MofaProfilerAspect.java, v 0.1 2018-04-23 下午9:33 Exp $
 */
@Aspect
@Component
@EnableConfigurationProperties(value = MofaProperties.class)
@ImportResource("classpath:mofa-context.xml")
@Slf4j
public class MofaProfilerAspect {
    private static final Logger MOFA_PREF = LoggerFactory.getLogger("MOFA_PROFILER_DIGEST");
    /**
     * 性能监控默认阈值
     */
    private static final Integer DEFAULT_PROFILER_THRESHOLD = 50;
    private final MofaProperties mofaProperties;

    public MofaProfilerAspect(ObjectProvider<MofaProperties> mofaPropertiesProvider) {
        this.mofaProperties = mofaPropertiesProvider.getIfUnique();
    }

    @Pointcut("execution(* so.dian..*ControllerInit.*(..))")
    private void aopProfiler() {
    }

    @Around("aopProfiler()")
    public Object aroundProfiler(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestId= request.getHeader(HttpConstants.REQUEST_ID);
        if(StringUtils.isNotBlank(requestId)){
            MDC.put(HttpConstants.REQUEST_ID, requestId);
        }
        if (StringUtils.isBlank(MDC.get(HttpConstants.REQUEST_ID))) {
            MDC.put(HttpConstants.REQUEST_ID, RandomCodeUtil.generateTextCode(RandomCodeUtil.TYPE_NUM_LOWER, 16, null));
        }
        // 开始时间
        long startTime = DateUtil.timeStampMilli();
        // 方法名称
        final Signature signature = point.getSignature();
        final MethodSignature methodSignature = (MethodSignature) signature;
        final Method targetMethod = methodSignature.getMethod();
        final String methodName = targetMethod.getName();

        // 拦截方法的执行结果
        Object result = null;
        try {
            // 性能日志开始计时
            Profiler.start("URI: "+request.getRequestURI()+" Method: " + methodName);
            result = point.proceed();
            // 释放start的栈
            Profiler.release();
            return result;
        } catch (Throwable e) {
            log.error("MOFA-PROFILER[" + methodName + "]拦截打印系统服务摘要日志出错", e);
            throw e;
        } finally {
            // 方法执行时间
            long expendTime = DateUtil.timeStampMilli() - startTime;
            if (expendTime > (null == mofaProperties.getProfilerTime() ? DEFAULT_PROFILER_THRESHOLD
                    : mofaProperties.getProfilerTime())) {
                MOFA_PREF.info(Profiler.dump());
            }
            // 性能日志线程变量清空
            Profiler.reset();
            MDC.clear();
        }
    }
}
