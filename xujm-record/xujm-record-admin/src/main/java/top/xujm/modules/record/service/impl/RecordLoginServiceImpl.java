package top.xujm.modules.record.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xujm.common.core.model.ResultData;
import top.xujm.modules.common.model.ResultAdminData;
import top.xujm.modules.common.utils.DateUtils;
import top.xujm.modules.common.utils.StringUtil;
import top.xujm.modules.record.model.RecordLogin;
import top.xujm.modules.record.repository.RecordLoginEntityRepository;
import top.xujm.modules.record.service.RecordLoginService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author ZhengYP
 */
@Component
public class RecordLoginServiceImpl implements RecordLoginService {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private RecordLoginEntityRepository recordLoginEntityRepository;

    /**
     * 获取登录日志列表
     */
    @Override
    public ResultAdminData<List<RecordLogin>> selectRecordLogin(int page, int limit, String account, String nickname, String loginTime, String appCode) {
        boolean condition = StringUtils.isEmpty(account) && StringUtils.isEmpty(nickname) && StringUtils.isEmpty(appCode) && StringUtils.isEmpty(loginTime);
                String sql = "select new RecordLogin (u.userId, u.account, u.nickname, u.avatar, u.role,r.id,r.clientIp,r.loginTime,r.appCode,r.loginCode,r.deviceModel,r.imei,r.appVersion)" +
                "from RecordLogin r,UserInfo u where r.userId = u.userId";

        if (StringUtils.isNotEmpty(account)) {
            sql += " and u.account like '%"+account + "%'";
        }
        if (StringUtils.isNotEmpty(nickname)) {
            sql += " and u.nickname like '%"+nickname + "%'";
        }
        if (StringUtils.isNotEmpty(appCode)) {
            sql += " and r.appCode ='"+appCode + "'";
        }
        if (StringUtils.isNotEmpty(loginTime)) {
            String[] times = StringUtil.subDate(loginTime);
            sql += " and r.loginTime >= " + times[0];
            sql += " and r.loginTime <= " + times[1];
        } else if (condition){
            sql += " and r.loginTime >= "+ StringUtil.timeToString(DateUtils.now() + "000000");
        }
        sql += " order by r.loginTime desc";
        //创建原生SQL查询QUERY实例
        Query query =  em.createQuery(sql);
        int total = query.getResultList().size();
        int offset = (page - 1) * limit;
        if(offset >= total) {
            offset = 0;           //如果查询结果少于开始记录，则返回第一页显示
        }
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        //执行查询，返回的是查询属性值数组的集合
        List<RecordLogin> objecArraytList = query.getResultList();
        em.close();
        return new ResultAdminData(total,objecArraytList);



    }

    /**
     * 获取今日登录人数
     */
    @Override
    public ResultData getLoginNum() {
        String loginTime = StringUtil.timeToString(DateUtils.now()) + "000000";
        int num = recordLoginEntityRepository.selectLoginNum(loginTime);
        return new ResultData(num);
    }
}
