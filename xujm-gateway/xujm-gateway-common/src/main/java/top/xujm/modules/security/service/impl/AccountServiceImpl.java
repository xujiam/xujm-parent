package top.xujm.modules.security.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import top.xujm.core.enums.BooleanTypeEnum;
import top.xujm.core.utils.LibConverterUtil;
import top.xujm.core.utils.LibDateUtil;
import top.xujm.core.utils.LibSysUtil;
import top.xujm.generator.sms.SmsProcessor;
import top.xujm.generator.sms.captcha.CaptchaConfig;
import top.xujm.generator.sms.captcha.CaptchaGenerator;
import top.xujm.generator.sms.captcha.CaptchaTypeEnum;
import top.xujm.modules.security.biz.AccountBiz;
import top.xujm.modules.security.common.ResourceLoginMode;
import top.xujm.modules.security.common.SecurityRemindException;
import top.xujm.modules.security.common.SecurityResultEnum;
import top.xujm.modules.security.model.LoginMode;
import top.xujm.modules.security.model.UserAccount;
import top.xujm.modules.security.model.UserSms;
import top.xujm.modules.security.repository.LoginModeRepository;
import top.xujm.modules.security.repository.UserAccountRepository;
import top.xujm.modules.security.repository.UserSmsRepository;
import top.xujm.modules.security.service.AccountService;

import java.util.List;
import java.util.Map;

/**
 * @author Xujm
 */
@Component
public class AccountServiceImpl extends SecurityService implements AccountService {

    @Autowired
    private UserSmsRepository userSmsRepository;
    @Autowired
    private SmsProcessor smsProcessor;
    @Autowired
    private AccountBiz accountBiz;
    @Autowired
   private PasswordEncoder passwordEncoder;
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private LoginModeRepository loginModeRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 发送验证码
     * @param mobile    手机号码
     * @param areaCode  手机区号
     * @param type      验证码类型
     */
    @Override
    public void sendSmsCaptcha(String mobile, String areaCode, Byte type){
        if(!LibSysUtil.isMobile(mobile,areaCode)){
            throw new SecurityRemindException(SecurityResultEnum.MOBILE_ERROR);
        }
        checkCanSendSmsCaptcha(mobile,type,areaCode);
        String captcha = CaptchaGenerator.createCaptcha();
        String content = CaptchaGenerator.getCaptchaContent(CaptchaTypeEnum.getEnum(type),captcha);
        smsProcessor.send(mobile,content,areaCode);
        accountBiz.recordUserSms(getFullMobile(mobile,areaCode),captcha,type);
    }

    /**
     * 手机号是否可以发送短信
     */
    private void checkCanSendSmsCaptcha(String mobile, Byte type,String areaCode){
        mobile = getFullMobile(mobile,areaCode);
        CaptchaConfig captchaConfig = CaptchaGenerator.getCaptchaConfig();
        UserSms userSms = userSmsRepository.findFirstByMobileAndTypeOrderByIdDesc(mobile, type);
        if (userSms != null) {
            long diffTime = LibDateUtil.getDateTimeTickToSecond(userSms.getSendTime());
            //上一个验证码是否过期
            if (diffTime <= 60) {
                throw new SecurityRemindException(SecurityResultEnum.CAPTCHA_SEND_FAST
                        , LibConverterUtil.toString(captchaConfig.getExpireTime()));
            }
            List<UserSms> list = userSmsRepository.findAllByMobileAndSendTimeGreaterThanEqual(mobile,LibDateUtil.getCurDayTime());
            int size = list.size();
            //验证日发送量是否超限
            if (size >= captchaConfig.getDayNum()) {
                throw new SecurityRemindException(SecurityResultEnum.CAPTCHA_SEND_OVER);
            }
            //验证每小时发送量是否超限
            if (size >= captchaConfig.getHoursNum()) {
                long hoursAgoTime = LibDateUtil.curTimeAddHours(-1);
                int n = 0;
                for (UserSms info : list) {
                    if (info.getSendTime() >= hoursAgoTime) {
                        n++;
                    }
                }
                if(n >= captchaConfig.getHoursNum()){
                    throw new SecurityRemindException(SecurityResultEnum.CAPTCHA_SEND_OVER);
                }
            }
        }
    }

