package top.xujm.modules.oss.common;

import top.xujm.core.exception.RemindException;

/**
 * @author Xujm
 */
public class OssRemindException extends RemindException {

    public OssRemindException(OssResultEnum resultEnum) {
        super(resultEnum.getCode(), resultEnum.getMessage());
    }

    public OssRemindException(OssResultEnum resultEnum, String... replace) {
        super(resultEnum.getCode(), resultEnum.getMessage(),replace);
    }

}
