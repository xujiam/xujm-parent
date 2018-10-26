package top.xujm.modules.user.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xujm.common.core.model.ResultData;
import top.xujm.modules.common.utils.StringUtil;
import top.xujm.modules.security.model.LoginMode;
import top.xujm.modules.security.repository.LoginModeRepository;
import top.xujm.modules.user.repository.UserInfoEntityRepository;
import top.xujm.modules.user.service.UserCountService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhengYP
 */
@Component
public class UserCountServiceImpl implements UserCountService {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private LoginModeRepository loginModeRepository;
    @Autowired
    private UserInfoEntityRepository userInfoEntityRepository;

    /**
     * 获取每日注册统计(图表)
     */
    @Override
    public ResultData selectRegisterDay(String addTime) {
        Map<String, Object> loginModeMap = new HashMap<>();
        List<LoginMode> loginModeList = loginModeRepository.findAll();
        String sql = "SELECT DATE_FORMAT(add_time,'%Y-%m-%d') as 'date',";
       /* String sql = "";*/
        for (LoginMode info : loginModeList) {
            sql += "IFNULL(SUM(CASE WHEN register_code='"+info.getProviderId()+"' THEN 1 END),0) as " + info.getProviderId() + ",";
            loginModeMap.put(info.getProviderId(), info.getProviderName());
        }
        sql += "COUNT(1) FROM xujm_user_account u WHERE 1=1";
        if (StringUtils.isNotEmpty(addTime)) {
            String[] times = StringUtil.subDate(addTime);
            sql += " and u.add_time >= " + times[0];
            sql += " and u.add_time <= " + times[1];
        }
        sql += " GROUP BY date ORDER BY date desc";
        Query query  = em.createNativeQuery(sql);
        /*sql += "COUNT(1) as num";*/
        /*List<Map<String, Object>> map = userInfoEntityRepository.selectRegisterDay(sql);*/
        List<Map<String, Object>> maplist = query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        em.close();
        Map<String, Object> map = new HashMap<>();
        map.put("maplist",maplist);
        map.put("loginMode",loginModeMap);
        return new ResultData(map);
    }

    /**
     * 获取注册类型
     */
    @Override
    public ResultData<List<LoginMode>> selectLoginMode() {
        return new ResultData(loginModeRepository.findAll());
    }

    /**
     * 获取每日注册统计(表格)
     */
    @Override
    public ResultData selectRegisterDayTable(String addTime, String register) {
        String sql = "";
        List<LoginMode> loginModeList = loginModeRepository.findAll();
        if ("day".equals(register)){
            sql = "SELECT DATE_FORMAT(add_time,'%Y-%m-%d') as 'date',";
        } else if ("month".equals(register)) {
            sql = "SELECT DATE_FORMAT(add_time,'%Y-%m') as 'date',";
        }
        for (LoginMode info : loginModeList) {
            sql += "IFNULL(SUM(CASE WHEN register_code='"+info.getProviderId()+"' THEN 1 END),0) as " + info.getProviderId() + ",";
        }
        sql += "COUNT(1) FROM xujm_user_account u WHERE 1=1";
        if (StringUtils.isNotEmpty(addTime)) {
            String[] times = new String[2];
            if ("day".equals(register)){
                times = StringUtil.subDate(addTime);
            } else if ("month".equals(register)) {
                times = StringUtil.subDateMonth(addTime);
            }
            sql += " and u.add_time >= " + times[0];
            sql += " and u.add_time <= " + times[1];
        }
        sql += " GROUP BY date ORDER BY date desc";
        Query query  = em.createNativeQuery(sql);
        List<Map<String, Object>> maplist = query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        em.close();
        return new ResultData(maplist);
    }

    /**
     * 获取最近一周的用户注册量
     */
    @Override
    public ResultData countlistRegisterWeek() {
        // String lastWeek = StringUtil.timeToString(DateUtils.getLastWeek()) + "000000";
        String lastWeek = "20180504000000";
        String lastWeek2 = "20180511000000";
        String sql = "SELECT DATE_FORMAT(add_time,'%m-%d') as 'date',COUNT(*) as 'num' FROM xujm_user_info WHERE add_time >= " + lastWeek;
        sql += " and add_time <= " + lastWeek2;
        sql += " GROUP BY date ";
        Query query = em.createNativeQuery(sql);
            List<Map<String, Object>> maplist = query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
        em.close();
        return new ResultData(maplist);
    }
}
