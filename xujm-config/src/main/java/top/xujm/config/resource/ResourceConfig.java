package top.xujm.config.resource;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import top.xujm.core.base.BaseResultEnum;
import top.xujm.core.exception.RemindException;
import top.xujm.core.redis.ResourceCache;
import top.xujm.core.utils.LibConverterUtil;
import top.xujm.core.utils.LibSysUtil;
import top.xujm.core.utils.SpringContextUtil;
import top.xujm.modules.config.biz.PlatformConfigBiz;
import top.xujm.modules.config.common.ConfigLocalEnum;
import top.xujm.modules.config.common.ConfigRedisCacheKeyEnum;
import top.xujm.modules.config.model.PlatformConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Xujm
 */
public class ResourceConfig {

    private final static Map<String,String> PLATFORM_CONFIG = new HashMap<>();

    private final static JSONObject APP_CONFIG = new JSONObject();

    static {
        init();
    }

    public static JSONObject getAppConfig(){
        if(APP_CONFIG.size() != 0){
            return APP_CONFIG;
        }
        return getRedisAppConfig();
    }

    private static JSONObject getRedisAppConfig(){
        Map<Object,Object> map = getRedisTemplate().opsForHash().entries(ConfigRedisCacheKeyEnum.APP_CONFIG.getKey());
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            APP_CONFIG.put(LibConverterUtil.toString(entry.getKey()),entry.getValue());
        }
        return APP_CONFIG;
    }

    public static String getConfig(String key){
        String value = PLATFORM_CONFIG.get(key);
        if(StringUtils.isEmpty(value)){
            value = getPlatformConfig().getConfigValue(key);
            if(LibSysUtil.isEmpty(value)){
                throw new RemindException(BaseResultEnum.ERROR.getCode(),"platform.config.error");
            }
            PLATFORM_CONFIG.put(key,value);
        }
        return value;
    }

    public static void putAppConfig(String key,String value){
        APP_CONFIG.put(key,value);
        cacheAppConfig(key,value);
    }

    private static void cacheAppConfig(String key,String value){
        getRedisTemplate().opsForHash().put(ConfigRedisCacheKeyEnum.APP_CONFIG.getKey(),key,value);
    }

    public static void refresh(){
        init();
    }

    private static void init(){
        List<PlatformConfig> list = getPlatformConfig().getAllPlatformConfig();
        for (PlatformConfig info:list){
            PLATFORM_CONFIG.put(info.getConfigKey(),info.getConfigValue());
            if(info.getConfigLocal() == ConfigLocalEnum.ALL.getType() || info.getConfigLocal() == ConfigLocalEnum.APP.getType()){
                cacheAppConfig(info.getConfigKey(),info.getConfigValue());
            }
        }
        getRedisAppConfig();
    }

    private static PlatformConfigBiz getPlatformConfig(){
        PlatformConfigBiz platformConfigBiz = (PlatformConfigBiz) SpringContextUtil.getBean("platformConfigBiz");
        if(platformConfigBiz == null){
            throw new RuntimeException("platformConfigBiz not found");
        }
        return platformConfigBiz;
    }

    private static StringRedisTemplate getRedisTemplate(){
        return ResourceCache.getRedisTemplate();
    }

}
