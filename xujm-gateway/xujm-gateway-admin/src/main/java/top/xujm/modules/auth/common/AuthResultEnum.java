package top.xujm.modules.auth.common;

/**
 * @author Xujm
 */
public enum AuthResultEnum {

    /**
     * 角色代号错误
     */
    ROLE_CODE_ERROR(1028,"admin.auth.role.code.error");

    private int code;

    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    AuthResultEnum(int code,String msg){
        this.code = code;
        this.message = msg;
    }

}
