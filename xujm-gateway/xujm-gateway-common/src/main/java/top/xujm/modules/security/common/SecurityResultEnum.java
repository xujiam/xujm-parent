package top.xujm.modules.security.common;

/**
 * @author Xujm
 */
public enum SecurityResultEnum {

    /**
     * 登录失效
     */
    AUTH_ERROR(1,"security.auth.error"),
    /**
     * 刷新token失效
     */
    REFRESH_TOKEN_ERROR(3,"security.auth.error"),

    /**
     * 手机号错误
     */
    MOBILE_ERROR(1002,"security.mobile.error"),
    /**
     * 验证码不存在
     */
    CAPTCHA_NOT_EXIST(1003,"security.captcha.not.exist"),
    /**
     * 验证码不能为空
     */
    CAPTCHA_GET_ERROR(1004,"security.captcha.is.empty"),
    /**
     * 验证码错误
     */
    CAPTCHA_ERROR(1005,"security.captcha.error"),
    /**
     * 验证码类型错误
     */
    CAPTCHA_TYPE_ERROR(1006,"security.captcha.type.error"),
    /**
     * 验证码发送太快
     */
    CAPTCHA_SEND_FAST(1007,"security.captcha.send.fast"),
    /**
     * 验证码发送超限
     */
    CAPTCHA_SEND_OVER(1008,"security.captcha.send.over"),
    /**
     * 验证码过期
     */
    CAPTCHA_EXPIRE(1009,"security.captcha.expire"),

    /**
     * 用户不存在
     */
    USER_NOT_EXIST(1020,"security.user.not.exist"),

    /**
     * 手机号存在
     */
    MOBILE_REGISTER(1021,"security.mobile.register"),
    /**
     * 密码格式有误
     */
    PASSWORD_FORMAT_ERROR(1022,"security.password.format.error"),
    /**
     * 账号禁用
     */
    ACCOUNT_DISABLE(1023,"security.account.disable"),

    /**
     * 密码有误
     */
    PASSWORD_ERROR(1024,"security.password.error"),

    /**
     * 账号或密码有误
     */
    ACCOUNT_PASSWORD_ERROR(1025,"security.account.password.error"),
    /**
     * 绑定账号已经存在
     */
    ACCOUNT_BIND_EXIST(1026,"security.account.bind.exist"),
    /**
     * 绑定账号失败
     */
    ACCOUNT_BIND_ERROR(1027,"security.account.bind.error"),
    /**
     * 手机号存在
     */
    MOBILE_EXIST(1028,"security.mobile.exist"),
    /**
     * 绑定账号缺失
     */
    BIND_LESS(1029,"security.bind.account.less"),

    /**
     * 账号存在
     */
    USERNAME_REGISTER(1030,"security.username.register");

    private int code;

    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    SecurityResultEnum(int code,String msg){
        this.code = code;
        this.message = msg;
    }

}
