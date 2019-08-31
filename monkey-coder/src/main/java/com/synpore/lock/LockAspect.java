package com.synpore.lock;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Optional;

@Component
@Aspect
@Order(0)//注意order优先级要比TransactionInterceptor高//
public class LockAspect {
    private static Logger logger = LoggerFactory.getLogger(LockAspect.class);

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 分布式锁切点
     */
    @Pointcut("(execution(* com.synpore.lock.DistributedLockDemon(..))) && "
            + "@annotation(com.synpore.lock.DistributeLock)")
    public void lockPointCut() {
    }

    @Around(value = "lockPointCut()")
    public Object lock(ProceedingJoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        DistributeLock distributeLock = method.getAnnotation(DistributeLock.class);
        String expression = distributeLock.key();
        Object[] args = joinPoint.getArgs();
        String value = SpELUtils.parseExpression(expression, method, args, String.class);
        value = StringUtils.isBlank(value) ? System.currentTimeMillis() + "ms" : value;
        String key = StringUtils.join(distributeLock.pre(), "_", value);
        long expire = distributeLock.expire();
        Optional<String> op = Optional.empty();
        try {
            op = RedisUtil.tryLock(redisTemplate, key, expire);
        } catch (Exception e1) {
            e1.printStackTrace();
            logger.error("获取分布式锁失败，key={}", key,e1);
            throw new RuntimeException("");
        }
        String token = null;
        if (!op.isPresent()) {
            logger.info("获取分布式锁失败，key={}", key);
            throw new RuntimeException("");
        } else {
            token = op.get();
            try {
                return joinPoint.proceed();
            } catch (RuntimeException e) {
                logger.info("业务异常error..", e);
                throw e;
            } catch (Throwable t) {
                logger.info("分布式锁，处理业务异常，key={},Throwable={}", key, t);
                throw new RuntimeException("");
            } finally {
                RedisUtil.unlock(redisTemplate, key, token);
            }
        }
    }
}
