package top.xujm.modules.config.service.impl;

import org.springframework.stereotype.Component;
import top.xujm.core.redis.CacheService;
import top.xujm.config.resource.ResourcesLang;

/**
 * @author Xujm
 */
@Component
public class LangCacheServiceImpl implements CacheService {
    @Override
    public void clear() {
        ResourcesLang.refresh();
    }
}
