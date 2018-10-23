package top.xujm.modules.user.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xujm.core.utils.LibBeanUtil;
import top.xujm.modules.common.model.ResultAdminData;
import top.xujm.modules.common.utils.DateUtils;
import top.xujm.modules.common.utils.StringUtil;
import top.xujm.modules.user.model.UserConsume;
import top.xujm.modules.user.model.UserConsumeExtend;
import top.xujm.modules.user.model.UserConsumeType;
import top.xujm.modules.user.model.UserInfo;
import top.xujm.modules.user.repository.UserConsumeTypeRepository;
import top.xujm.modules.user.repository.UserInfoEntityRepository;
import top.xujm.modules.user.service.UserConsumeService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Component
public class UserConsumeServiceImpl implements UserConsumeService {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private UserInfoEntityRepository userInfoEntityRepository;
    @Autowired
    private UserConsumeTypeRepository userConsumeTypeRepository;

    /**
     * 获取用户消费记录
     * @param page
     * @param limit
     * @param account
     * @param toaccount
     * @param addTime
     * @return
     */
    @Override
    public ResultAdminData<List> selectUserConsume(int page, int limit, String account, String toaccount, String addTime) {
        String sql = "select new UserConsume(c.id, u.userId, u.account, u.nickname, u.avatar, c.toId, c.costNum, c.incomeNum, c.consumeCode, c.moduleId, c.extendId, c.addTime)" +
                " from UserConsume c,UserInfo u,UserInfo  u2 where c.userId = u.userId and c.toId = u2.userId ";
        if (StringUtils.isNotEmpty(account)) {
            sql += " and u.account = '" + account +"'";
        }
        if (StringUtils.isNotEmpty(toaccount)) {
            sql += " and u2.account = '" + toaccount +"'";
        }
        if (StringUtils.isNotEmpty(addTime)) {
            String[] times = StringUtil.subDate(addTime);
            sql += " and c.addTime >= " + times[0];
            sql += " and c.addTime <= " + times[1];
        } else {
            sql += " and c.addTime >= "+ StringUtil.timeToString(DateUtils.getLastWeek() + "000000");
        }
        sql += " order by c.addTime desc ";
        Query query = em.createQuery(sql);
        int total = query.getResultList().size();
        int offset = (page - 1) * limit;
        if(offset >= total) {
            offset = 0;
        }
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        //执行查询，返回的是查询属性值数组的集合
        List<UserConsume> objecArraytList = query.getResultList();
        em.close();
        List<Integer> toids = new ArrayList<>();
        for (UserConsume c : objecArraytList) {
            toids.add(c.getToId());
        }
        if(toids.size() != 0) {
            List<UserInfo> modules = getUserInfo(toids);                        // 得到本次查询对方用户的用户信息
            Map<String, String> userConsumeTypeMap = getUserConsumeTypeMap();   // 得到消费类型数据
            List<UserConsumeExtend> list = new ArrayList<>();
            UserConsumeExtend userConsumeExtend;
            for (UserConsume info : objecArraytList) {
                userConsumeExtend = LibBeanUtil.entityCopy(info, UserConsumeExtend.class);
                for (UserInfo m : modules) {
                    if (info.getToId() == m.getUserId()) {
                        userConsumeExtend.setToaccount(m.getAccount());
                        userConsumeExtend.setTonickname(m.getNickname());
                    }
                }
                list.add(userConsumeExtend);
            }
            Iterator<Map.Entry<String, String>> it = userConsumeTypeMap.entrySet().iterator();
            while  (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                for (UserConsumeExtend info : list) {
                    if (info.getConsumeCode().equals(entry.getKey())) {
                        info.setConsumeMark(entry.getValue());
                    }
                }
            }
            return new ResultAdminData<>(total,list);
        }
        return new ResultAdminData<>(0,null);
    }


    private List<UserInfo> getUserInfo(List<Integer> toids) {
        List<UserInfo> modules = userInfoEntityRepository.selectUserInfoInUserId(toids);
        return modules;
    }

    private Map<String, String> getUserConsumeTypeMap() {
        List<UserConsumeType> userConsumeTypeList =  userConsumeTypeRepository.findAll();
        Map<String, String>  userConsumeTypeMap = new HashMap<>();
        for (UserConsumeType info : userConsumeTypeList) {
            userConsumeTypeMap.put(info.getConsumeCode(), info.getMark());
        }
        return userConsumeTypeMap;
    }
}
