package top.xujm.modules.user.service;

import com.alibaba.fastjson.JSONObject;
import top.xujm.common.core.model.ListPage;
import top.xujm.modules.user.model.ResultUserInfo;
import top.xujm.modules.user.model.UserBaseInfo;
import top.xujm.modules.user.model.UserLabel;

import java.util.List;
import java.util.Map;

/**
 * @author Xujm
 */
public interface UserService {

    /**
     * 获得用户信息
     */
    ResultUserInfo getResultUserInfo(String account);

    List<UserBaseInfo> getRecommendUserList();

    void modify(Map<String, String> map);

    UserBaseInfo getUserBaseInfo(int userId);

    List<ResultUserInfo> getUserListByQuery(String query, ListPage listPage);

    String getUserInfoByUserId(int userId, String field);

    void altUser(int userId, String account, String altCode, int objectId);

    void altUser(int userId, String altCode, int objectId, JSONObject altUserObject);

    String getUserLangCode(String account);

    String getUserLangCode(int userId);

    void clearUserInfo(String account);

    /**
     * 获得用户标签列表
     */
    List<UserLabel> getUserLabelList();

    ResultUserInfo getUserInfo(String account);

    String updateAltUserContent(String content);

    void pushMsg(String account, String msg);

}
