package top.xujm.modules.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xujm.common.core.model.ResultData;
import top.xujm.common.core.model.ResultMsg;
import top.xujm.modules.common.model.PlatformModule;
import top.xujm.modules.common.model.ResultAdminData;
import top.xujm.modules.common.service.PlatformConfigService;
import top.xujm.modules.common.service.PlatformModulePluginService;
import top.xujm.modules.config.model.PlatformConfig;

import java.util.List;

@RestController
@RequestMapping("/platform")
public class PlatformController {

    @Autowired
    private PlatformConfigService platformConfigService;
    @Autowired
    private PlatformModulePluginService platformModulePluginService;

    /**
     * 平台配置表
     * @return
     */
    @GetMapping("/config/list")
    public ResultAdminData<List<PlatformConfig>> configList(int page, int limit, String configKey, String configLocal, String configMark){
        return platformConfigService.selectConfigList(page, limit, configKey, configLocal, configMark);
    }

    /**
     * 添加配置
     */
    @PostMapping("addConfig")
    public ResultMsg addConfig(PlatformConfig platformConfig) {
        platformConfigService.addConfig(platformConfig);
        return new ResultMsg();
    }

    /**
     * 删除配置
     */
    @PostMapping("delConfig")
    public ResultMsg delConfig(int id) {
        platformConfigService.delConfig(id);
        return new ResultMsg();
    }

    /**
     * 模块组件扩展
     */
    @GetMapping("/module/list")
    public ResultData<List<PlatformModule>> moduleList(){
        return platformModulePluginService.selectModulePlugin();
    }

    /**
     * 添加模块组件扩展
     */
    @PostMapping("addModulePlugin")
    public ResultMsg addModulePlugin(PlatformModule platformModulePlugin) {
        platformModulePluginService.addModulePlugin(platformModulePlugin);
        return new ResultMsg();
    }

    /**
     * 删除模块组件扩展
     */
    @PostMapping("delModulePlugin")
    public ResultMsg delModulePlugin(int id) {
        platformModulePluginService.delModulePlugin(id);
        return new ResultMsg();
    }

    /**
     * 根据模块扩展组件查询对应模块
     */
    @GetMapping("/getModulePluginByCode")
    public ResultData<List<PlatformModule>> getModulePluginByCode(String pluginCode){
        return platformModulePluginService.getModulePluginByCode(pluginCode);
    }
}
