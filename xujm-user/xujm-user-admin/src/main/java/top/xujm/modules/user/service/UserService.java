package top.xujm.modules.user.service;

import top.xujm.common.core.model.ResultData;
import top.xujm.modules.auth.model.AdminRole;
import top.xujm.modules.common.model.ResultAdminData;
import top.xujm.modules.user.model.UserAccountEntity;
import top.xujm.modules.user.model.UserAccountExtend;
import top.xujm.modules.user.model.UserInfo;

import java.util.List;

public interface UserService {

    ResultAdminData selectUserList(int page, int limit, String account, String nickname, String sex, String addTime);

    void editUser(int userId, String nickname, short insideRole, Byte sex, short role);

    void blackUser(int userId);

    void removeblack(int userId);

    List<UserAccountExtend> selectAccountList();

    List<AdminRole> selectAdminRole();

    void editAccount(int userId, String username, String roles);

    void updateUserNickname(String account, String username);

    List<UserInfo> getUserInfo(List<Integer> toids);

    ResultData<List<UserAccountEntity>>  selectUserById(int objectId);

    void editUserPwd(int userId, String newPwd);

    ResultData selectUserNum();
}
