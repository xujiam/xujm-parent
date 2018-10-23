package top.xujm.modules.security.common;

import top.xujm.core.base.BaseResultEnum;
import top.xujm.core.exception.RemindException;

/**
 * @author Xujm
 */
public class SecurityRemindException extends RemindException {

    public SecurityRemindException(SecurityResultEnum resultEnum) {
        super(resultEnum.getCode(), resultEnum.getMessage());
    }

    public SecurityRemindException(int code,String message) {
        super(code, message);
    }

    public SecurityRemindException(String message) {
        super(BaseResultEnum.ERROR.getCode(), message);
    }

    public SecurityRemindException(SecurityResultEnum resultEnum, String... replace) {
        super(resultEnum.getCode(), resultEnum.getMessage(),replace);
    }
}
