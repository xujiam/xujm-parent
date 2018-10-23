package top.xujm.modules.user.service;

import top.xujm.modules.common.model.ResultAdminData;
import top.xujm.modules.user.model.UserFollowExtend;

import java.util.List;

public interface UserFollowService {

    ResultAdminData<List<UserFollowExtend>> selectUserFollow(int page, int limit, String account, String toaccount, String addTime);
}
