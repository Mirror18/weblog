package com.mirror.weblog.common.aspect;

import com.mirror.weblog.common.utils.JsonUtil;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author mirror
 */
@Aspect
@Component
@Slf4j
public class ApiOperationLogAspect {
    /**
     * 以自定义 @ApiOperationLog 注解为切点
     * 凡是添加 @ApiOperationLog 的方法
     * 都会执行环绕中的代码
     */
    @Pointcut("@annotation(com.mirror.weblog.common.aspect.ApiOperationLog)")
    public void apiOperationLog() {
    }

    /**
     * 环绕
     *
     * @param joinPoint 织入点的方法
     * @return Object 织入点方法的结果
     * @throws Throwable
     */
    @Around("apiOperationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        //请求开始时间
        long startTime = System.currentTimeMillis();
        //MDC 用于存储信息的，可以理解为thread local
        MDC.put("traceId", UUID.randomUUID().toString());
        //获取被请求的类和方法
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        //请求入参
        Object[] args = joinPoint.getArgs();
        //入参转JSON字符串
        String argsJsonStr = Arrays.stream(args).map(toJsonStr()).collect(Collectors.joining(","));

        // 功能描述信息
        String description = getApiOperationLogDescription(joinPoint);

        // 打印请求相关参数
        log.info("====== 请求开始: [{}], 入参: {}, 请求类: {}, 请求方法: {} =================================== ",
                description, argsJsonStr, className, methodName);
        Object result = null;
        try {
            //执行切点方法
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("====== 请求异常: [{}], 错误信息: {} =================================== ",
                    description, throwable.getMessage());
            // 重新抛出异常
            throw throwable;
        } finally {
            //执行耗时
            long executionTime = System.currentTimeMillis() - startTime;
            // 打印出参等相关信息
            log.info("====== 请求结束: [{}], 耗时: {}ms, 出参: {} =================================== ",
                    description, executionTime, JsonUtil.toJsonString(result));

            MDC.clear();
        }
        return result;
    }

    /**
     * 获取注解的描述信息
     *
     * @param joinPoint
     * @return
     */
    private String getApiOperationLogDescription(ProceedingJoinPoint joinPoint) {
        //从proceedingJoinPoint 获取 MethodSignature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature(); // 修改这里
        //使用 MethodSignature 获取当前被注解的 Method
        Method method = signature.getMethod();
        //从 Method 中提取 LogExecution 注解
        ApiOperationLog annotation = null;
        if (method != null) {
            annotation = method.getAnnotation(ApiOperationLog.class);
        }
        //从 LogExecution 注解中获取 description 属性
        if (annotation != null) {
            return annotation.description();
        }
        return "null";
    }

    /**
     * 转 Json 字符串
     *
     * @return
     */
    private Function<Object, String> toJsonStr() {
        return JsonUtil::toJsonString;
    }
}
