package top.xujm.modules.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xujm.common.core.model.ResultMsg;
import top.xujm.core.base.BaseController;
import top.xujm.modules.security.common.SecurityRemindException;
import top.xujm.modules.security.common.SecurityResultEnum;
import top.xujm.modules.security.service.AccountService;


/**
 * @author Xujm
 */
@RestController
@RequestMapping("/admin/user")
public class AccountController extends BaseController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register/mobile")
    public ResultMsg registerMobile(String mobile, String password, String captcha, String areaCode) {
        accountService.registerMobile(mobile, password, areaCode,captcha);
        return new ResultMsg();
    }

    @GetMapping("/signIn")
    public void signIn() {
        throw new SecurityRemindException(SecurityResultEnum.AUTH_ERROR);
    }


    @PostMapping("/editPwdByOld")
    public ResultMsg editPwdByOld(String oldPwd,String newPwd) {
        accountService.editPwdByOld(oldPwd,newPwd);
        return new ResultMsg();
    }

    @PostMapping("/editPwdByCaptcha")
    public ResultMsg editPwdByCaptcha(String mobile,String areaCode,String captcha,String newPwd) {
        accountService.editPwdByCaptcha(mobile,areaCode,captcha,newPwd);
        return new ResultMsg();
    }

    @PostMapping("/logout")
    public ResultMsg logout() {
        return new ResultMsg();
    }

}
