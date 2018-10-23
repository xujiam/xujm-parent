package top.xujm.modules.user.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import top.xujm.modules.common.model.ResultAdminData;
import top.xujm.modules.common.utils.StringUtil;
import top.xujm.modules.user.model.UserFeedback;
import top.xujm.modules.user.repository.UserFeedbackEntityRepository;
import top.xujm.modules.user.service.FeedBackService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component
public class FeedBackServiceImpl implements FeedBackService {

    @Autowired
    private RestTemplate restTemplate;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserFeedbackEntityRepository userFeedbackEntityRepository;


    /**
     * 用户反馈
     * @param page
     * @param limit
     * @param account
     * @param nickname
     * @return
     */
    @Override
    public ResultAdminData<List<UserFeedback>> selectFeedBackList(int page, int limit, String account, String nickname, String addTime, String status) {
        String sql = "select new UserFeedback (f.id,u.userId,u.account,u.nickname,u.avatar, f.content, f.contactWay, f.status, f.addTime)" +
                " from UserFeedback f ,UserInfo u where u.userId = f.userId ";
        if (StringUtils.isNotEmpty(account)) {
            sql += " and u.account like '%"+account + "%'";
        }
        if (StringUtils.isNotEmpty(nickname)) {
            sql += " and u.nickname like '%"+nickname + "%'";
        }
        if (StringUtils.isNotEmpty(status)) {
            sql += " and f.status ="+status;
        }
        if (StringUtils.isNotEmpty(addTime)) {
            String[] times = StringUtil.subDate(addTime);
            sql += " and f.addTime >= " + times[0];
            sql += " and f.addTime <= " + times[1];
        }
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
        List<UserFeedback> objecArraytList = query.getResultList();
        em.close();
        return new ResultAdminData<>(total,objecArraytList);
    }

    /**
     * 删除反馈信息
     * @param id 反馈信息id
     */
    @Override
    public void delFeedBack(int id) {
        UserFeedback userFeedback = this.userFeedbackEntityRepository.findFirstById(id);
        if (userFeedback != null) {
            userFeedbackEntityRepository.delete(userFeedback);
        }
    }

    /**
     * 审阅反馈信息
     * @param id    反馈ID
     */
    @Override
    public void reviewFeedBack(int id) {
        UserFeedback userFeedback = this.userFeedbackEntityRepository.findFirstById(id);
        if (userFeedback != null) {
            userFeedbackEntityRepository.reviewFeedBack(id);
        }
    }

    /**
     * 忽略反馈信息
     * @param id    反馈ID
     */
    @Override
    public void ignoreFeedBack(int id) {
        UserFeedback userFeedback = this.userFeedbackEntityRepository.findFirstById(id);
        if (userFeedback != null) {
            userFeedbackEntityRepository.ignoreFeedBack(id);
        }
    }
}
