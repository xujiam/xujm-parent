package top.xujm.modules.security.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xujm.core.utils.LibDateUtil;
import top.xujm.modules.security.model.UserSms;
import top.xujm.modules.security.repository.UserSmsRepository;

/**
 * @author Xujm
 */
@Component
public class AccountBiz {

    @Autowired
    private UserSmsRepository userSmsRepository;

    public void recordUserSms(String mobile,String captcha,int type){
        UserSms userSms = new UserSms();
        userSms.setMobile(mobile);
        userSms.setCaptcha(captcha);
        userSms.setType((byte)type);
        userSms.setSendTime(LibDateUtil.getNowTime());
        userSmsRepository.save(userSms);
    }


}
