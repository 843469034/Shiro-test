package natapp.liujinliang.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/11/8.
 */
public class RedisCacheManager implements CacheManager {

    @Resource
    private  RedisCache redisCache;

    public <K, V> Cache<K, V> getCache(String name) throws CacheException {


            return redisCache;

    }
}
