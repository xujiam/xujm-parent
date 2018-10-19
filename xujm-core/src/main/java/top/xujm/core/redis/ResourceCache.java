package top.xujm.core.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import top.xujm.core.utils.SpringContextUtil;

/**
 * @author Xujm
 */
public class ResourceCache {

    public static void clear(String cacheCode){
        getRedisTemplate().convertAndSend("cache",cacheCode);
    }

    public static StringRedisTemplate getRedisTemplate(){
        StringRedisTemplate redisTemplate = (StringRedisTemplate) SpringContextUtil.getBean("stringRedisTemplate");
        if(redisTemplate == null){
            throw new RuntimeException("redisTemplate not found");
        }
        return redisTemplate;
    }

}
