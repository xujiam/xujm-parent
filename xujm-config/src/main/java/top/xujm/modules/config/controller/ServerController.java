package top.xujm.modules.config.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xujm.core.base.BaseController;
import top.xujm.core.redis.ResourceCache;
import top.xujm.core.utils.LibSysUtil;
import top.xujm.modules.config.biz.AppVersionBiz;

import java.util.List;

/**
 * @author Xujm
 */
@RestController
@RequestMapping("/server")
public class ServerController extends BaseController {

    @Autowired
    private AppVersionBiz appVersionBiz;
    @Autowired(required = false)
    private ScheduledTask scheduledTask;

    @GetMapping("/config/refresh")
    public JSONObject configRefresh(){
        ResourceCache.clear("config");
        return LibSysUtil.getResultJSON();
    }


//    @GetMapping("/getAppConfig")
//    public ResultData<JSONObject> getAppConfig(String appCode, String appVersion){
//        JSONObject object = new JSONObject();
//        JSONObject appConfig = ResourceConfig.getAppConfig();
//        if(appConfig != null){
//            AppVersion appInfo = appVersionBiz.verifyAppVersion(appCode,appVersion);
//            if(appInfo != null){
//                object.put("appCode",appInfo.getAppCode());
//                object.put("appSize",appInfo.getAppSize());
//                object.put("appUrl",appInfo.getAppUrl());
//                object.put("forceType",appInfo.getForceType());
//                object.put("updateMsg",appInfo.getUpdateMsg());
//            }
//            object.putAll(appConfig);
//        }
//        return new ResultData<>(object);
//    }
//
//    @GetMapping("/stopCron")
//    public ResultMsg stopCron(String scheduledCode){
//        scheduledTask.stopCron(scheduledCode);
//        return new ResultMsg();
//    }
//
//    @GetMapping("/restartCron")
//    public ResultMsg restartCron(String scheduledCode,String cron){
//        scheduledTask.restartCron(scheduledCode,cron);
//        return new ResultMsg();
//    }
//
//    @GetMapping("/cronList")
//    public ResultData<List<PlatformScheduled>> cronList(){
//        return new ResultData<>(scheduledTask.getCronList());
//    }

}
