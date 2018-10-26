package top.xujm.modules.record.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xujm.common.core.model.ResultData;
import top.xujm.modules.common.model.ResultAdminData;
import top.xujm.modules.record.model.RecordLogin;
import top.xujm.modules.record.service.RecordLoginService;

import java.util.List;

/**
 * @author ZhengYP
 */
@RestController
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordLoginService recordLoginService;

    /**
     * 获取登录日志列表
     */
    @GetMapping("/recordlogin/list")
    public ResultAdminData<List<RecordLogin>> recordLoginList(int page, int limit, String account, String nickname, String loginTime, String appCode) {
        return recordLoginService.selectRecordLogin(page, limit, account, nickname, loginTime, appCode);
    }

    /**
     * 获取今日登录人数
     */
    @GetMapping("/getLoginNum")
    public ResultData getLoginNum() {
        return recordLoginService.getLoginNum();
    }
}
