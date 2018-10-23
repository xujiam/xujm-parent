package top.xujm.modules.user.service;

import top.xujm.common.core.model.ResultData;
import top.xujm.modules.security.model.LoginMode;

import java.util.List;

/**
 * @author ZhengYP
 */
public interface UserCountService {


    ResultData selectRegisterDay(String addTime);
    ResultData selectRegisterDayTable(String addTime, String register);

    ResultData<List<LoginMode>> selectLoginMode();

    ResultData countlistRegisterWeek();
}
