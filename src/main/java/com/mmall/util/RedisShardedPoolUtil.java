package com.mmall.util;

import avro.shaded.com.google.common.collect.Lists;
import com.mmall.common.RedisShardedPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.ShardedJedis;

import java.util.List;

/**
 * Created by www on 2020/3/28.
 */
@Slf4j
public class RedisShardedPoolUtil {
    /*
    设置key的有效期
     */
    public static Long expire(String key, int exTime) {
        ShardedJedis shardedJedis = null;
        Long result = null;
        try {
            shardedJedis = RedisShardedPool.getShardedJedis();
            result = shardedJedis.expire(key, exTime);
        } catch (Exception e) {
            log.error("set key:{} error", key, e);
            RedisShardedPool.returnBrokenResource(shardedJedis);
            return result;
        }
        RedisShardedPool.returnResource(shardedJedis);
        return result;
    }

    public static String setEx(String key, String value, int exTime) {
        ShardedJedis shardedJedis = null;
        String result = null;
        try {
            shardedJedis = RedisShardedPool.getShardedJedis();
            result = shardedJedis.setex(key, exTime, value);
        } catch (Exception e) {
            log.error("set key:{} value:{} error", key, value, e);
            RedisShardedPool.returnBrokenResource(shardedJedis);
            return result;
        }
        RedisShardedPool.returnResource(shardedJedis);
        return result;
    }

    public static String set(String key, String value) {
        ShardedJedis shardedJedis = null;
        String result = null;
        try {
            shardedJedis = RedisShardedPool.getShardedJedis();
            result = shardedJedis.set(key, value);
        } catch (Exception e) {
            log.error("set key:{} value:{} error", key, value, e);
            RedisShardedPool.returnBrokenResource(shardedJedis);
            return result;
        }
        RedisShardedPool.returnResource(shardedJedis);
        return result;
    }
    public static String get(String key) {
        ShardedJedis shardedJedis = null;
        String result = null;
        try {
            shardedJedis = RedisShardedPool.getShardedJedis();
            result = shardedJedis.get(key);
        } catch (Exception e) {
            log.error("set key:{} value:{} error", key, e);
            RedisShardedPool.returnBrokenResource(shardedJedis);
            return result;
        }
        RedisShardedPool.returnResource(shardedJedis);
        return result;
    }

    public static Long del(String key) {
        ShardedJedis shardedJedis = null;
        Long result = null;
        try {
            shardedJedis = RedisShardedPool.getShardedJedis();
            result = shardedJedis.del(key);
        } catch (Exception e) {
            log.error("set key:{} value:{} error", key, e);
            RedisShardedPool.returnBrokenResource(shardedJedis);
            return result;
        }
        RedisShardedPool.returnResource(shardedJedis);
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








