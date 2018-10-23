package top.xujm.modules.oss.common;

import top.xujm.core.base.BaseConstants;

/**
 * @author Xujm
 */
public interface OssConstants {


    String OSS_CONFIG = "oss.config";

    String MODULE_NAME = "oss";

    /**
     * 加上模块的表名前缀
     */
    String MODULE_PREFIX = BaseConstants.TABLE_PREFIX + BaseConstants.TABLE_LINK_SYMBOL + MODULE_NAME;

}
