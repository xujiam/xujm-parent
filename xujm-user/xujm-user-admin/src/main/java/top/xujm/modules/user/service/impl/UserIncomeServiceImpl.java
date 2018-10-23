package top.xujm.modules.user.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xujm.core.utils.LibBeanUtil;
import top.xujm.modules.common.model.ResultAdminData;
import top.xujm.modules.common.utils.DateUtils;
import top.xujm.modules.common.utils.StringUtil;
import top.xujm.modules.user.model.UserIncome;
import top.xujm.modules.user.model.UserIncomeExtend;
import top.xujm.modules.user.model.UserIncomeType;
import top.xujm.modules.user.repository.UserIncomeTypeRepository;
import top.xujm.modules.user.service.UserIncomeService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Component
public class UserIncomeServiceImpl implements UserIncomeService {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private UserIncomeTypeRepository userIncomeTypeRepository;

    /**
     * 获取用户收入记录
     * @param page
     * @param limit
     * @param account
     * @param toaccount
     * @param addTime
     * @return
     */
    @Override
    public ResultAdminData<List> selectUserConsume(int page, int limit, String account, String toaccount, String addTime) {
        String sql = "select new UserIncome(i.id, u.userId, u.account, u.nickname, u.avatar, i.incomeNum, i.incomeCode, i.moduleId, i.extendId, i.addTime)" +
                " from UserIncome i,UserInfo u where i.userId = u.userId";
        if (StringUtils.isNotEmpty(account)) {
            sql += " and u.account = '" + account +"'";
        }
        if (StringUtils.isNotEmpty(addTime)) {
            String[] times = StringUtil.subDate(addTime);
            sql += " and i.addTime >= " + times[0];
            sql += " and i.addTime <= " + times[1];
        } else {
            sql += " and i.addTime >= "+ StringUtil.timeToString(DateUtils.getLastWeek() + "000000");
        }
        sql += " order by i.addTime desc ";
        Query query = em.createQuery(sql);
        int total = query.getResultList().size();
        int offset = (page - 1) * limit;
        if(offset >= total) {
            offset = 0;
        }
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        //执行查询，返回的是查询属性值数组的集合
        List<UserIncome> objecArraytList = query.getResultList();
        em.close();
        Map<String, String> userIncomeTypeMap = getUserConsumeTypeMap();   // 得到收入类型数据
        List<UserIncomeExtend> list = new ArrayList<>();
        UserIncomeExtend userIncomeExtend;
        for (UserIncome info : objecArraytList) {
            userIncomeExtend = LibBeanUtil.entityCopy(info, UserIncomeExtend.class);
            Iterator<Map.Entry<String, String>> it = userIncomeTypeMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                if (info.getIncomeCode().equals(entry.getKey())) {
                    userIncomeExtend.setIncomeMark(entry.getValue());
                }
            }
            list.add(userIncomeExtend);
        }
        return new ResultAdminData<>(total,list);
    }

    private Map<String, String> getUserConsumeTypeMap() {
        List<UserIncomeType> userIncomeTypeList =  userIncomeTypeRepository.findAll();
        Map<String, String>  userIncomeTypeMap = new HashMap<>();
        for (UserIncomeType info : userIncomeTypeList) {
            userIncomeTypeMap.put(info.getIncomeCode(), info.getMark());
        }
        return userIncomeTypeMap;
    }
}
