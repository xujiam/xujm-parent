package top.xujm.modules.user.service;

import top.xujm.common.core.model.ListPage;
import top.xujm.modules.user.model.UserFollow;
import top.xujm.msg.model.ImBaseInfo;

import java.util.List;

/**
 * @author Xujm
 */
public interface FollowService {

    /**
     * 关注
     * @param account 关注对象
     */
    void follow(String account);

    /**
     * 取消关注
     * @param account 取消对象
     */
    void cancel(String account);

    /**
     * 关注列表
     * @param account 用户账号
     * @param listPage 分页
     * @return List<UserFollow>
     */
    List<UserFollow> getFollowList(String account, ListPage listPage);

    List<UserFollow> getFansList(String account, ListPage listPage);


    /**
     * 关注用户ID列表
     * @param userId 用户ID
     * @return List<Integer>
     */
    List<Integer> getFollowUserIdList(int userId);

    /**
     * 获取关注列表(0未关注1关注2互关注)
     * @param userId 用户ID
     * @param toId 对方ID
     * @return int
     */
    byte getFollowState(int userId, int toId);


    /**
     * 通知粉丝
     * @param userId 用户ID
     * @param msg 消息
     * @param imContent IM其他信息
     */
    void notifyFans(int userId, String msg, ImBaseInfo imContent);


}
