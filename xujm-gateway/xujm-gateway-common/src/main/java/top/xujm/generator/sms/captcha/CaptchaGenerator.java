package top.xujm.generator.sms.captcha;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.RandomStringUtils;
import top.xujm.config.resource.ResourceConfig;
import top.xujm.core.language.Language;
import top.xujm.modules.security.common.SecurityConstants;
import top.xujm.modules.security.common.SecurityRemindException;
import top.xujm.modules.security.common.SecurityResultEnum;

/**
 * @author Xujm
 * 验证码工具
 */
public class CaptchaGenerator {


    public static String createCaptcha(){
        CaptchaConfig captchaConfig = getCaptchaConfig();
        return RandomStringUtils.randomNumeric(captchaConfig.getLength());
    }


    public static CaptchaConfig getCaptchaConfig(){
        String captchaConfig = ResourceConfig.getConfig(SecurityConstants.SMS_CAPTCHA);
        return JSON.parseObject(captchaConfig,CaptchaConfig.class);
    }

    /**
     * 获得短信内容
     * @param captchaTypeEnum captchaTypeEnum
     * @return String
     */
    public static String getCaptchaContent(CaptchaTypeEnum captchaTypeEnum,String captcha){
        String content = String.format("【%s】", Language.getMsg("platform.name"));
        switch (captchaTypeEnum) {
            case login:
                content += Language.getMsg("security.captcha.login");
                break;
            case bing:
                content += Language.getMsg("security.captcha.bing");
                break;
            case password:
                content += Language.getMsg( "security.captcha.password");
                break;
            case register:
                content += Language.getMsg("security.captcha.register");
                break;
            case cancel_bing:
                content += Language.getMsg("security.captcha.cancel_bing");
                break;
            default:
                throw new SecurityRemindException(SecurityResultEnum.CAPTCHA_TYPE_ERROR);
        }
        return String.format(content,captcha);
    }


}
