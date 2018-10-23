package top.xujm.modules.user.common;

import top.xujm.config.resource.ResourceConfig;
import top.xujm.core.base.BaseConstants;
import top.xujm.core.utils.LibConverterUtil;

/**
 * @author Xujm
 */
public interface UserConstants {

    /**
     * 模块名
     */
    String MODULE_NAME = "user";
    /**
     * 加上模块的表名前缀
     */
    String MODULE_PREFIX = BaseConstants.TABLE_PREFIX + BaseConstants.TABLE_LINK_SYMBOL + MODULE_NAME;

    boolean SERVER_SEND_MSG = LibConverterUtil.toBoolean(ResourceConfig.getConfig("server.send.msg"));

}
