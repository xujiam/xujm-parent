package top.xujm.generator.sms.common;

import top.xujm.core.exception.RemindException;

/**
 * @author Xujm
 */
public class SmsRemindException extends RemindException {

    public SmsRemindException(int code, String msg) {
        super(code, msg);
    }

    public SmsRemindException(SmsResultEnum smsResultEnum) {
        super(smsResultEnum.getCode(), smsResultEnum.getMessage());
    }

    public SmsRemindException(int code, String msg, String[] replace) {
        super(code, msg, replace);
    }
}
