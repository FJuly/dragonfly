/**
 * Copyright 2012-2015 www.fangdd.com All Rights Reserved.
 * Author: Liu Xiao Long <liuxiaolong@fangdd.com>
 * Date:   2015年5月22日
 */
package utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.*;

// 对jedis的简单封装处理
public class JedisTemplate {

    private JedisPool jedisPool;

    public JedisTemplate(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

                    private final Logger logger = LoggerFactory.getLogger(JedisTemplate.class);

                    // 设置key并且设定一个默认失效时间
                    // expired单位为ex 秒，从设置的时间开始往后推算expired 秒
                public void set(String key, String value, int expired) {
                    if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
                        JedisPool jedisPool = null;
                        Jedis jedis = null;
                        try {
                            jedisPool = getJedisPool();
                            if (jedisPool != null) {
                                jedis = jedisPool.getResource();
                                jedis.setex(key, expired, value);
                                logger.info("jedisTemplate>>>set key={},value={},expired={} succeed", new Object[] {key, value,
                                        expired});
                            }
                        } catch (Exception e) {
                            if (jedisPool != null) {
                    jedisPool.returnBrokenResource(jedis);
                }
                logger.error("jedisTemplate>>>set key={},value={},expired={} failed e={}",
                             new Object[] {key, value, expired, e.getCause()});
            } finally {
                if (jedisPool != null) {
                    jedisPool.returnResource(jedis);
                }
            }
        }
    }

