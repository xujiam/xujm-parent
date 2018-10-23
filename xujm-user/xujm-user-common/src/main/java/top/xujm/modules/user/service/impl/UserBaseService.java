package top.xujm.modules.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import top.xujm.config.resource.ResourceConfig;
import top.xujm.core.base.BaseResultEnum;
import top.xujm.core.exception.RemindException;
import top.xujm.core.utils.LibConverterUtil;
import top.xujm.core.utils.LibRuleRandomUtil;
import top.xujm.core.utils.LibSysUtil;
import top.xujm.modules.security.common.SecurityConstants;
import top.xujm.modules.security.service.impl.SecurityService;

/**
 * @author Xujm
 */
public class UserBaseService extends SecurityService {

    String createAccount(int userId){
        String accountLength = ResourceConfig.getConfig(SecurityConstants.CONFIG_ACCOUNT_LENGTH);
        return LibRuleRandomUtil.numberToCode(userId, LibConverterUtil.toInt(accountLength));
    }

    protected int getUserIdByAccount(String account){
        if(LibSysUtil.isEmpty(account)){
            throw new RemindException(BaseResultEnum.DATA_ERROR);
        }
        return LibRuleRandomUtil.numberToId(account);
    }

    /**
     * 获得对方ID(若account是null则返回用户ID)
     * @param account 对方account
     */
    protected int getToIdByAccount(String account){
        if(StringUtils.isEmpty(account)){
            return getLoginUserId();
        }
        return getUserIdByAccount(account);
    }

    String createInviteCode(int userId){
        String inviteCode;
        String inviteConfig = ResourceConfig.getConfig(SecurityConstants.CONFIG_INVITE_CODE);
        JSONObject inviteObj = JSONObject.parseObject(inviteConfig);
        int length = inviteObj.getInteger("length");
        switch (inviteObj.getInteger("format")){
            case 2:
                inviteCode = LibRuleRandomUtil.letterToCode(userId,length);
                break;
            case 3:
                inviteCode = LibRuleRandomUtil.numberToCode(userId,length);
                break;
            default:
                inviteCode = LibRuleRandomUtil.allToCode(userId,length);
                break;
        }
        return inviteCode;
    }

    protected int getUserIdByInviteCode(String inviteCode){
        int userId;
        String inviteConfig = ResourceConfig.getConfig(SecurityConstants.CONFIG_INVITE_CODE);
        JSONObject inviteObj = JSONObject.parseObject(inviteConfig);
        switch (inviteObj.getInteger("format")){
            case 2:
                userId = LibRuleRandomUtil.letterToId(inviteCode);
                break;
            case 3:
                userId = LibRuleRandomUtil.numberToId(inviteCode);
                break;
            default:
                userId = LibRuleRandomUtil.allToId(inviteCode);
                break;
        }
        return userId;
    }

}
