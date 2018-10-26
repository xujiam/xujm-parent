package top.xujm.modules.user.common;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import top.xujm.config.resource.ResourceConfig;

/**
 * @author Xujm
 */
@Component
public class UserApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        JSONObject visitorObj = JSONObject.parseObject(ResourceConfig.getConfig("visitor.push.user.info"));
        JSONObject systemObj = JSONObject.parseObject(ResourceConfig.getConfig("system.push.user.info"));
        ResourceConfig.putAppConfig("visitorAccount",visitorObj.getString("account"));
        ResourceConfig.putAppConfig("systemAccount",systemObj.getString("account"));
    }

}
