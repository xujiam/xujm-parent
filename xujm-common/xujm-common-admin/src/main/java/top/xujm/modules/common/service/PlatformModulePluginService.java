package top.xujm.modules.common.service;

import top.xujm.common.core.model.ResultData;
import top.xujm.modules.common.model.PlatformModule;

import java.util.List;
import java.util.Map;

/**
 * @author ZhengYP
 */
public interface PlatformModulePluginService {

    ResultData<List<PlatformModule>> selectModulePlugin();

    void addModulePlugin(PlatformModule platformModulePlugin);

    void delModulePlugin(int id);

    ResultData<List<PlatformModule>> getModulePluginByCode(String pluginCode);

    Map<String, String>getModulePluginMapByCode(String pluginCode);
}
