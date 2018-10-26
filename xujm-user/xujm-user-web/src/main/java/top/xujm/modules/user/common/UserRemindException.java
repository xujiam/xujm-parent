package top.xujm.modules.user.common;

import top.xujm.core.exception.RemindException;

/**
 * @author Xujm
 */
public class UserRemindException extends RemindException {

    public UserRemindException(UserResultEnum userResultEnum) {
        super(userResultEnum.getCode(),userResultEnum.getMessage());
    }

    public UserRemindException(int code,String msg) {
        super(code,msg);
    }

}
