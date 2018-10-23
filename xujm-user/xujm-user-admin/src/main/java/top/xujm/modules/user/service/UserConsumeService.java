package top.xujm.modules.user.service;

import top.xujm.modules.common.model.ResultAdminData;

import java.util.List;

public interface UserConsumeService {

    ResultAdminData<List> selectUserConsume(int page, int limit, String account, String toaccount, String addTime);
}