    // 设置key-value不设置失效时间
    public void set(String key, String value) {
        if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
            JedisPool jedisPool = null;
            Jedis jedis = null;
            try {
                jedisPool = getJedisPool();
                if (jedisPool != null) {
                    jedis = jedisPool.getResource();
                    jedis.set(key, value);
                    logger.info("jedisTemplate>>>set key={},value={} succeed", new Object[] {key, value});
                }
            } catch (Exception e) {
                if (jedisPool != null) {
                    jedisPool.returnBrokenResource(jedis);
                }
                logger.error("jedisTemplate>>>set key={},value={} failed e={}", new Object[] {key, value, e.getCause()});
            } finally {
                if (jedisPool != null) {
                    jedisPool.returnResource(jedis);
                }
            }
        }
    }

    public boolean set(String key, String value, String nxxx, String expx, int time) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value)) {
            return false;
        }
        JedisPool jedisPool = null;
        Jedis jedis = null;
        String result = "null";
        try {
            jedisPool = getJedisPool();
            if (jedisPool != null) {
                jedis = jedisPool.getResource();
                result = jedis.set(key, value, nxxx, expx, time);
                logger.info("jedisTemplate>>>set key={},value={} result={}",
                        new Object[] {key, value, result});
            }
        } catch (Exception e) {
            if (jedisPool != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            logger.error("jedisTemplate>>>set key={},value={} failed e={}", new Object[] {key,
                    value, e.getCause()});
        } finally {
            if (jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
        }
        return "OK".equals(result) ? true : false;
    }

    // 根据key查询value
    public String get(String key) {
        String value = "";
        if (StringUtils.isNotEmpty(key)) {
            JedisPool jedisPool = null;
            Jedis jedis = null;
            try {
                jedisPool = getJedisPool();
                if (jedisPool != null) {
                    jedis = jedisPool.getResource();
                    String tmp = jedis.get(key);
                    value = tmp != null ? tmp : value;
                    logger.info("jedisTemplate>>>get key={},value={} succeed", new Object[] {key, value});
                }
            } catch (Exception e) {
                if (jedisPool != null) {
                    jedisPool.returnBrokenResource(jedis);
                }
                logger.error("jedisTemplate>>>get key={},value={} failed e={}", new Object[] {key, value, e.getCause()});
            } finally {
                if (jedisPool != null) {
                    jedisPool.returnResource(jedis);
                }
            }
        }
        return value;
    }

    // 并发控制
    public boolean getAndSet(String key, String value, int expired) {
        if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
            JedisPool jedisPool = null;
            Jedis jedis = null;
            try {
                jedisPool = getJedisPool();
                if (jedisPool != null) {
                    jedis = jedisPool.getResource();
                    String temp = jedis.get(key);
                    if (temp != null) {
                        return false;
                    }
                    jedis.watch(key);
                    Transaction tx = jedis.multi();
                    tx.setex(key, expired, value);

                    List<Object> result = tx.exec(); // 运行时在这边打断点，然后通过命令行改变foo的值
                    if (result == null || result.isEmpty()) {
                        return getAndSet(key, value, expired);
                    }
                    logger.info("jedisTemplate>>>set key={},value={},expired={} succeed", new Object[] {
                        key,
                        value,
                        expired
                    });
                }
            } catch (Exception e) {
                if (jedisPool != null) {
                    jedisPool.returnBrokenResource(jedis);
                }
                logger.error("jedisTemplate>>>set key={},value={},expired={} failed e={}", new Object[] {
                    key,
                    value,
                    expired,
                    e
                });
            } finally {
                if (jedisPool != null) {
                    jedisPool.returnResource(jedis);
                }
            }

        }
        return true;
    }

    // 设置hset,key-fieldKey-value值
    public void hset(String key, String fieldKey, String value) {
        if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value) && StringUtils.isNotEmpty(fieldKey)) {
            JedisPool jedisPool = null;
            Jedis jedis = null;
            try {
                jedisPool = getJedisPool();
                if (jedisPool != null) {
                    jedis = jedisPool.getResource();
                    jedis.hset(key, fieldKey, value);
                    logger.info("jedisTemplate>>>hset key={},fieldKey={},value={} succeed", new Object[] {key,
                                                                                                          fieldKey,
                                                                                                          value});
                }
            } catch (Exception e) {
                if (jedisPool != null) {
                    jedisPool.returnBrokenResource(jedis);
                }
                logger.error("jedisTemplate>>>hset key={},fieldKey={},value={} failed e={}",
                             new Object[] {key, fieldKey, value, e.getCause()});
            } finally {
                if (jedisPool != null) {
                    jedisPool.returnResource(jedis);
                }
            }
        }
    }

    // 获取hgetall key对应的值，放在map<fieldKey,value>中返回
    public Map<String, String> hgetAll(String key) {
        Map<String, String> map = new HashMap<String, String>();
        if (StringUtils.isNotEmpty(key)) {
            JedisPool jedisPool = null;
            Jedis jedis = null;
            try {
                jedisPool = getJedisPool();
                if (jedisPool != null) {
                    jedis = jedisPool.getResource();
                    Map<String, String> tmpmap = jedis.hgetAll(key);
                    map = tmpmap != null ? tmpmap : map;
                    logger.info("jedisTemplate>>>hgetall key={},value={} succeed", new Object[] {key, tmpmap});
                }
            } catch (Exception e) {
                if (jedisPool != null) {
                    jedisPool.returnBrokenResource(jedis);
                }
                logger.error("jedisTemplate>>>hget key={},value={},failed e={}", new Object[] {key, map, e.getCause()});
            } finally {
                if (jedisPool != null) {
                    jedisPool.returnResource(jedis);
                }
            }
        }
        return map;
    }

    /**
     * 查询匹配keys全部key键值列表
     * redis里边的keys* 命令
     */
    public Set<String> keys(String keysPattern) {
        Set<String> set = new HashSet<String>();
        if (StringUtils.isNotEmpty(keysPattern)) {
            JedisPool jedisPool = null;
            Jedis jedis = null;
            try {
                jedisPool = getJedisPool();
                if (jedisPool != null) {
                    jedis = jedisPool.getResource();
                    Set<String> tmp = jedis.keys(keysPattern);
                    set = tmp != null ? tmp : set;
                    logger.info("jedisTemplate>>>keys keyPattern={}, set.size={}",
                                new Object[] {keysPattern, set.size()});
                }
            } catch (Exception e) {
                if (jedisPool != null) {
                    jedisPool.returnBrokenResource(jedis);
                }
                logger.error("jedisTemplate>>>keys keyPattern={}, failed e={}",
                             new Object[] {keysPattern, e.getCause()});
            } finally {
                if (jedisPool != null) {
                    jedisPool.returnResource(jedis);
                }
            }
        }
        return set;
    }

    // 从hset中hget出对应的key-field对应的value值
    public String hget(String key, String fieldKey) {
        String value = "";
        if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(fieldKey)) {
            JedisPool jedisPool = null;
            Jedis jedis = null;
            try {
                jedisPool = getJedisPool();
                if (jedisPool != null) {
                    jedis = jedisPool.getResource();
                    String tmp = jedis.hget(key, fieldKey);
                    value = tmp != null ? tmp : value;
                    logger.info("jedisTemplate>>>hget key={},fieldKey={},value={} succeed", new Object[] {key,
                                                                                                          fieldKey,
                                                                                                          value});
                }
            } catch (Exception e) {
                if (jedisPool != null) {
                    jedisPool.returnBrokenResource(jedis);
                }
                logger.error("jedisTemplate>>>hget key={},fieldKey={},value={} failed e={}",
                             new Object[] {key, fieldKey, value, e.getCause()});
            } finally {
                if (jedisPool != null) {
                    jedisPool.returnResource(jedis);
                }
            }
        }
        return value;
    }

    // lpush对应的value值
    public void lpush(String key, int seconds, String... values) {
        String value = "";
        if (StringUtils.isNotEmpty(key) && values.length > 0) {
            JedisPool jedisPool = null;
            Jedis jedis = null;
            try {
                jedisPool = getJedisPool();
                if (jedisPool != null) {
                    jedis = jedisPool.getResource();
                    jedis.lpush(key, values);
                    if (seconds > 0) {
                        jedis.expire(key, seconds);
                    }
                    logger.info("jedisTemplate>>>lpush key={},values={} succeed", new Object[] {key,
                            values});
                }
            } catch (Exception e) {
                if (jedisPool != null) {
                    jedisPool.returnBrokenResource(jedis);
                }
                logger.error("jedisTemplate>>>lpush key={},values={} failed e={}",
                        new Object[] {key, values, e.getCause()});
            } finally {
                if (jedisPool != null) {
                    jedisPool.returnResource(jedis);
                }
            }
        }
    }

    // 从hset中hget出对应的key-field对应的value值
    public String lpop(String key) {
        String value = "";
        if (StringUtils.isNotEmpty(key)) {
            JedisPool jedisPool = null;
            Jedis jedis = null;
            try {
                jedisPool = getJedisPool();
                if (jedisPool != null) {
                    jedis = jedisPool.getResource();
                    String tmp = jedis.lpop(key);
                    value = tmp != null ? tmp : value;
                    logger.info("jedisTemplate>>>lpop key={},value={} succeed", new Object[] {key,
                            value});
                }
            } catch (Exception e) {
                if (jedisPool != null) {
                    jedisPool.returnBrokenResource(jedis);
                }
                logger.error("jedisTemplate>>>lpop key={}, value={} failed e={}",
                        new Object[] {key, value, e.getCause()});
            } finally {
                if (jedisPool != null) {
                    jedisPool.returnResource(jedis);
                }
            }
        }
        return value;
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public String hmset(String key, Map<String, String> hash) {
        if (StringUtils.isEmpty(key) || hash == null || hash.size() == 0) {
            return null;
        }

        JedisPool jedisPool = null;
        Jedis jedis = null;
        try {
            jedisPool = getJedisPool();
            jedis = jedisPool.getResource();
            String result = jedis.hmset(key, hash);
            logger.info("jedisTemplate>>>hmset key={}, map={} succeed，result={}", key, hash, result);
            return result;
        } catch (Exception e) {
            if (jedisPool != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            logger.info("jedisTemplate>>>hmset key={}, map={} failed", key, hash, e);
        } finally {
            if (jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
            return null;
        }
    }

    public Long hincrBy(String key, String field, long n) {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        try {
            jedisPool = getJedisPool();
            jedis = jedisPool.getResource();
            Long result = jedis.hincrBy(key, field, 1);
            logger.info("jedisTemplate>>>hincrBy key={}, field={}, n={} succeed, result={}", key, field, n, result);
            return result;
        } catch (Exception e) {
            if (jedisPool != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            logger.info("jedisTemplate>>>hincrBy key={}, field={}, n={} failed", key, field, n, e);
        } finally {
            if (jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
        }
        return null;
    }

    public Long del(String key) {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        try {
            jedisPool = getJedisPool();
            jedis = jedisPool.getResource();
            Long result = jedis.del(key);
            logger.info("jedisTemplate>>>del key={}, succeed, result={}", key, result);
            return result;
        } catch (Exception e) {
            if (jedisPool != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            logger.info("jedisTemplate>>>hincrBy key={}, failed", key, e);
        } finally {
            if (jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
        }
        return null;
    }

    public Object eval(String script, int keyCount, String... params) {
        JedisPool jedisPool = null;
        Jedis jedis = null;
        try {
            jedisPool = getJedisPool();
            jedis = jedisPool.getResource();
            Object result = jedis.eval(script, keyCount, params);
            logger.info("jedisTemplate>>>eval script={}, succeed, result={}", script, JSON.toJSONString(result));
            return result;
        } catch (Exception e) {
            if (jedisPool != null) {
                jedisPool.returnBrokenResource(jedis);
            }
            logger.info("jedisTemplate>>>eval script={}, failed", script, e);
        } finally {
            if (jedisPool != null) {
                jedisPool.returnResource(jedis);
            }
        }
        return null;
    }

}
