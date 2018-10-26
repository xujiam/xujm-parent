package top.xujm.modules.record.service;

import top.xujm.common.core.model.ResultData;
import top.xujm.modules.common.model.ResultAdminData;
import top.xujm.modules.record.model.RecordLogin;

import java.util.List;

/**
 * @author ZhengYP
 */
public interface RecordLoginService {

    ResultAdminData<List<RecordLogin>> selectRecordLogin(int page, int limit, String account, String nickname, String loginTime, String appCode);

    ResultData getLoginNum();
}
