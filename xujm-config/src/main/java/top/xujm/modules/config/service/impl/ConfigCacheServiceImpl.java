package top.xujm.modules.config.service.impl;

import org.springframework.stereotype.Component;
import top.xujm.core.redis.CacheService;
import top.xujm.config.resource.ResourceConfig;

/**
 * @author Xujm
 */
@Component
public class ConfigCacheServiceImpl implements CacheService {

    @Override
    public void clear() {
        ResourceConfig.refresh();
    }
}
