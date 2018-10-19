package top.xujm.modules.config.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xujm.core.base.BaseConstants;
import top.xujm.modules.config.model.PlatformConfig;
import top.xujm.modules.config.repository.PlatformConfigRepository;

import java.util.List;

/**
 * @author Xujm
 */
@Component("platformConfigBiz")
public class PlatformConfigBiz {

    @Autowired
    private PlatformConfigRepository platformConfigRepository;

    public List<PlatformConfig> getAllPlatformConfig(){
        return platformConfigRepository.findAll();
    }

    public String getConfigValue(String configKey){
       PlatformConfig platformConfig = platformConfigRepository.findByConfigKey(configKey);
       if(platformConfig == null){
           return BaseConstants.DEFAULT_EMPTY;
       }
       return platformConfig.getConfigValue();
    }

}
