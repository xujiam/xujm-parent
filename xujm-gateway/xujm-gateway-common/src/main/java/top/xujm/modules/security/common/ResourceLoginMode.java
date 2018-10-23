package top.xujm.modules.security.common;

import top.xujm.core.utils.SpringContextUtil;
import top.xujm.modules.security.model.LoginMode;
import top.xujm.modules.security.repository.LoginModeRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Xujm
 */
public class ResourceLoginMode {

    private static Map<String, LoginMode> LOGIN_MODE = new HashMap<>();

    public static LoginMode getLoginMode(String providerId){
        if(LOGIN_MODE.size() == 0){
            List<LoginMode> list = getLoginModeRepository().findAll();
            for (LoginMode info:list){
                LOGIN_MODE.put(info.getProviderId(),info);
            }
        }
        LoginMode loginMode = LOGIN_MODE.get(providerId);
        if(loginMode == null){
            throw new RuntimeException("loginMode not found");
        }
        return loginMode;
    }


    private static LoginModeRepository getLoginModeRepository(){
        LoginModeRepository loginModeRepository = (LoginModeRepository) SpringContextUtil.getBean("loginModeRepository");
        if(loginModeRepository == null){
            throw new RuntimeException("loginModeRepository not found");
        }
        return loginModeRepository;
    }

}
