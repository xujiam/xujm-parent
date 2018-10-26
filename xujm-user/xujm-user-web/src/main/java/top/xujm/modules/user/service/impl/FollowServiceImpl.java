package top.xujm.modules.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.xujm.common.core.model.ListPage;
import top.xujm.core.page.OffsetPageRequest;
import top.xujm.core.utils.LibDateUtil;
import top.xujm.modules.user.biz.UserBiz;
import top.xujm.modules.user.common.UserFollowStateEnum;
import top.xujm.modules.user.common.UserImCodeEnum;
import top.xujm.modules.user.common.UserRemindException;
import top.xujm.modules.user.common.UserResultEnum;
import top.xujm.modules.user.model.UserFollow;
import top.xujm.modules.user.repository.FollowRepository;
import top.xujm.modules.user.service.FollowService;
import top.xujm.modules.user.service.MsgService;
import top.xujm.msg.model.ImBaseInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xujm
 */
@Component
public class FollowServiceImpl extends UserBaseService implements FollowService {

    @Autowired
    private UserBiz userBiz;
    @Autowired
    private FollowRepository followRepository;
    @Autowired(required = false)
    private MsgService msgService;
    /**
     * 关注
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void follow(String account){
        int userId = getLoginUserId();
        int toId = getUserIdByAccount(account);
        //不能关注自己
        if(toId == userId){
            throw new UserRemindException(UserResultEnum.FOLLOW_SELF);
        }
        //是否存在这个用户
        userBiz.verifyUserExist(toId);
        //是否已关注
        if(getFollowState(userId,toId) != 0){
            throw new UserRemindException(UserResultEnum.FOLLOW_EXIST);
        }
        byte state = UserFollowStateEnum.FOLLOW.getState();
        //通过更新检测对方是否关注自己，关注则状态置为互关注
        int r = followRepository.updateUserFollowState(toId,userId, UserFollowStateEnum.MUTUAL_FOLLOW.getState());
        if(r > 0){
            state = UserFollowStateEnum.MUTUAL_FOLLOW.getState();
        }
        UserFollow record = new UserFollow();
        record.setUserId(userId);
        record.setToId(toId);
        record.setFollowState(state);
        record.setAddTime(LibDateUtil.getNowTime());
        followRepository.save(record);
        userBiz.updateUserFansNum(toId,1);
        userBiz.updateUserFollowNum(userId,1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancel(String account){
        int userId = getLoginUserId();
        int toId = getUserIdByAccount(account);
        UserFollow userFollow = followRepository.findFirstByUserIdAndToId(userId,toId);
        if(userFollow != null){
            followRepository.deleteById(userFollow.getId());
            if(userFollow.getFollowState() == UserFollowStateEnum.MUTUAL_FOLLOW.getState()){
                followRepository.updateUserFollowState(toId,userId, UserFollowStateEnum.FOLLOW.getState());
            }
            userBiz.updateUserFansNum(toId,-1);
            userBiz.updateUserFollowNum(userId,-1);
        }
    }

    @Override
    public List<UserFollow> getFollowList(String account, ListPage listPage){
        int toId = getToIdByAccount(account);
        return followRepository.selectFollowList(toId,new OffsetPageRequest(listPage));
    }

    @Override
    public List<UserFollow> getFansList(String account, ListPage listPage){
        int userId = getUserId();
        int toId = getToIdByAccount(account);
        List<UserFollow> list = followRepository.selectFansList(toId,new OffsetPageRequest(listPage));
        List<UserFollow> resultList = new ArrayList<>();
        for (UserFollow info:list){
            if(userId != 0){
                info.setFollowState(getFollowState(userId,info.getUserId()));
            }
            resultList.add(info);
        }
        return resultList;
    }

    @Override
    public List<Integer> getFollowUserIdList(int userId){
        return followRepository.selectFollowUserIdList(userId);
    }


    @Override
    public byte getFollowState(int userId, int toId){
        if(userId == 0 || toId == 0 || userId == toId){
            return UserFollowStateEnum.NOT_FOLLOW.getState();
        }
        UserFollow userFollow = followRepository.findFirstByUserIdAndToId(userId,toId);
        if(userFollow == null){
            return UserFollowStateEnum.NOT_FOLLOW.getState();
        }
        return userFollow.getFollowState();
    }

    @Override
    @Async
    public void notifyFans(int userId, String msg, ImBaseInfo imContent) {
        if(msgService != null){
            List<String> list = followRepository.selectFansCidList(userId);
            msgService.pushListUserMsg(userId,list, UserImCodeEnum.NOTIFY_FANS.getCode(),msg,imContent);
        }
    }

}
