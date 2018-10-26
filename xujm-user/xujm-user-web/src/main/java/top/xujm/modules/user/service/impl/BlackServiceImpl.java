package top.xujm.modules.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xujm.core.utils.LibDateUtil;
import top.xujm.modules.user.common.UserRemindException;
import top.xujm.modules.user.common.UserResultEnum;
import top.xujm.modules.user.model.UserBlack;
import top.xujm.modules.user.repository.BlackRepository;
import top.xujm.modules.user.service.BlackService;

import java.util.List;

/**
 * @author Xujm
 */
@Component
public class BlackServiceImpl extends UserBaseService implements BlackService {

    @Autowired
    private BlackRepository blackRepository;

    @Override
    public void pull(String account){
        int userId = getLoginUserId();
        int toId = getUserIdByAccount(account);
        if(userId == toId){
            throw new UserRemindException(UserResultEnum.BLACK_SELF);
        }
        if(verifyBlack(userId,toId)){
            throw new UserRemindException(UserResultEnum.BLACK_EXIST);
        }
        UserBlack userBlack = new UserBlack();
        userBlack.setUserId(userId);
        userBlack.setToId(toId);
        userBlack.setAddTime(LibDateUtil.getNowTime());
        blackRepository.save(userBlack);
    }

    @Override
    public void cancel(String account){
        int userId = getLoginUserId();
        int toId = getUserIdByAccount(account);
        if(!verifyBlack(userId,toId)){
            throw new UserRemindException(UserResultEnum.BLACK_NOT_EXIST);
        }
        blackRepository.deleteByUserIdAndToId(userId,toId);
    }

    @Override
    public List<UserBlack> list(){
        int userId = getLoginUserId();
        return blackRepository.selectBlackListByUserId(userId);
    }

    @Override
    public boolean verifyBlack(int userId, int toId){
        return blackRepository.findByUserIdAndToId(userId,toId) != null;
    }

    @Override
    public boolean verifyBlack(int userId, String account){
        return verifyBlack(userId,getUserIdByAccount(account));
    }

    @Override
    public void checkBlack(int userId,int toId){
        if(verifyBlack(userId,toId)){
            throw new UserRemindException(UserResultEnum.BLACK);
        }
    }

}
