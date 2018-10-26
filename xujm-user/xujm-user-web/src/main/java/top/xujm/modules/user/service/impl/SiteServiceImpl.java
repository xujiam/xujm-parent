package top.xujm.modules.user.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.xujm.core.enums.BooleanTypeEnum;
import top.xujm.core.utils.LibConverterUtil;
import top.xujm.core.utils.LibDateUtil;
import top.xujm.modules.user.common.UserConstants;
import top.xujm.modules.user.common.UserRemindException;
import top.xujm.modules.user.common.UserResultEnum;
import top.xujm.modules.user.model.UserAdv;
import top.xujm.modules.user.model.UserFeedback;
import top.xujm.modules.user.model.UserReport;
import top.xujm.modules.user.repository.FeedbackRepository;
import top.xujm.modules.user.repository.UserAdvRepository;
import top.xujm.modules.user.repository.UserReportRepository;
import top.xujm.modules.user.service.SiteService;

import java.util.List;

/**
 * @author Xujm
 */
@Component
public class SiteServiceImpl extends UserBaseService implements SiteService {

    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private UserReportRepository userReportRepository;
    @Autowired
    private UserAdvRepository userAdvRepository;

    @Override
    public void feedback(String content, String contactWay){
        if(StringUtils.isEmpty(content)){
            throw new UserRemindException(UserResultEnum.FEEDBACK_CONTENT_EMPTY);
        }
        int userId = getUserId();
        UserFeedback userFeedback = new UserFeedback();
        userFeedback.setStatus((byte)2);
        userFeedback.setContent(content);
        userFeedback.setContactWay(contactWay);
        userFeedback.setUserId(userId);
        userFeedback.setAddTime(LibDateUtil.getNowTime());
        feedbackRepository.save(userFeedback);
    }

    @Override
    public void report(UserReport userReport){
        if(StringUtils.equals(userReport.getModuleName(), UserConstants.MODULE_NAME)){
            userReport.setObjectId(getUserIdByAccount(userReport.getReportObject()));
        }else{
            userReport.setObjectId(LibConverterUtil.toInt(userReport.getReportObject()));
        }
        int userId = getUserId();
        userReport.setUserId(userId);
        userReport.setAddTime(LibDateUtil.getNowTime());
        userReportRepository.save(userReport);
    }

    @Override
    public List<UserAdv> getAdvList(String position){
        return userAdvRepository.findByPositionAndIsShowOrderBySortsDesc(position, BooleanTypeEnum.TRUE.getType());
    }

}
