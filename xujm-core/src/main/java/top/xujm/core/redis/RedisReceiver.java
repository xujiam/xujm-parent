package top.xujm.core.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Xujm
 */
@Component
public class RedisReceiver {

    private Logger logger = LoggerFactory.getLogger(RedisReceiver.class);

    @Autowired(required = false)
    private Map<String,CacheService> cacheServiceMap;

    public void receiveMessage(String message) {
        if(cacheServiceMap != null){
            getCacheServiceImpl(message).clear();
        }
    }

    private CacheService getCacheServiceImpl(String cacheCode){
        String cacheApi = String.format("%sCacheServiceImpl",cacheCode);
        CacheService cacheService = cacheServiceMap.get(cacheApi);
        if(cacheService == null){
            logger.error("CacheService not found:{}",cacheCode);
        }
        return cacheService;
    }

}
