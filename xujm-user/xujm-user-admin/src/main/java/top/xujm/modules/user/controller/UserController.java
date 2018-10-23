package top.xujm.modules.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xujm.common.core.model.ResultData;
import top.xujm.common.core.model.ResultMsg;
import top.xujm.modules.auth.model.AdminRole;
import top.xujm.modules.common.model.ResultAdminData;
import top.xujm.modules.security.model.LoginMode;
import top.xujm.modules.user.model.*;
import top.xujm.modules.user.service.*;

import java.util.List;


/**
 * @author Xujm
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;
    @Autowired
    private FeedBackService feedBackService;
    @Autowired
    private UserPocketService userPocketService;
    @Autowired
    private UserFollowService userFollowService;
    @Autowired
    private UserConsumeTypeService userConsumeTypeService;
    @Autowired
    private UserConsumeService userConsumeService;
    @Autowired
    private UserIncomeTypeService userIncomeTypeService;
    @Autowired
    private UserIncomeService userIncomeService;
    @Autowired
    private UserReportService userReportService;
    @Autowired
    private UserCountService userCountService;

    /**
     * 用户列表
     */
    @GetMapping("/userinfo/list")
    public ResultAdminData<List<UserAccountEntity>> list(int page, int limit, String account, String nickname, String sex, String addTime){
        return userService.selectUserList(page,limit, account, nickname, sex,addTime);
    }

    /**
     * 编辑用户
     */
    @PostMapping("/editUser")
    public ResultMsg editUser(int userId, String nickname, short insideRole, Byte sex, short role) {
        userService.editUser(userId,nickname, insideRole, sex, role);
        return new ResultMsg();
    }

    /**
     * 拉黑用户
     */
    @PostMapping("/blackUser")
    public ResultMsg blackUser(int userId) {
        userService.blackUser(userId);
        return new ResultMsg();
    }

    /**
     * 洗白用户
     */
    @PostMapping("/removeblack")
    public ResultMsg removeblack(int userId) {
        userService.removeblack(userId);
        return new ResultMsg();
    }

    /**
     * 后台用户
     */
    @GetMapping("/account/list")
    public ResultData<List<UserAccountExtend>> accountList(){
        return new ResultData(userService.selectAccountList());
    }

    /**
     * 后台用户注册
     */
    @PostMapping("/registerMobile")
    public ResultMsg registerMobile(String mobile, String password, String areaCode, String captcha) {
        return new ResultMsg();
    }

    /**
     * 发送验证码
     */
    @PostMapping("/sendSmsCaptcha")
    public ResultMsg sendSmsCaptcha(String mobile, String areaCode ) {
        return new ResultMsg();
    }

    /**
     * 获取角色
     */
    @GetMapping("/getAdminRole")
    public ResultData<List<AdminRole>> getAdminRole(){
        return new ResultData(userService.selectAdminRole());
    }

    /**
     * 修改后台用户
     */
    @PostMapping("/editAccount")
    public ResultMsg editAccount(int userId,String username,String roles) {
        userService.editAccount(userId, username, roles);
        return new ResultMsg();
    }

    /**
     * 用户反馈
     */
    @GetMapping("/feedback/list")
    public ResultAdminData<List<UserFeedback>> feedBackList(int page, int limit, String account, String nickname, String addTime, String status){
        return feedBackService.selectFeedBackList(page,limit, account, nickname, addTime, status);
    }

    /**
     * 删除反馈信息
     */
    @PostMapping("/delFeedBack")
    public ResultMsg delFeedBack(int id) {
        feedBackService.delFeedBack(id);
        return new ResultMsg();
    }

    /**
     * 审阅反馈信息
     */
    @PostMapping("/reviewFeedBack")
    public ResultMsg reviewFeedBack(int id) {
        feedBackService.reviewFeedBack(id);
        return new ResultMsg();
    }

    /**
     * 忽略反馈信息
     */
    @PostMapping("/ignoreFeedBack")
    public ResultMsg ignoreFeedBack(int id) {
        feedBackService.ignoreFeedBack(id);
        return new ResultMsg();
    }

    /**
     * 后台充值列表
     */
    @GetMapping("/pocket/list")
    public ResultData<List<UserPocket>> pocketList(String account, String nickname) {
        return new ResultData(userPocketService.getUserPocket(account,nickname));
    }

    /**
     * 后台充值
     */
    @PostMapping("/recharge")
    public ResultMsg recharge(String account,String addNum) {
        userPocketService.recharge(account, addNum);
        System.out.println(account);
        System.out.println(addNum);
        return new ResultMsg();
    }

    /**
     * 获取关注列表
     */
    @GetMapping("/follow/list")
    public ResultAdminData<List<UserFollowExtend>> followList(int page , int limit, String account, String toaccount, String addTime) {
        return userFollowService.selectUserFollow(page, limit, account, toaccount, addTime);
    }

    /**
     * 获取消费类型--配置
     */
    @GetMapping("/consumetype/list")
    public ResultData<List<UserConsumeType>> consumetypeList() {
        return new ResultData(userConsumeTypeService.selectUserConsumeType());
    }

    /**
     * 添加消费类型
     */
    @PostMapping("/addConsumetype")
    public ResultMsg addConsumetype(UserConsumeType userConsumeType) {
        userConsumeTypeService.addConsumetype(userConsumeType);
        return new ResultMsg();
    }

    /**
     * 消费类型是否启用
     */
    @PostMapping("/editisEnable")
    public ResultMsg editisEnable(int id,Byte isEnable) {
        userConsumeTypeService.editisEnable(id,isEnable);
        return new ResultMsg();
    }

    /**
     * 删除消费类型
     */
    @PostMapping("/delConsumetype")
    public ResultMsg delConsumetype(int id) {
        userConsumeTypeService.delConsumetype(id);
        return new ResultMsg();
    }

    /**
     * 获取用户消费记录
     */
    @GetMapping("/consume/list")
    public ResultAdminData<List> consumeList(int page ,int limit, String account, String toaccount,String addTime) {
        return userConsumeService.selectUserConsume(page, limit, account, toaccount, addTime);
    }

    /**
     * 获取收入类型--配置
     */
    @GetMapping("/incometype/list")
    public ResultData<List<UserIncomeType>> incomeTypeList() {
        return new ResultData(userIncomeTypeService.selectUserIncomeType());
    }

    /**
     * 添加收入类型
     */
    @PostMapping("/addIncometype")
    public ResultMsg addIncometype(UserIncomeType userIncomeType) {
        userIncomeTypeService.addIncometype(userIncomeType);
        return new ResultMsg();
    }

    /**
     * 收入类型是否启用
     */
    @PostMapping("/editIncomeisEnable")
    public ResultMsg editIncomeisEnable(int id,Byte isEnable) {
        userIncomeTypeService.editIncomeisEnable(id,isEnable);
        return new ResultMsg();
    }

    /**
     * 获取用户收入记录
     */
    @GetMapping("/income/list")
    public ResultAdminData<List> incomeList(int page ,int limit, String account, String toaccount,String addTime) {
        return userIncomeService.selectUserConsume(page, limit, account, toaccount, addTime);
    }

    /**
     * 获取用户举报列表
     */
    @GetMapping("/report/list")
    public ResultAdminData reportList(int page ,int limit, String account,String addTime, String moduleName) {
        return userReportService.selectUserReport(page ,limit, account, addTime, moduleName);
    }

    /**
     * 根据id获取用户
     */
    @GetMapping("/user")
    public ResultData<List<UserAccountEntity>>  user(int objectId) {
        System.out.println(objectId);
        return userService.selectUserById(objectId);
    }

    /**
     * 获取每日注册统计
     */
    @GetMapping("/count/listRegister")
    public ResultData countListRegisterDay(String addTime) {
        System.out.println(addTime);
        return userCountService.selectRegisterDay(addTime);
    }

    /**
     * 获取每日注册统计
     */
    @GetMapping("/count/listRegister_Day")
    public ResultData countlistRegisterDay(String addTime, String register) {
        return userCountService.selectRegisterDayTable(addTime,register);
    }

    /**
     * 获取每月注册统计
     */
    @GetMapping("/count/listRegister_Month")
    public ResultData countlistRegisterMonth(String addTime, String register) {
        return userCountService.selectRegisterDayTable(addTime,register);
    }


    /**
     * 获取注册类型
     */
    @GetMapping("/getLoginMode")
    public ResultData<List<LoginMode>> getLoginMode() {
        return userCountService.selectLoginMode();
    }

    /**
     * 修改后台密码
     */
    @PostMapping("/editUserPwd")
    public ResultMsg editUserPwd(int userId, String newPwd) {
        userService.editUserPwd(userId,newPwd);
        return new ResultMsg();
    }

    /**
     * 获取最近一周的用户注册量
     */
    @GetMapping("/count/listRegister_Week")
    public ResultData countlistRegisterWeek() {
        return userCountService.countlistRegisterWeek();
    }

    /**
     * 获取用户量
     */
    @GetMapping("/getUserNum")
    public ResultData getUserNum() {
        return userService.selectUserNum();
    }
}
