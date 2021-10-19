package cn.crtech.cooperop.bus.cache.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import cn.crtech.cooperop.bus.cache.CacheInterface;
import cn.crtech.cooperop.bus.log.log;
import cn.crtech.cooperop.bus.util.CommonFun;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class Redis implements CacheInterface {

    private static final String AREA_KEY_CONCATER = ":";

    private static JedisPool jedisPool;// 非切片连接池
    private ShardedJedisPool shardedJedisPool;// 切片连接池
    private static int expire_time = -1; //过期时间

    @Override
    public void init(Properties config) throws Exception {
        expire_time = Integer.parseInt(config.getProperty("expire", "-1"));
        initPool(config);
        log.release("init memory cache [redis] success.");
    }

    /**
     * 非切片池，用于单机redis
     *
     * @throws Exception
     * @throws IOException
     */

    public void initPool(Properties p) throws Exception {
        // 池基本配置
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(Integer.parseInt(p.getProperty("redis.maxTotal")));
        config.setMaxIdle(Integer.parseInt(p.getProperty("redis.maxIdle")));
        config.setMaxWaitMillis(Long.valueOf(p.getProperty("redis.maxWait")));
        config.setTestOnBorrow(Boolean.valueOf(p.getProperty("redis.testOnBorrow")));

        String host = p.getProperty("redis.host");
        String password = p.getProperty("redis.password");
        int port = Integer.parseInt(p.getProperty("redis.port"));
        int timeout = Integer.parseInt(p.getProperty("redis.timeout"));
        int databaseid = Integer.parseInt(p.getProperty("redis.database"));
        jedisPool = new JedisPool(config, host, port, timeout, CommonFun.isNe(password) ? null : password, databaseid);
        if (jedisPool.isClosed()) {
            throw new Exception("connect to Redis server failed");
        }
    }

    /**
     * 切片池，用于分布式，操作redis集群
     *
     * @throws Exception
     */
    public void initShardedPool(Properties p) throws Exception {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(Integer.parseInt(p.getProperty("redis.maxTotal")));
        config.setMaxIdle(Integer.parseInt(p.getProperty("redis.maxIdle")));
        config.setMaxWaitMillis(Long.valueOf(p.getProperty("redis.maxWait")));
        config.setTestOnBorrow(Boolean.valueOf(p.getProperty("redis.testOnBorrow")));

        List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
        shards.add(new JedisShardInfo("127.0.0.1", 7379, "master"));
        // 构造池
        shardedJedisPool = new ShardedJedisPool(config, shards);
        if (shardedJedisPool.isClosed()) {
            throw new Exception("connect to Redis server failed");
        }
    }

    @Override
    public void destory() {
        if (jedisPool != null) {
            jedisPool.destroy();
        }
        if (shardedJedisPool != null) {
            shardedJedisPool.destroy();
        }
    }

    /**
     * 同步获取Jedis实例
     *
     * @return Jedis
     */
    private synchronized static Jedis getJedis() {
        Jedis jedis = null;
        try {
            if (jedisPool != null) {
                jedis = jedisPool.getResource();
                // jedis.auth(redisCacheConfig.getAuth());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jedis;
    }

    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    @Override
    public void put(String area, String key, Object value) {
        put(area, key, value, expire_time);
    }

    @Override
    public void putStr(String area, String key, String value) {
        putStr(area, key, value, expire_time);
    }

    @Override
    public void put(String area, String key, Object value, int lifetime) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String k = area + AREA_KEY_CONCATER + key;
            jedis.set(k.getBytes(), serialize(value));
            if (lifetime >= 0) {
                jedis.expire(k.getBytes(), lifetime);
            }
        } catch (Exception e) {
            log.error(e);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public void putStr(String area, String key, String value, int lifetime) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String k = area + AREA_KEY_CONCATER + key;
            jedis.set(k, value);
            if (lifetime >= 0) {
                jedis.expire(k.getBytes(), lifetime);
            }
        } catch (Exception e) {
            log.error(e);
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public void putAll(String area, HashMap<String, Object> map) {
        putAll(area, map, expire_time);
    }

    @Override
    public void putAll(String area, HashMap<String, Object> map, int lifetime) {
        if (map == null)
            return;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Iterator<String> itr = map.keySet().iterator();
            while (itr.hasNext()) {
                String key = itr.next();
                String k = area + AREA_KEY_CONCATER + key;
                jedis.set(k.getBytes(), serialize(map.get(key)));
                if (lifetime >= 0) {
                    jedis.expire(k.getBytes(), lifetime);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            returnResource(jedis);
        }
    }

    @Override
    public Object get(String area, String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String k = area + AREA_KEY_CONCATER + key;
            byte[] in =  jedis.get(k.getBytes());
            return deserialize(in);
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            returnResource(jedis);
        }
        return null;
    }

    @Override
    public String getStr(String area, String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String k = area + AREA_KEY_CONCATER + key;
            return jedis.get(k);
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            returnResource(jedis);
        }
        return null;
    }

    @Override
    public boolean containsKey(String area, String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String k = area + AREA_KEY_CONCATER + key;
            return jedis.exists(k.getBytes());
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            returnResource(jedis);
        }
        return false;
    }

    @Override
    public Object remove(String area, String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String k = area + AREA_KEY_CONCATER + key;
            byte[] in =  jedis.get(k.getBytes());
            jedis.del(k.getBytes());
            return deserialize(in);
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            returnResource(jedis);
        }
        return null;
    }

    @Override
    public String removeStr(String area, String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String k = area + AREA_KEY_CONCATER + key;
            String rtn = jedis.get(k);
            jedis.del(k);
            return rtn;
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            returnResource(jedis);
        }
        return null;
    }

    @Override
    public Set<String> areaSet() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Set<String> set = jedis.keys("*:*");
            Iterator<String> it = set.iterator();
            Set<String> nset = new HashSet<String>();
            //TODO:过滤只拿area部分的
            while (it.hasNext()) {
                String k = it.next();
                if (k.indexOf(AREA_KEY_CONCATER) > 0) {
                    k = k.split(AREA_KEY_CONCATER)[0];
                    if (!nset.contains(k)) {
                        nset.add(k);
                    }
                }
            }

            return nset;
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            returnResource(jedis);
        }
        return null;
    }

    @Override
    public Set<String> keySet(String area) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            Set<String> set =  jedis.keys(area + AREA_KEY_CONCATER + "*");
            Iterator<String> it = set.iterator();
            Set<String> nset = new HashSet<String>();
            while (it.hasNext()) {
                nset.add(it.next().substring((area + AREA_KEY_CONCATER).length()));
            }
            return nset;
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            returnResource(jedis);
        }
        return null;
    }

    @Override
    public void flushArea(String area) {
        Set<String> set = keySet(area);
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            remove(area, it.next());
        }
    }

    @Override
    public void flushAll() {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.flushAll();
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            returnResource(jedis);
        }
    }

    private static byte[] serialize(Object value) {
        if (value == null) {
            return null;
        }
        byte[] rv = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(value);
            os.close();
            bos.close();
            rv = bos.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException("Non-serializable object", e);
        } finally {
            try {
                if (os != null)
                    os.close();
                if (bos != null)
                    bos.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return rv;
    }

    private static Object deserialize(byte[] in) {
        if (in == null)
            return null;
        Object rv = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if (in != null) {
                bis = new ByteArrayInputStream(in);
                is = new ObjectInputStream(bis);
                rv = is.readObject();
                is.close();
                bis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
                if (bis != null)
                    bis.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return rv;
    }

}
