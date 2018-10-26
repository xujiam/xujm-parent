package top.xujm.modules.user.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import top.xujm.common.core.model.ListPage;
import top.xujm.common.core.model.ResultData;
import top.xujm.common.core.model.ResultMsg;
import top.xujm.core.base.BaseController;
import top.xujm.modules.user.model.*;
import top.xujm.modules.user.service.BlackService;
import top.xujm.modules.user.service.FollowService;
import top.xujm.modules.user.service.PocketService;
import top.xujm.modules.user.service.UserService;

import java.util.List;
import java.util.Map;

/**
 * @author Xujm
 */
@Api(value = "/user",tags = "用户模块")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @Autowired
    private PocketService pocketService;
    @Autowired
    private BlackService blackService;

    @ApiOperation(value="用户信息")
    @GetMapping("/info")
    public ResultData<ResultUserInfo> info(String account) {
        return new ResultData<>(userService.getResultUserInfo(account));
    }

    @ApiOperation(value="清除用户缓存")
    @GetMapping("/info/clear")
    public ResultMsg clearUserCache(String account) {
        userService.clearUserInfo(account);
        return new ResultMsg();
    }

    @ApiImplicitParam(name = "query",value = "搜索关键字")
    @ApiOperation(value = "搜索用户")
    @GetMapping("/search/user")
    public ResultData<List<ResultUserInfo>> searchUser(String query, ListPage listPage){
        return new ResultData<>(userService.getUserListByQuery(query,listPage));
    }

    @ApiOperation(value="修改用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickname", value = "昵称"),
            @ApiImplicitParam(name = "langCode", value = "语言"),
            @ApiImplicitParam(name = "sex", value = "性别(1男2女)"),
            @ApiImplicitParam(name = "avatar", value = "头像地址"),
            @ApiImplicitParam(name = "city", value = "用户所在地"),
            @ApiImplicitParam(name = "lng", value = "经度"),
            @ApiImplicitParam(name = "lat", value = "纬度"),
            @ApiImplicitParam(name = "birthday", value = "出生日期"),
            @ApiImplicitParam(name = "cid", value = "个推CID"),
            @ApiImplicitParam(name = "label", value = "用户标签(labelId以,隔开)"),
            @ApiImplicitParam(name = "signature", value = "个性签名"),
    })
    @PostMapping("/info/modify")
    public ResultMsg modify(@ApiIgnore @RequestParam Map<String,String> map) {
        userService.modify(map);
        return new ResultMsg();
    }


    @ApiOperation("关注")
    @PostMapping("/follow")
    public ResultMsg follow(String account){
        followService.follow(account);
        return new ResultMsg();
    }

    @ApiOperation("取消关注")
    @PostMapping("/follow/cancel")
    public ResultMsg cancel(String account){
        followService.cancel(account);
        return new ResultMsg();
    }

    @ApiOperation("关注列表")
    @GetMapping("/follow/list")
    public ResultData<List<UserFollow>> list(String account, ListPage listPage){
        List<UserFollow> list = followService.getFollowList(account,listPage);
        return new ResultData<>(list);
    }

    @ApiOperation("粉丝列表")
    @GetMapping("/fans/list")
    public ResultData<List<UserFollow>> fansList(String account, ListPage listPage){
        List<UserFollow> list = followService.getFansList(account,listPage);
        return new ResultData<>(list);
    }

    @ApiOperation("拉黑用户")
    @GetMapping("/black/pull")
    public ResultMsg pullBlack(String account){
        blackService.pull(account);
        return new ResultMsg();
    }

    @ApiOperation("取消拉黑")
    @GetMapping("/black/cancel")
    public ResultMsg cancelBlack(String account){
        blackService.cancel(account);
        return new ResultMsg();
    }

    @ApiOperation("拉黑列表")
    @GetMapping("/black/list")
    public ResultData<List<UserBlack>> blackList(){
        return new ResultData<>(blackService.list());
    }

    @ApiOperation("获取用户钱包信息")
    @GetMapping("/pocket")
    public ResultData<UserPocket> getUserPocket(){
        return new ResultData<>(pocketService.getUserPocket());
    }

    @ApiOperation("获取用户标签列表")
    @GetMapping("/labelList")
    public ResultData<List<UserLabel>> getUserLabelList(){
        return new ResultData<>(userService.getUserLabelList());
    }

    @ApiOperation("获取推荐用户列表")
    @GetMapping("/getRecommendUserList")
    public ResultData<List<UserBaseInfo>> getRecommendUserList(){
        return new ResultData<>(userService.getRecommendUserList());
    }

    @ApiOperation("发送消息")
    @GetMapping("/sendMsg")
    public ResultMsg sendMsg(String account, String msg){
        userService.pushMsg(account, msg);
        return new ResultMsg();
    }

}
