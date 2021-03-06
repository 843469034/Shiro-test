package natapp.liujinliang.session;

import natapp.liujinliang.util.JedisUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.util.CollectionUtils;
import org.springframework.util.SerializationUtils;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2018/11/6.
 */
public class RedisSessionDao extends AbstractSessionDAO {
    @Resource
    private JedisUtil jedisUtil;
    private  final  String  SHIRO_SESSION_PREFIX ="imooc-session";
    private byte[] getKey(String sessionid){
        return  (SHIRO_SESSION_PREFIX+sessionid).getBytes();
    }
    private  void  saveSession(Session session){
        if(session !=null && session.getId()!=null){
            byte[] key =  getKey(session.getId().toString());
            byte[] value = SerializationUtils.serialize(session);

            jedisUtil.set(key,value);
            jedisUtil.expire(key,600);
        }
    }

    protected Serializable doCreate(Session session) {
    // generateSessionId 先随机randowuuid生成id   才能通过getId去取   但是随机生成需要通过assignSessionId来绑定
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session,sessionId);

        System.out.println("generateSessionId: "+sessionId.toString());
        System.out.println("getsession:"+session.getId());
       saveSession(session);
        return sessionId;
    }

    protected Session doReadSession(Serializable sessionId) {
        System.out.println("read session");
        if(sessionId == null){
            return  null;
        }
        byte[] key =getKey(sessionId.toString());
        byte[] value = jedisUtil.get(key);
       return (Session) SerializationUtils.deserialize(value);

    }


    public void update(Session session) throws UnknownSessionException {
        saveSession(session);
    }

    public void delete(Session session) {
        if(session ==null && session.getId()==null){
            return;
        }
        byte [] key = getKey(session.getId().toString());
        jedisUtil.delete(key);
    }

    public Collection<Session> getActiveSessions() {
        Set<byte[]>  keys = jedisUtil.keys(SHIRO_SESSION_PREFIX);
         Set<Session> sessions = new HashSet<Session>();
         if (CollectionUtils.isEmpty(keys)){
             return  sessions;
         }
         for (byte[] key : keys){
             byte[] value = jedisUtil.get(key);
             Session  session = (Session) SerializationUtils.deserialize(value);
             sessions.add(session);

         }
        return sessions;
    }
}
