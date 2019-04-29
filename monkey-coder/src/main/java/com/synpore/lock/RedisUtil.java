package com.synpore.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

import java.util.Optional;

@Slf4j
public class RedisUtil {

    /**
     * 获取锁，isPresent()为true表明获取到锁，get()则能获取到token，用于解锁
     *
     * @param key    指定锁
     * @param expire 锁的过期时间，单位秒
     * @return 是否锁定及锁定token
     * @see Optional#isPresent()
     * @see Optional#get()
     */
    public static Optional<String> tryLock(final RedisTemplate redisTemplate, String key, long expire) {
        String token = String.valueOf(System.nanoTime());
        RedisConnection redisConnection = null;
        try {
            redisConnection = redisTemplate.getConnectionFactory().getConnection();
            Jedis jedis = (Jedis) redisConnection.getNativeConnection();
            boolean locked = "OK".equals(jedis.set(key, token, "NX", "EX", expire));
            if (locked) {
                return Optional.of(token);
            }

        } catch (Exception e) {
            log.error("error when execute redis command, key={}", key, e);
            throw new RuntimeException(e);
        } finally {
            if (redisConnection != null) {
                redisConnection.close();
            }
        }
        return Optional.empty();
    }

    private static String UNLOCK_SCRIPT = "local token = redis.call('get',KEYS[1]) if token == ARGV[1] then return redis.call('del',KEYS[1]) elseif token == false then return 1 else return 0 end";

    /**
     * 解除指定的锁定
     *
     * @param key   指定锁
     * @param token 锁定的token
     * @return
     */
    public static boolean unlock(final RedisTemplate redisTemplate, String key, String token) {
        RedisConnection redisConnection = null;
        try {
            redisConnection = redisTemplate.getConnectionFactory().getConnection();
            Jedis jedis = (Jedis) redisConnection.getNativeConnection();
            Long num = (Long) jedis.eval(UNLOCK_SCRIPT, 1, key, token);
            return num > 0;
        } catch (Exception e) {
            log.error("error when execute redis command, key={}", key, e);
            throw new RuntimeException(e);
        } finally {
            if (redisConnection != null) {
                redisConnection.close();
            }
        }
    }
}
