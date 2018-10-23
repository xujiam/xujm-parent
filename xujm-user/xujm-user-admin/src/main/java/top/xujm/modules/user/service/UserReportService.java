package top.xujm.modules.user.service;


import top.xujm.modules.common.model.ResultAdminData;

import java.util.List;

/**
 * @author ZhengYP
 */
public interface UserReportService {

    ResultAdminData selectUserReport(int page, int limit, String account, String addTime, String moduleName);
}
