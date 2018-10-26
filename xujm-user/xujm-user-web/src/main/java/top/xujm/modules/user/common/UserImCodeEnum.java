package top.xujm.modules.user.common;

/**
 * @author Xujm
 */
public enum UserImCodeEnum {

    /**
     * 系统消息
     */
    SYSTEM(100),
    NOTIFY_FANS(201),
    ALT_ME(200);

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    UserImCodeEnum(int code){
        this.code = code;
    }

}
