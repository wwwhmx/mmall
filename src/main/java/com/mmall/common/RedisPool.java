package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by www on 2020/3/26.
 */
public class RedisPool {
    private static JedisPool pool ;
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total", "20"));    //最大连接数
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle", "10"));    //在jedispool中最大的idel状态（空闲状态）的jedis实例的个数
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle", "2"));    //在jedispool中最小的idel状态（空闲状态）的jedis实例的个数

    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow", "true"));  //在return一个jedis实例的时候，是否要进行校验操作，如果赋值ture表示可用连接
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return", "true")); //在return一个jedis实例的时候，是否要进行校验操作，如果赋值ture表示可用连接

    private static Integer redisPort = Integer.parseInt(PropertiesUtil.getProperty("redis.port"));    //redisPort
    private static String redisIp = PropertiesUtil.getProperty("redis.ip");

    private static void initPool() {
        JedisPoolConfig  config = new JedisPoolConfig();
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxTotal);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);

        config.setBlockWhenExhausted(true); //连接耗尽时候，是否阻塞，false抛出异常，true阻塞直到超时默认为true
        pool = new JedisPool(config, redisIp, redisPort, 1000 * 2);

    }

    static {
        initPool();
    }

    public static Jedis getJedis(){
        return pool.getResource();
    }

    public static void returnResource(Jedis jedis) {
            pool.returnResource(jedis);
    }

    public static void returnBrokenResource(Jedis jedis) {
            pool.returnBrokenResource(jedis);
    }

    public static void main(String[] args) {
        Jedis jedis = getJedis();

        Jedis jedis1 = pool.getResource();
        jedis.set("key", "value");
        returnResource(jedis);
        pool.destroy();     //临时销毁
    }

}