    /**
     * 验证验证码
     */
    @Override
    public void validateSmsCaptcha(String mobile, Byte type, String areaCode, String captcha){
        if(StringUtils.isEmpty(captcha)){
            throw new SecurityRemindException(SecurityResultEnum.CAPTCHA_GET_ERROR);
        }
        UserSms userSms = userSmsRepository.findFirstByMobileAndTypeOrderByIdDesc(getFullMobile(mobile,areaCode), type);
        if(userSms == null){
            throw new SecurityRemindException(SecurityResultEnum.CAPTCHA_NOT_EXIST);
        }
        //时间为0为永久验证码
        if(userSms.getSendTime() == 0){
            return;
        }
        CaptchaConfig captchaConfig = CaptchaGenerator.getCaptchaConfig();
        long diffTime = LibDateUtil.getDateTimeTickToSecond(userSms.getSendTime());
        //上一个验证码是否过期
        if (diffTime > captchaConfig.getExpireTime()) {
            throw new SecurityRemindException(SecurityResultEnum.CAPTCHA_EXPIRE);
        }
        if(!StringUtils.equals(userSms.getCaptcha(),captcha)){
            throw new SecurityRemindException(SecurityResultEnum.CAPTCHA_ERROR);
        }
    }

    private void validateSmsCaptcha(String mobile, CaptchaTypeEnum captchaTypeEnum, String areaCode, String captcha){
        validateSmsCaptcha(mobile, captchaTypeEnum.getType(),areaCode,captcha);
    }

    @Override
    public UserAccount registerMobile(String mobile, String password, String areaCode, String captcha) {
        validateSmsCaptcha(mobile,CaptchaTypeEnum.register,areaCode,captcha);
        UserAccount userAccount = userAccountRepository.findUserAccountByMobileAndAreaCode(mobile,areaCode);
        if(userAccount != null){
            throw new SecurityRemindException(SecurityResultEnum.MOBILE_REGISTER);
        }
        return saveUserAccount(null,mobile,password,areaCode,"mobile");
    }

    @Override
    public UserAccount registerUsername(String username, String password) {
        verifyUsername(username);
        return saveUserAccount(username,null,password,null,"username");
    }

    @Override
    public void verifyUsername(String username){
        UserAccount userAccount = userAccountRepository.findFirstByUsername(username);
        if(userAccount != null){
            throw new SecurityRemindException(SecurityResultEnum.USERNAME_REGISTER);
        }
    }

    private UserAccount saveUserAccount(String username,String mobile,String password,String areaCode,String registerCode){
        verifyPasswordFormat(password);
        //verifyLoginMode(registerCode);
        UserAccount user = new UserAccount();
        if(StringUtils.isNotEmpty(username)){
            user.setUsername(username);
        }
        if(StringUtils.isNotEmpty(mobile)){
            user.setMobile(mobile);
            user.setAreaCode(areaCode);
        }
        user.setPassword(passwordEncode(password));
        user.setIsBlack((byte)0);
        user.setRegisterCode(registerCode);
        user.setAddTime(LibDateUtil.getNowTime());
        return userAccountRepository.save(user);
    }

    @Override
    public void editPwdByOld(String oldPwd, String newPwd){
        verifyPasswordFormat(newPwd);
        int userId = getLoginUserId();
        UserAccount userAccount = userAccountRepository.findFirstByUserId(userId);
        if(userAccount == null || !passwordEncoder.matches(oldPwd,userAccount.getPassword())){
            throw new SecurityRemindException(SecurityResultEnum.PASSWORD_ERROR);
        }
        updatePwd(userId,newPwd);
    }

