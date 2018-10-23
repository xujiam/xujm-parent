package top.xujm.modules.user.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import top.xujm.modules.common.model.ResultAdminData;
import top.xujm.modules.common.utils.DateUtils;
import top.xujm.modules.common.utils.StringUtil;
import top.xujm.modules.user.model.UserReport;
import top.xujm.modules.user.model.UserReportExtend;
import top.xujm.modules.user.service.UserReportService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author ZhengYP
 */
@Component
public class UserReportServiceImpl implements UserReportService {
    @PersistenceContext
    private EntityManager em;

    /**
     * 获取用户举报列表
     */
    @Override
    public ResultAdminData<List<UserReportExtend>> selectUserReport(int page , int limit, String account, String addTime, String moduleName) {
        boolean condition = StringUtils.isEmpty(account) && StringUtils.isEmpty(addTime) && StringUtils.isEmpty(moduleName);
        String sql = "select new UserReport(r.id, u.userId, u.account, u.nickname, u.avatar, r.objectId, r.moduleName, r.reportMsg, r.addTime)" +
                "from UserReport r,UserInfo u where r.userId = u.userId ";
        if (StringUtils.isNotEmpty(account)) {
            sql += " and u.account like '%"+account + "%'";
        }
        if (StringUtils.isNotEmpty(moduleName)) {
            sql += " and r.moduleName ='" + moduleName+"'";
        }
        if (StringUtils.isNotEmpty(addTime)) {
            String[] times = StringUtil.subDate(addTime);
            sql += " and r.addTime >= " + times[0];
            sql += " and r.addTime <= " + times[1];
        } else if (condition){
            sql += " and r.addTime >= "+ StringUtil.timeToString(DateUtils.getLastWeek() + "000000");
        }
        sql += " order by r.addTime desc";
        Query query = em.createQuery(sql);
        int total = query.getResultList().size();
        int offset = (page - 1) * limit;
        if(offset >= total) {
            offset = 0;           //如果查询结果少于开始记录，则返回第一页显示
        }
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        List<UserReport> objectArrayList  = query.getResultList();
        return new ResultAdminData(total,objectArrayList);
    }
}
