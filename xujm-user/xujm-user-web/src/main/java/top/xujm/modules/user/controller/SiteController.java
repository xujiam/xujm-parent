package top.xujm.modules.user.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import top.xujm.common.core.model.ResultData;
import top.xujm.common.core.model.ResultMsg;
import top.xujm.core.base.BaseController;
import top.xujm.modules.user.model.UserAdv;
import top.xujm.modules.user.model.UserReport;
import top.xujm.modules.user.service.MsgService;
import top.xujm.modules.user.service.SiteService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Xujm
 */
@Api(tags = "用户设置")
@RestController
@RequestMapping("/user")
public class SiteController extends BaseController {

    @Autowired
    private MsgService msgService;
    @Autowired
    private SiteService siteService;

    @ApiOperation(value="消息开关列表")
    @GetMapping("/msg/switchList")
    public ResultData<JSONObject> switchList() {
        return new ResultData<>(msgService.msgSwitchList());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "isClose",value = "是否关闭(1关闭0开启)"),
            @ApiImplicitParam(name = "imCode",value = "消息码(详情见说明文档)")
    })
    @ApiOperation(value="消息开关")
    @GetMapping("/msg/switch")
    public ResultMsg msgSwitch(int imCode, byte isClose) {
        msgService.msgSwitch(imCode,isClose);
        return new ResultMsg();
    }

    @ApiOperation(value="反馈")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content",value = "反馈内容"),
            @ApiImplicitParam(name = "contactWay",value = "联系方式")
    })
    @GetMapping("/feedback")
    public ResultMsg feedback(String content,String contactWay){
        siteService.feedback(content,contactWay);
        return new ResultMsg();
    }

    @ApiOperation(value="举报")
    @GetMapping("/report")
    public ResultMsg report(@Valid UserReport userReport, @ApiIgnore BindingResult result){
        throwException(result);
        siteService.report(userReport);
        return new ResultMsg();
    }

    @ApiOperation("广告列表")
    @ApiImplicitParam(name = "position",value = "广告位置(首页home,登录页login)")
    @GetMapping("/adv/list")
    public ResultData<List<UserAdv>> advList(String position){
        return new ResultData<>(siteService.getAdvList(position));
    }
}
