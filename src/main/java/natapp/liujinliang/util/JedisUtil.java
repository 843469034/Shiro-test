package natapp.liujinliang.util;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by Administrator on 2018/11/6.
 */
@Component
public class JedisUtil {

    @Resource
    private JedisPool jedisPool;

    private Jedis getJedisPool(){
        return  jedisPool.getResource();
    }

    public byte[] set(byte[] key, byte[] value) {
          Jedis jedis = getJedisPool();
        try {
            jedis.set(key,value);
            return  value;
        } finally {
            jedis.close();
        }

    }

    public void  expire(byte[] key, int i) {
            Jedis jedis = getJedisPool();
        try {
            jedis.expire(key,i);
        } finally {
            jedis.close();
        }

    }

    public byte[] get(byte[] key) {
        Jedis jedis = getJedisPool();
        try {
            return jedis.get(key);

        } finally {
            jedis.close();
        }
    }

    public void delete(byte[] key) {
        Jedis jedis = getJedisPool();
        try {
            jedis.del(key);
        } finally {
            jedis.close();
        }
    }

    public Set<byte[]> keys(String shiro_session_prefix) {
        Jedis jedis = getJedisPool();
        try {
          return  jedis.keys((shiro_session_prefix+"*").getBytes());
        } finally {
            jedis.close();
        }
    }
}
