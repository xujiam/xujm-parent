package top.xujm.modules.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xujm.common.core.model.ResultData;
import top.xujm.modules.common.model.PlatformModule;
import top.xujm.modules.common.repository.PlatformModulePluginRepository;
import top.xujm.modules.common.service.PlatformModulePluginService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhengYP
 */
@Component
public class PlatformModulePluginServiceImpl implements PlatformModulePluginService {


    @Autowired
    private PlatformModulePluginRepository platformModulePluginRepository;

    @Override
    public ResultData<List<PlatformModule>> selectModulePlugin() {
        return new ResultData(platformModulePluginRepository.findAll());
    }

    @Override
    public void addModulePlugin(PlatformModule platformModulePlugin) {
        platformModulePluginRepository.save(platformModulePlugin);
    }

    @Override
    public void delModulePlugin(int id) {
        PlatformModule p =  platformModulePluginRepository.findById(id).get();
        if (p != null) {
            platformModulePluginRepository.delete(p);
        }
    }

    @Override
    public ResultData<List<PlatformModule>> getModulePluginByCode(String pluginCode) {
        return new ResultData(platformModulePluginRepository.selectModulePluginByCode(pluginCode));
    }


    @Override
    public Map<String, String> getModulePluginMapByCode(String pluginCode) {
        Map<String,String> map = new HashMap<>(0);
        List<PlatformModule> list = platformModulePluginRepository.selectModulePluginByCode(pluginCode);
        for (PlatformModule info:list){
            map.put(info.getModuleName(),info.getModuleValue());
        }
        return map;
    }
}
