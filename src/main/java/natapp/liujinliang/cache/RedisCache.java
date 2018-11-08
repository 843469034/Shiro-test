package natapp.liujinliang.cache;

import com.sun.xml.internal.fastinfoset.tools.FI_SAX_XML;
import natapp.liujinliang.util.JedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;
import  java.math.*;
/**
 * Created by Administrator on 2018/11/7.
 */

@Component
public class RedisCache<k,v> implements Cache<k,v> {
    @Resource
    private JedisUtil jedisUtil;

    private  final  String  CACHE_PREFIX = "imooc-cache";
    private  byte[] getKey(k k){
            if(k instanceof  String ){

                return (CACHE_PREFIX+k).getBytes();
            }
        return SerializationUtils.serialize(k);

    }
    public v get(k k) throws CacheException {
        byte[] value = jedisUtil.get(getKey(k));
        if(value != null){
            return (v) SerializationUtils.deserialize(value);
        }

        return null;
    }

    public v put(k k, v v) throws CacheException {
        byte[] key = getKey(k);
        byte[] value = SerializationUtils.serialize(v);

        jedisUtil.set(key,value);
        return v;
    }

    public v remove(k k) throws CacheException {
        byte[] key = getKey(k);

        byte[] value = jedisUtil.get(key);
        jedisUtil.delete(key);
           if(value  != null){

               return (v) SerializationUtils.deserialize(value);
           }
        return null;
    }

    public void clear() throws CacheException {

    }

    public int size() {
        return 0;
    }

    public Set<k> keys() {
        return null;
    }

    public Collection<v> values() {
        return null;
    }
}
