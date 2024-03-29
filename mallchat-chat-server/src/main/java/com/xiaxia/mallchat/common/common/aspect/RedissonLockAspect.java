package com.xiaxia.mallchat.common.common.aspect;

import cn.hutool.core.util.StrUtil;
import com.xiaxia.mallchat.common.common.annotation.RedissonLock;
import com.xiaxia.mallchat.common.common.service.LockService;
import com.xiaxia.mallchat.common.common.util.SpElUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author xfl
 * @date 2024/2/5 14:09
 * Description: 分布式锁切面
 * 确保比事务注解先执行，分布式锁在事务外
 */
@Slf4j
@Aspect
@Component
@Order(0)//
public class RedissonLockAspect {
    @Autowired
    private LockService lockService;

    @Around("@annotation(com.xiaxia.mallchat.common.common.annotation.RedissonLock)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        RedissonLock redissonLock = method.getAnnotation(RedissonLock.class);
        //默认方法限定名+注解排名（可能多个）
        String prefix = StrUtil.isBlank(redissonLock.prefixKey()) ? SpElUtils.getMethodKey(method) : redissonLock.prefixKey();
        String key = SpElUtils.parseSpEl(method, joinPoint.getArgs(), redissonLock.key());
        return lockService.executeWithLockThrows(prefix + ":" + key, redissonLock.waitTime(), redissonLock.unit(), joinPoint::proceed);
    }
}
