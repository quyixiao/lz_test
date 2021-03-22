package com.admin.crawler.utils;

import com.admin.crawler.constant.CacheConstants;
import com.fasterxml.jackson.databind.JavaType;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * @author yuzhong
 * @date 2017-12-20 11:07 AM
 */
@Slf4j
@Component("redisCacheUtil")
public class RedisCacheUtil {

    protected static Logger logger = LoggerFactory.getLogger(RedisCacheUtil.class);

    public static boolean RISK_CACHE_SWITCH = true;//风控缓存开关，true：打开（即使用缓存）  false：关闭（即不使用缓存）

    private static final String KEY_SEPARATOR = "_";

    final Random random = new Random();


    enum KeyPre {

        TABLE_KEY("table_"),
        TARGET_LT_KEY("target_lt_");

        private String key;

        KeyPre(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }

    }


    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;


    /**
     * 保存到缓存，过期时间为默认过期时间
     *
     * @param key 缓存key
     * @param obj 缓存数据
     */
    public void saveObject(final String key, final Object obj) {
        this.saveObject(key, obj, CacheConstants.DEFAULT_EXPIRE_SECOND);
    }


    /**
     * 保存到缓存，并设定过期时间
     *
     * @param key    缓存key
     * @param obj    缓存数据
     * @param expire 过期时间
     */
    public void saveObject(final String key, final Object obj, final long expire) {
        if (!RISK_CACHE_SWITCH || StringUtil.isBlank(key) || obj == null) {
            return;
        }
        try {
            redisTemplate.opsForValue().set(key, obj, expire, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("saveObject", e);
        }
    }

    /**
     * 保存到缓存，并设定过期时间
     *
     * @param key    缓存key
     * @param
     * @param expire 过期时间
     */
    public void saveStr(final String key, final String str, final long expire) {
        if (!RISK_CACHE_SWITCH || StringUtil.isBlank(key) || str == null) {
            return;
        }
        try {
            redisTemplate.opsForValue().set(key, str, expire, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("saveObject", e);
        }
    }


    /**
     * 调用set后的返回值
     */
    public static final String OK = "OK";


    /**
     * 重试 获取锁
     *
     * @param key
     * @param lockTime ，获得锁以后的锁定时间
     * @return
     */
    public boolean lockTryTimes(final String key, final Long tryTimes, final Long lockTime) {
        final String value = "xx";
        boolean flag = false;
        try {
            // 请求锁超时时间，毫秒
            long timeout = tryTimes * 1000;
            // 系统当前时间，毫秒
            long nowTime = System.currentTimeMillis();
            while ((System.currentTimeMillis() - nowTime) < timeout) {
                if (set(key, value)) {
                    flag = true;
                    break;
                }
                LoggerUtils.info("redis lockTryTimes key=" + key + ",wait time =" + (System.currentTimeMillis() - nowTime), 1);
                // 每次请求等待一段时间
                sleep(100, 500);
            }
            if (flag) {
                redisTemplate.execute(new RedisCallback<Object>() {
                    @Override
                    public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                        return connection.expire(redisTemplate.getStringSerializer().serialize(key), lockTime);
                    }
                });
            }
        } catch (Exception e) {
            logger.error("getLock error", e);
        }
        return flag;
    }

    public static void sleep(int min, int max) {
        try {
            Random random = new Random();
            int time = random.nextInt(max) % (max - min + 1) + min;
            logger.info(" sleep time is : " + time);
            int divTime = time / 2000;
            int modTime = time % 2000;
            if (divTime > 0) {
                for (int i = 0; i < divTime; i++) {
                    TimeUnit.MILLISECONDS.sleep(2000);
                    logger.info("sleep....");
                }
                TimeUnit.MILLISECONDS.sleep(modTime);
            } else {
                TimeUnit.MILLISECONDS.sleep(time);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        long a = System.currentTimeMillis();
        System.out.println(a);
    }

    /**
     * @param millis 毫秒
     * @param nanos  纳秒
     * @Title: seleep
     * @Description: 线程等待时间
     * @author yuhao.wang
     */
    private void seleep(long millis, int nanos) {
        try {
            Thread.sleep(millis, random.nextInt(nanos));
        } catch (InterruptedException e) {
            logger.info("获取分布式锁休眠被中断：", e);
        }
    }


    /**
     * 立即获取锁
     *
     * @param key
     * @param lockTime ，获得锁以后的锁定时间
     * @return
     */
    public boolean lock(final String key, final Long lockTime) {
        final String value = "xx";
        try {
            Boolean flag = set(key, value);
            if (flag) {
                redisTemplate.execute(new RedisCallback<Object>() {
                    @Override
                    public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                        return connection.expire(redisTemplate.getStringSerializer().serialize(key), lockTime);
                    }
                });
            }
            return flag;
        } catch (Exception e) {
            logger.error("getLock error", e);
            return false;
        }
    }


    private Boolean set(String key, String value) {
        return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.setNX(redisTemplate.getStringSerializer().serialize(key), value.getBytes());
            }
        });
    }

    /**
     * 锁住某个key值30S，需要解锁时删除即可
     *
     * @param key
     * @param key
     * @return
     */
    public boolean lock30Second(final String key) {
        final String value = "xx";
        try {
            Boolean flag = set(key, value);
            if (flag) {
                redisTemplate.execute(new RedisCallback<Object>() {
                    @Override
                    public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                        return connection.expire(redisTemplate.getStringSerializer().serialize(key), CacheConstants.SECOND_OF_THREE);
                    }
                });
            }
            return flag;
        } catch (Exception e) {
            logger.error("getLock error", e);
            return false;
        }
    }


    /**
     * 锁住某个key值30S，需要解锁时删除即可
     *
     * @param key
     * @param key
     * @return
     */
    public boolean lock180Second(final String key) {
        final String value = "xx";
        try {
            Boolean flag = set(key, value);
            if (flag) {
                redisTemplate.execute(new RedisCallback<Object>() {
                    @Override
                    public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                        return connection.expire(redisTemplate.getStringSerializer().serialize(key), CacheConstants.MINITS_OF_THREE);
                    }
                });
            }
            return flag;
        } catch (Exception e) {
            logger.error("getLock error", e);
            return false;
        }
    }


    /**
     * 锁住某个key值，需要解锁时删除即可
     *
     * @param key
     * @param value
     * @return
     */
    public boolean getLock(final String key, final String value) {
        try {
            return (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
                @Override
                public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.setNX(redisTemplate.getStringSerializer().serialize(key), value.getBytes());
                }
            });
        } catch (Exception e) {
            logger.error("getLock error", e);
            return false;
        }
    }


    /**
     * 删除缓存
     *
     * @param key 需要删除缓存的id
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void unLock(final String key) {
        //如果为空
        if (StringUtil.isBlank(key)) {
            return;
        }
        try {
            redisTemplate.execute(new RedisCallback() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.del(redisTemplate.getStringSerializer().serialize(key));
                }
            });
        } catch (Exception e) {
            logger.error("delCache error", e);
        }
    }


    /**
     * 删除缓存
     *
     * @param key 需要删除缓存的id
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void delCache(final String key) {
        try {
            redisTemplate.execute(new RedisCallback() {
                @Override
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    return connection.del(redisTemplate.getStringSerializer().serialize(key));
                }
            });
        } catch (Exception e) {
            logger.error("delCache error", e);

        }
    }


    /**
     * 保存到缓存，永不过期
     *
     * @param key 缓存key
     * @param obj 缓存数据
     */
    public void saveObjectForever(final String key, final Object obj) {
        if (!RISK_CACHE_SWITCH || StringUtil.isBlank(key) || obj == null) {
            return;
        }
        try {
            redisTemplate.opsForValue().set(key, obj);
        } catch (Exception e) {
            logger.error("saveObject", e);
        }
    }

    /**
     * 获取缓存对象
     *
     * @param key 缓存key
     * @return
     */
    public <T> T getObject(final String key, Class<T> objClass) {
        if (!RISK_CACHE_SWITCH || StringUtil.isBlank(key)) {
            return null;
        }
        try {
            return (T) redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            logger.error("getObject error", e);
            return null;
        }
    }

    /**
     * 获取缓存对象
     *
     * @param key 缓存key
     * @return
     */
    public Object getObject(final String key) {
        if (!RISK_CACHE_SWITCH || StringUtil.isBlank(key)) {
            return null;
        }
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            logger.error("getObject error", e);
            return null;
        }
    }


    /**
     * 获取缓存对象
     *
     * @param key      缓存key
     * @param javaType Jackson JavaType
     * @return
     */
    public <T> T getObject(final String key, JavaType javaType) {
        if (!RISK_CACHE_SWITCH || StringUtil.isBlank(key)) {
            return null;
        }
        try {
            return (T) redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            logger.error("getObject error", e);
            return null;
        }
    }


    /**
     * 返回String类型缓存值，null则返回默认值
     *
     * @param key          缓存key
     * @param defaultValue 默认值
     * @return
     */
    public String getValue(String key, String defaultValue) {
        String value = getObject(key, String.class);
        if (StringUtil.isEmpty(value)) {
            return defaultValue;
        }
        return StringUtil.isNull(value);
    }


    /**
     * 缓存续期，单位秒，使用CacheConstants定义的时间
     *
     * @param key
     * @param expire
     */
    public void expire(final String key, final long expire) {
        if (!RISK_CACHE_SWITCH || StringUtil.isBlank(key)) {
            return;
        }
        try {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("expire error", e);
        }
    }


    /**
     * 以前缀删除缓存
     *
     * @param prefix
     */
    public void deleteByPrefix(String prefix) {
        if (!RISK_CACHE_SWITCH || StringUtil.isBlank(prefix)) {
            return;
        }
        try {
            Set<Object> keys = redisTemplate.keys(prefix + "*");
            redisTemplate.delete(keys);
        } catch (Exception e) {
            logger.error("redis delete prefix key error", e);
        }
    }

    /**
     * 以后缀删除缓存
     *
     * @param suffix
     */
    public void deleteBySuffix(String suffix) {
        if (!RISK_CACHE_SWITCH || StringUtil.isBlank(suffix)) {
            return;
        }
        try {
            Set<Object> keys = redisTemplate.keys("*" + suffix);
            redisTemplate.delete(keys);
        } catch (Exception e) {
            logger.error("redis delete suffix key error", e);
        }
    }

    /**
     * 以keys删除缓存
     *
     * @param keys
     */
    public void deleteByKeys(String... keys) {
        if (!RISK_CACHE_SWITCH || keys == null) {
            return;
        }
        try {
            redisTemplate.delete(Arrays.asList(keys));
        } catch (Exception e) {
            logger.error("redis delete keys error", e);
        }
    }


    /**
     * 锁住某个key值30S，需要解锁时删除即可
     *
     * @param key
     * @param value
     * @return
     */
    public boolean lock1Hour(final String key) {
        final String value = "xx";
        try {
            Boolean flag = set(key, value);
            if (flag) {
                redisTemplate.execute(new RedisCallback<Object>() {
                    @Override
                    public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                        return connection.expire(redisTemplate.getStringSerializer().serialize(key), CacheConstants.HOUR_OF_ONE);
                    }
                });
            }
            return flag;
        } catch (Exception e) {
            logger.error("getLock error", e);
            return false;
        }
    }

}
