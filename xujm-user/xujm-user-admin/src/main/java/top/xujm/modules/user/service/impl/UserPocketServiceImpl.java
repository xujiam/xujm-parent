package top.xujm.modules.user.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xujm.modules.user.model.UserPocket;
import top.xujm.modules.user.repository.UserPocketEntityRepository;
import top.xujm.modules.user.service.UserPocketService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class UserPocketServiceImpl extends UserBaseService implements UserPocketService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserPocketEntityRepository userPocketEntityRepository;

    /**
     * 获取用户对应钱包信息
     * @param account
     * @param nickname
     * @return
     */
    @Override
    public List<UserPocket> getUserPocket(String account, String nickname) {
        if (StringUtils.isNotEmpty(account)) {
            return userPocketEntityRepository.selectUserPocketByAccount(account);
        }
        if (StringUtils.isNotEmpty(nickname)) {
            return userPocketEntityRepository.selectUserPocketByNickname(nickname);
        }
        return null;
    }

    @Override
    public void recharge(String account, String addNum) {
        int userId = getUserIdByAccount(account);
        System.out.println(userId);
    }

}
