package top.xujm.core.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Xujm
 */
@Component
public class RedisBiz {

    @Autowired
    protected StringRedisTemplate stringRedisTemplate;

    /**
     * 保存字符串
     */
    public void set(String key,String value){
        stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * 保存字符串
     * @param time 过期时间(秒)
     */
    public void set(String key,String value,long time){
        stringRedisTemplate.opsForValue().set(key, value,time, TimeUnit.SECONDS);
    }

    /**
     * 获取字符串
     */
    public String get(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 删除键
     */
    public void del(String key){
        stringRedisTemplate.delete(key);
    }

    /**
     * 缓存对象
     * @param key 缓存键
     * @param valueKey 对象键
     * @param val 对象
     */
    public void setObject(String key,String valueKey,Object val){
        stringRedisTemplate.opsForHash().put(key,valueKey,val);
    }

    /**
     * 获取对象
     * @param key 缓存键
     * @param valueKey 对象键
     */
    public void getObject(String key,String valueKey){
        stringRedisTemplate.opsForHash().get(key,valueKey);
    }

}
