package top.xujm.modules.security.common;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import top.xujm.common.core.constant.ConfigConstant;
import top.xujm.config.resource.ResourceConfig;
import top.xujm.core.base.BaseConstants;
import top.xujm.core.base.BaseResultEnum;
import top.xujm.core.utils.LibConverterUtil;
import top.xujm.modules.security.model.SecurityUser;

/**
 * @author Xujm
 */
public class LibSecurityUtil {

    public static SecurityUser getSecurityUser(){
        Authentication authentication = getAuthentication();
        if(authentication == null){
            return null;
        }
        Object principal = authentication.getPrincipal();
        if(principal instanceof SecurityUser){
            return  (SecurityUser) principal;
        }
        return null;
    }

    public static int getUserId(){
        SecurityUser securityUser = getSecurityUser();
        if(securityUser != null){
            return LibConverterUtil.toInt(securityUser.getUserId());
        }
        return 0;
    }

    /**
     * 游客调用直接报错
     */
    public static int getLoginUserId(){
        int userId = getUserId();
        if(userId == 0){
            throw new SecurityRemindException(SecurityResultEnum.AUTH_ERROR);
        }
        return userId;
    }


    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }


    public static JSONObject getResultJSON(String data){
        return getResultJSON(JSONObject.parseObject(data));
    }

    public static JSONObject getResultJSON(JSONObject jsonObject){
        JSONObject result = new JSONObject();
        result.put(BaseConstants.RESULT_CODE, BaseResultEnum.SUCCESS.getCode());
        if(LibConverterUtil.toBoolean(ResourceConfig.getConfig(ConfigConstant.dataEncrypt))){
            //result.put(BaseConstants.RESULT_ENCRYPT, WkUtils.encryString(jsonObject.toJSONString()));
        }else {
            result.put(BaseConstants.RESULT_DATA,jsonObject);
        }
        return result;
    }

}
