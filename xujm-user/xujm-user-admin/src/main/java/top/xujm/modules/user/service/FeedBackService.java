package top.xujm.modules.user.service;

import top.xujm.modules.common.model.ResultAdminData;
import top.xujm.modules.user.model.UserFeedback;

import java.util.List;

public interface FeedBackService {

    ResultAdminData<List<UserFeedback>> selectFeedBackList(int page, int limit, String account, String nickname, String addTime, String status);

    void delFeedBack(int id);

    void reviewFeedBack(int id);

    void ignoreFeedBack(int id);
}
