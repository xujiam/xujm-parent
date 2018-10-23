package top.xujm.modules.security.service;


import top.xujm.modules.security.model.UserAccount;

/**
 * @author Xujm
 */
public interface AccountService {

    /**
     * 发送验证码
     * @param mobile 手机号
     * @param areaCode 手机区号
     * @param type 验证码类型
     * @return JSONObject
     */
    void sendSmsCaptcha(String mobile, String areaCode, Byte type);


    /**
     * 验证验证码
     * @param mobile 手机号
     * @param type 类型
     * @param areaCode 手机区号
     * @param captcha 验证码
     */
    void validateSmsCaptcha(String mobile, Byte type, String areaCode, String captcha);

    /**
     * 手机号注册
     * @param mobile 手机号
     * @param password 密码
     * @param areaCode 手机区号
     * @param captcha 验证码
     * @return UserAccount
     */
    UserAccount registerMobile(String mobile, String password, String areaCode, String captcha);

    UserAccount registerUsername(String username, String password);

    void verifyUsername(String username);

    void editPwdByOld(String oldPwd, String newPwd);

    void editPwdByCaptcha(String mobile, String areaCode, String captcha, String newPwd);

    void bindMobile(String mobile, String captcha, String areaCode);

    void cancelBindMobile(String mobile, String captcha, String areaCode);

    void updatePwd(int userId, String newPwd);
}
