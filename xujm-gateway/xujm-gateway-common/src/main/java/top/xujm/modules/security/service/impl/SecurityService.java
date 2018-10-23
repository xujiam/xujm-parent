package top.xujm.modules.security.service.impl;

import org.springframework.security.core.Authentication;
import top.xujm.core.base.BaseService;
import top.xujm.modules.security.common.LibSecurityUtil;
import top.xujm.modules.security.model.SecurityUser;

/**
 * @author Xujm
 */
public class SecurityService extends BaseService {

    protected SecurityUser getSecurityUser(){
        return LibSecurityUtil.getSecurityUser();
    }

    protected int getUserId(){
        return LibSecurityUtil.getUserId();
    }

    /**
     * 游客调用直接报错
     */
    protected int getLoginUserId(){
        return LibSecurityUtil.getLoginUserId();
    }


    protected Authentication getAuthentication() {
        return LibSecurityUtil.getAuthentication();
    }

}
