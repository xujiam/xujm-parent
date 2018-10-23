package top.xujm.modules.security.common;

import top.xujm.core.base.BaseConstants;

/**
 * @author Xujm
 */
public interface SecurityConstants {

    /**
     * 模块名
     */
    String MODULE_NAME = "user";
    /**
     * 加上模块的表名前缀
     */
    String MODULE_PREFIX = BaseConstants.TABLE_PREFIX + BaseConstants.TABLE_LINK_SYMBOL + MODULE_NAME;

    String CONFIG_ACCOUNT_LENGTH = "user.account.length";

    String CONFIG_INVITE_CODE = "invite.code.config";

    String SERVER_IP = "server_ip";

    String SMS_ACCOUNT = "sms.account";

    String SMS_CAPTCHA = "sms.captcha";

    /**
     * security游客默认标识
     */
    String ANONYMOUS_USER = "anonymousUser";
}
