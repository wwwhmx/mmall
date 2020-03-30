package com.mmall.util;

import avro.shaded.com.google.common.collect.Lists;
import com.mmall.common.RedisPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by www on 2020/3/28.
 */
@Slf4j
public class RedisPoolUtil {
    /*
    设置key的有效期
     */
    public static Long expire(String key, int exTime) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.expire(key, exTime);
        } catch (Exception e) {
            log.error("set key:{} error", key, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static String setEx(String key, String value, int exTime) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.setex(key, exTime, value);
        } catch (Exception e) {
            log.error("set key:{} value:{} error", key, value, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static String set(String key, String value) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("set key:{} value:{} error", key, value, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }
    public static String get(String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("set key:{} value:{} error", key, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static Long del(String key) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("set key:{} value:{} error", key, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static void main(String[] args) {
       /* Jedis jedis = RedisPool.getJedis();

        RedisPoolUtil.set("key1", "value1");

        String value = RedisPoolUtil.get("key1");
        RedisPoolUtil.setEx("key2", "value2", 60 * 10);
        RedisPoolUtil.expire("key1", 60 * 20);
        RedisPoolUtil.del("key1");
        System.out.println("end");*/

        List list = Lists.newArrayList();
        String str = new String();
        str = null;
        if (list == null) {
            System.out.println("list1 empty");
        }else{
            System.out.println("list1 not empty");
        }
        if (str == null) {
            System.out.println("str empty");
        }else{
            System.out.println("str not empty");
        }
        if (str == null) {
            System.out.println("str empty");
        }else{
            System.out.println("str not empty");
        }

    }

}








