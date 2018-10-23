package top.xujm.generator.sms.captcha;

import top.xujm.modules.security.common.SecurityRemindException;
import top.xujm.modules.security.common.SecurityResultEnum;

/**
 * @author Xujm
 */
public enum CaptchaTypeEnum {
    /**
     * 注册验证码
     */
    register((byte) 1),
    /**
     * 登录验证码
     */
    login((byte)2),
    /**
     * 绑定验证码
     */
    bing((byte)3),
    /**
     * 修改密码验证码
     */
    password((byte)4),
    /**
     * 解绑验证码
     */
    cancel_bing((byte)5);

    private byte type;


    CaptchaTypeEnum(byte type){
        this.type = type;
    }

    public byte getType() {
        return type;
    }

    public static CaptchaTypeEnum getEnum(byte type){
        CaptchaTypeEnum captchaTypeEnum = null;
        CaptchaTypeEnum[] captchaType = CaptchaTypeEnum.values();
        for(CaptchaTypeEnum captcha:captchaType){
            if(captcha.getType() == type){
                captchaTypeEnum = captcha;
            }
        }
        if(captchaTypeEnum == null){
            throw new SecurityRemindException(SecurityResultEnum.CAPTCHA_TYPE_ERROR);
        }
        return captchaTypeEnum;
    }

}
