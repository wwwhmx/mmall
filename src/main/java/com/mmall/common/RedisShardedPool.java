package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.*;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by www on 2020/4/4.
 */
public class RedisShardedPool {
    private static ShardedJedisPool pool ;
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total", "20"));    //最大连接数
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle", "10"));    //在jedispool中最大的idel状态（空闲状态）的jedis实例的个数
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle", "2"));    //在jedispool中最小的idel状态（空闲状态）的jedis实例的个数

    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow", "true"));  //在return一个jedis实例的时候，是否要进行校验操作，如果赋值ture表示可用连接
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return", "true")); //在return一个jedis实例的时候，是否要进行校验操作，如果赋值ture表示可用连接

    private static Integer redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis.port"));    //redisPort
    private static String redisIp = PropertiesUtil.getProperty("redis.ip");

    private static Integer redisPort_2 = Integer.parseInt(PropertiesUtil.getProperty("redis.port_2"));    //redisPort_2
    private static String redisIp_2 = PropertiesUtil.getProperty("redis.ip_2");

    private static void initPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxTotal);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        config.setBlockWhenExhausted(true); //连接耗尽时候，是否阻塞，false抛出异常，true阻塞直到超时默认为true
        //pool = new JedisPool(config, redisIp, redisPort, 1000 * 2);
        JedisShardInfo info = new JedisShardInfo(redisIp, redisPort, 1000 * 2);
        JedisShardInfo info_2 = new JedisShardInfo(redisIp_2, redisPort_2, 1000 * 2);

        List<JedisShardInfo> jedisShardInfoList = new ArrayList<JedisShardInfo>();
        jedisShardInfoList.add(info);
        jedisShardInfoList.add(info_2);

        pool = new ShardedJedisPool(config, jedisShardInfoList, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);

    }

    static {
        initPool();
    }

    public static ShardedJedis getShardedJedis(){
        return pool.getResource();
    }

    public static void returnResource(ShardedJedis shardedJedis) {
        pool.returnResource(shardedJedis);
    }

    public static void returnBrokenResource(ShardedJedis shardedJedis) {
        pool.returnBrokenResource(shardedJedis);
    }

    public static void main(String[] args) {
       // ShardedJedis shardedJedis = getShardedJedis();

        ShardedJedis shardedJedis = pool.getResource();
        for(int i=0; i<100; i++) {
            shardedJedis.set("key"+i, "value"+i);
        }
        returnResource(shardedJedis);
       // pool.destroy();     //临时销毁
    }

}
