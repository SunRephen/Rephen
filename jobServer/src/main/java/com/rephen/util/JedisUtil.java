package com.rephen.util;

import java.util.Set;

import javax.annotation.Resource;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis 工具类
 * @author Rephen
 *
 */
public class JedisUtil {
    
    @Resource
    private JedisPoolConfig jedisPoolConfig;
    
    @Resource
    private JedisPool jedisPool;
    
    
    /**
     * 从jedis连接池中获取获取jedis对象
     * @return
     */
    private Jedis getJedis() {
        if (null == jedisPool) {
            return null;
        }
        return jedisPool.getResource();
    }
    
    /**
     * 回收jedis
     * @param jedis
     */
    private void returnJedis(Jedis jedis) {
        if (null == jedisPool) {
            return;
        }
        jedisPool.returnResource(jedis);
    }

    /**
     * 设置过期时间
     * @param key
     * @param seconds
     */
    public void expire(String key, int seconds) {
        if (seconds <= 0) {
            return;
        }
        Jedis jedis = getJedis();
        jedis.expire(key, seconds);
        returnJedis(jedis);
    }
    
    /**
     * 添加String
     * @param key
     * @param value
     */
    public void add(String key,String value){
        Jedis jedis = getJedis();
        jedis.set(key, value);
        returnJedis(jedis);
    }
    
    /**
     * 获取String
     * @param key
     * @return
     */
    public String get(String key){
        Jedis jedis = getJedis();
        String value = jedis.get(key);
        returnJedis(jedis);
        return value;
    }
    
    /**
     * 添加 zset 
     * @param key
     * @param member
     * @param score
     */
    public void zadd(String key,String member,double score){
        Jedis jedis = getJedis();
        jedis.zadd(key, score, member);
        returnJedis(jedis);
    }
    
    /**
     * zset 获取权重值
     * @param key
     * @param member
     * @return
     */
    public Double zgetScore(String key,String member){
        Jedis jedis = getJedis();
        Double score = jedis.zscore(key, member);
        returnJedis(jedis);
        return score;
    }
    
    /**
     * zset 获取member
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zgetMember(String key,int start,int end){
        Jedis jedis = getJedis();
        Set<String> members = jedis.zrange(key, start, end);
        returnJedis(jedis);
        return members;
    }
    
    /**
     * 立即过期
     * @param key
     */
    public void expireimmediately(String key){
        Jedis jedis = getJedis();
        jedis.del(key);
        returnJedis(jedis);
    }
    
    
    /**
     * 添加set
     * @param key
     * @param member
     */
    public void sadd(String key,String member){
        Jedis jedis = getJedis();
        jedis.sadd(key, member);
        returnJedis(jedis);
    }
    
    /**
     * set 获取members
     * @param key
     * @return
     */
    public Set<String> sget(String key){
        Jedis jedis = getJedis();
        Set<String> members = jedis.smembers(key);
        returnJedis(jedis);
        return members;
    }
    
    /**
     * set 获取元素个数
     * @param key
     * @return
     */
    public Long scount(String key){
        Jedis jedis = getJedis();
        Long count = jedis.scard(key);
        returnJedis(jedis);
        return count;
    }
    
    
    

}
