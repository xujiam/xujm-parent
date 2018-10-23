package top.xujm.generator.sms.common;

/**
 * @author Xujm
 */
public enum SmsResultEnum {

    /**
     * 发送失败
     */
    CAPTCHA_SEND_ERROR(1010,"security.captcha.send.error");


    private int code;

    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    SmsResultEnum(int code,String msg){
        this.code = code;
        this.message = msg;
    }

}
