package top.xujm.modules.common.service;

import top.xujm.modules.common.model.ResultAdminData;
import top.xujm.modules.config.model.PlatformConfig;

import java.util.List;

public interface PlatformConfigService {

    ResultAdminData<List<PlatformConfig>> selectConfigList(int page, int limit, String configKey, String configLocal, String configMark);

    void addConfig(PlatformConfig platformConfig);

    void delConfig(int id);

    PlatformConfig selectConfig(String configKey);
}
