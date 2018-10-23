package top.xujm.modules.security.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xujm.core.redis.RedisBiz;
import top.xujm.core.redis.RedisCache;

/**
 * @author Xujm
 */
@Component
public class SecurityRedisCache extends RedisCache {

    @Autowired
    private RedisBiz redisBiz;

    public void setCaptcha(String badge,String value,long expireTime){
        redisBiz.set(getKey(SecurityRedisCacheEnum.CAPTCHA.getKey(),badge),value,expireTime);
    }

    public String getCaptcha(String badge){
        return redisBiz.get(getKey(SecurityRedisCacheEnum.CAPTCHA.getKey(),badge));
    }

}
