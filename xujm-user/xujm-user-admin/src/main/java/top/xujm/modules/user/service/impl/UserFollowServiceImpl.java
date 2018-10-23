package top.xujm.modules.user.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xujm.core.utils.LibBeanUtil;
import top.xujm.modules.common.model.ResultAdminData;
import top.xujm.modules.common.utils.StringUtil;
import top.xujm.modules.user.model.UserFollow;
import top.xujm.modules.user.model.UserFollowExtend;
import top.xujm.modules.user.model.UserInfo;
import top.xujm.modules.user.repository.UserInfoEntityRepository;
import top.xujm.modules.user.service.UserFollowService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserFollowServiceImpl implements UserFollowService {


    @PersistenceContext
    private EntityManager em;
    @Autowired
    private UserInfoEntityRepository userInfoEntityRepository;

    /**
     * 关注列表
     * @param page
     * @param limit
     * @param account
     * @param toaccount
     * @return
     */
    @Override
    public ResultAdminData<List<UserFollowExtend>> selectUserFollow(int page, int limit, String account, String toaccount, String addTime) {
        String sql = "select new UserFollow(f.id, u.userId, f.toId, u.account, u.nickname, u.avatar, f.addTime, u.role, f.followState)" +
                " from UserFollow f,UserInfo u,UserInfo  u2 where f.userId = u.userId and f.toId = u2.userId ";
        if (StringUtils.isNotEmpty(account)) {
            sql += " and u.account = '" + account +"'";
        }
        if (StringUtils.isNotEmpty(toaccount)) {
            sql += " and u2.account = '" + toaccount +"'";
        }
        if (StringUtils.isNotEmpty(addTime)) {
            String[] times = StringUtil.subDate(addTime);
            sql += " and f.addTime >= " + times[0];
            sql += " and f.addTime <= " + times[1];
        }
        Query query = em.createQuery(sql);
        int total = query.getResultList().size();
        int offset = (page - 1) * limit;
        if(offset >= total) {
            offset = 0;
        }
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        //执行查询，返回的是查询属性值数组的集合
        List<UserFollow> objecArraytList = query.getResultList();
        em.close();
        List<Integer> toids = new ArrayList<>();
        for (UserFollow f : objecArraytList) {
            toids.add(f.getToId());
        }
        List<UserInfo> modules = getUserInfo(toids);
        List<UserFollowExtend> list = new ArrayList<>();
        UserFollowExtend userFollowExtend;
        for (UserFollow info : objecArraytList) {
            userFollowExtend = LibBeanUtil.entityCopy(info, UserFollowExtend.class);
            for (UserInfo m : modules) {
                if (info.getToId() == m.getUserId()) {
                    userFollowExtend.setToaccount(m.getAccount());
                    userFollowExtend.setTonickname(m.getNickname());
                }
            }
            list.add(userFollowExtend);
        }
        return new ResultAdminData(total,list);
    }

    private List<UserInfo> getUserInfo(List<Integer> toids) {
        List<UserInfo> modules = userInfoEntityRepository.selectUserInfoInUserId(toids);
        return modules;
    }
}