    @Override
    public void updatePwd(int userId, String newPwd){
        userAccountRepository.updateUserPassword(userId,passwordEncode(newPwd));
    }


    @Override
    public void editPwdByCaptcha(String mobile, String areaCode, String captcha, String newPwd){
        verifyPasswordFormat(newPwd);
        validateSmsCaptcha(mobile,CaptchaTypeEnum.password,areaCode,captcha);
        UserAccount userAccount = userAccountRepository.findFirstByMobile(mobile);
        if(userAccount == null){
            throw new SecurityRemindException(SecurityResultEnum.USER_NOT_EXIST);
        }
        updatePwd(userAccount.getUserId(),newPwd);
    }

    public void cancelBingMobile(String mobile, String captcha, String areaCode){
        int userId = getLoginUserId();
        if(!LibSysUtil.isMobile(mobile,areaCode)){
            throw new SecurityRemindException(SecurityResultEnum.MOBILE_ERROR);
        }
    }

    @Override
    public void bindMobile(String mobile, String captcha, String areaCode){
        int userId = getLoginUserId();
        if(!LibSysUtil.isMobile(mobile,areaCode)){
            throw new SecurityRemindException(SecurityResultEnum.MOBILE_ERROR);
        }
        validateSmsCaptcha(mobile,CaptchaTypeEnum.bing,areaCode,captcha);
        UserAccount userAccount = userAccountRepository.findByMobileAndAreaCode(mobile,areaCode);
        if(userAccount != null){
            throw new SecurityRemindException(SecurityResultEnum.MOBILE_EXIST);
        }
        userAccountRepository.updateUserMobile(userId,mobile,areaCode);
    }

    @Override
    public void cancelBindMobile(String mobile, String captcha, String areaCode){
        int userId = getLoginUserId();
        if(!LibSysUtil.isMobile(mobile,areaCode)){
            throw new SecurityRemindException(SecurityResultEnum.MOBILE_ERROR);
        }
        validateSmsCaptcha(mobile,CaptchaTypeEnum.cancel_bing,areaCode,captcha);
        if(bindAccountNum(userId) <= 1){
            throw new SecurityRemindException(SecurityResultEnum.BIND_LESS);
        }
        userAccountRepository.updateUserMobile(userId,"",areaCode);
    }

    private String passwordEncode(String password){
        return passwordEncoder.encode(password);
    }

    /**
     * 获得添加区号的手机号
     */
    private String getFullMobile(String mobile,String areaCode){
        return String.format("%s%s",areaCode,mobile);
    }

    /**
     * 验证密码格式
     * @param password 密码
     */
    private void verifyPasswordFormat(String password){
        int min = 6;
        int max = 16;
        int length = password.length();
        if(length < min || length > max){
            throw new SecurityRemindException(SecurityResultEnum.PASSWORD_FORMAT_ERROR);
        }
    }

    public int bindAccountNum(int userId){
        int bindAccountNum = 0;
        Map<String,Object> map = getUserAccountInfo(userId);
        //统计绑定第三方数量
        List<LoginMode> list = loginModeRepository.findByIsEnable(BooleanTypeEnum.TRUE.getType());
        for (LoginMode info:list){
            if(StringUtils.isNotEmpty(map.get(info.getProviderId()).toString())){
                bindAccountNum++;
            }
        }
        if(StringUtils.isNotEmpty(map.get("mobile").toString())){
            bindAccountNum++;
        }
        if(StringUtils.isNotEmpty(map.get("username").toString())){
            bindAccountNum++;
        }
        return bindAccountNum;
    }

    private Map<String,Object> getUserAccountInfo(int userId){
        String sql = String.format("select * from xujm_user_account where user_id = %d", userId);
        return jdbcTemplate.queryForMap(sql);
    }

    private void verifyLoginMode(String loginCode){
        ResourceLoginMode.getLoginMode(loginCode);
    }
 }
