package top.xujm.modules.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import top.xujm.common.core.model.ListPage;
import top.xujm.config.resource.ResourcesLang;
import top.xujm.core.base.BaseConstants;
import top.xujm.core.enums.BooleanTypeEnum;
import top.xujm.core.exception.RemindException;
import top.xujm.core.language.Language;
import top.xujm.core.page.OffsetPageRequest;
import top.xujm.core.utils.LibConverterUtil;
import top.xujm.core.utils.LibSysUtil;
import top.xujm.modules.security.model.SecurityUser;
import top.xujm.modules.user.biz.UserBiz;
import top.xujm.modules.user.common.UserImCodeEnum;
import top.xujm.modules.user.common.UserRemindException;
import top.xujm.modules.user.common.UserResourceUtil;
import top.xujm.modules.user.common.UserResultEnum;
import top.xujm.modules.user.model.*;
import top.xujm.modules.user.repository.*;
import top.xujm.modules.user.service.FollowService;
import top.xujm.modules.user.service.MsgService;
import top.xujm.modules.user.service.UserService;
import top.xujm.msg.model.ImBasePushInfo;

import java.util.*;

/**
 * @author Xujm
 */
@Component
public class UserServiceImpl extends UserBaseService implements UserService {

    @Autowired
    private UserBiz userBiz;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private FollowService followService;
    @Autowired
    private UserExtendKeyRepository userExtendKeyRepository;
    @Autowired
    private UserExtendRepository userExtendRepository;
    @Autowired
    private UserLabelRepository userLabelRepository;
    @Autowired
    private MsgService msgService;

    @Override
    public ResultUserInfo getResultUserInfo(String account){
        ResultUserInfo resultUserInfo = getUserInfo(account);
        resultUserInfo.setLabel(getUserLabelList(userBiz.getUserExtendInfoByUserId(resultUserInfo.getUserId(),"label")));
        return resultUserInfo;
    }

    @Override
    public ResultUserInfo getUserInfo(String account){
        ResultUserInfo resultUserInfo = new ResultUserInfo();
        //如果account为空查询本身,否则根据account查询
        if(StringUtils.isEmpty(account)){
            resultUserInfo.copy(getUserInfoByUserId(getLoginUserId()));
        }else {
            resultUserInfo.copy(getUserInfoByAccount(account));
        }
        resultUserInfo.setFansNum(followRepository.selectFansNumByUserId(resultUserInfo.getUserId()));
        resultUserInfo.setFollowNum(followRepository.selectFollowNumByUserId(resultUserInfo.getUserId()));
        resultUserInfo.setFollowState(followService.getFollowState(getUserId(),resultUserInfo.getUserId()));
        return resultUserInfo;
    }

    @Override
    public UserBaseInfo getUserBaseInfo(int userId){
        UserBaseInfo userBaseInfo = userBiz.getUserBaseInfoByUserId(userId);
        userBaseInfo.setRoleName(UserResourceUtil.getUserRoleName(userBaseInfo.getRole()));
        return userBaseInfo;
    }

    @Override
    public List<UserBaseInfo> getRecommendUserList(){
        return userRepository.selectRecommendUserList();
    }

    @Override
    public String getUserLangCode(String account){
        return getUserLangCode(getUserIdByAccount(account));
    }

    @Override
    public String getUserLangCode(int userId){
        return userBiz.getUserInfoByUserId(userId,"langCode");
    }

    @Override
    public List<ResultUserInfo> getUserListByQuery(String query, ListPage listPage) {
        int userId = getUserId();
        List<UserInfo> list = userRepository.selectSearchUserList(query,new OffsetPageRequest(listPage));
        List<ResultUserInfo> resultList = new ArrayList<>();
        ResultUserInfo resultUserInfo;
        for (UserInfo info:list){
            resultUserInfo = new ResultUserInfo();
            resultUserInfo.copy(info);
            if(userId > 0){
                resultUserInfo.setFollowState(followService.getFollowState(userId,info.getUserId()));
            }
            resultList.add(resultUserInfo);
        }
        return resultList;
    }

    @Async
    @Override
    public void altUser(int userId, String account, String altCode, int objectId){
        String msg = Language.getMsg(String.format("user.alt.%s.msg",altCode),getUserLangCode(account));
        ImBasePushInfo imAltUser = new ImBasePushInfo();
        imAltUser.setSourceCode(altCode);
        imAltUser.setObjectId(objectId);
        msgService.pushSingleUserMsg(userId,getUserIdByAccount(account), UserImCodeEnum.ALT_ME.getCode(),msg,imAltUser);
    }

    @Async
    @Override
    public void altUser(int userId, String altCode, int objectId, JSONObject altUserObject){
        if(altUserObject != null){
            //如果有@用户则发送推送
            for (Map.Entry<String,Object> entry:altUserObject.entrySet()){
                altUser(userId,entry.getKey(),altCode,objectId);
            }
        }
    }

    @Override
    public String updateAltUserContent(String content){
        if(StringUtils.isEmpty(content)){
            return null;
        }
        JSONObject contentObj = JSONObject.parseObject(content);
        contentObj.put("user",updateAltUserNickname(contentObj.getJSONObject("user")));
        return contentObj.toString();
    }

    private JSONObject updateAltUserNickname(JSONObject altUserObject){
        if(altUserObject != null){
            //如果有@用户则发送推送
            for (Map.Entry<String,Object> entry:altUserObject.entrySet()){
                altUserObject.put(entry.getKey(),userBiz.getUserInfoByUserId(getUserIdByAccount(entry.getKey()),"nickname"));
            }
        }
        return altUserObject;
    }

    @Override
    public List<UserLabel> getUserLabelList(){
        int userId = getUserId();
        String[] labels = null;
        if(userId > 0){
            String label = userBiz.getUserExtendInfoByUserId(userId,"label");
            if(!LibSysUtil.isEmpty(label)){
                labels = label.split(",");
            }
        }
        List<UserLabel> userLabelList = userLabelRepository.findByIsShowOrderBySortsDesc(BooleanTypeEnum.TRUE.getType());
        List<UserLabel> resultList = new ArrayList<>();
        for (UserLabel info:userLabelList){
            if(labels != null){
                if(Arrays.binarySearch(labels, LibConverterUtil.toString(info.getLabelId()))>=0){
                    info.setSelect(true);
                }
            }
            info.setLabelName(ResourcesLang.getLang(info.getLabelName()));
            resultList.add(info);
        }
        return resultList;
    }

    public List<String> getUserLabelList(String label){
        if(StringUtils.isEmpty(label)){
            return null;
        }
        List<String> resultList = new ArrayList<>();
        String[] labels = label.split(",");
        Map<Integer,String> userLabelMap =  getUserLabelMap();
        for (String labelId:labels){
            resultList.add(ResourcesLang.getLang(userLabelMap.get(LibConverterUtil.toInt(labelId))));
        }
        return resultList;
    }

    private Map<Integer,String> getUserLabelMap(){
        List<UserLabel> userLabelList = userLabelRepository.findByIsShowOrderBySortsDesc(BooleanTypeEnum.TRUE.getType());
        Map<Integer,String> userLabelMap = new HashMap<>();
        for (UserLabel info:userLabelList){
            userLabelMap.put(info.getLabelId(),info.getLabelName());
        }
        return userLabelMap;
    }

    @Override
    public void clearUserInfo(String account){
        int userId = 0;
        if(StringUtils.isNotEmpty(account)){
            userId = getUserIdByAccount(account);
        }
        userBiz.clearUserInfo(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void modify(Map<String, String> map){
        int userId = getLoginUserId();
        getUserInfoByUserId(userId);
        Map<String,String> userExtendMap = new HashMap<>();
        Map<String,String> userExtendKeyMap = getUserExtendKeyMap();

        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, String> item = it.next();
            if(StringUtils.isEmpty(item.getValue())){
                it.remove();
            }else{
                if(userExtendKeyMap.get(item.getKey()) != null){
                    userExtendMap.put(item.getKey(),item.getValue());
                    it.remove();
                }
            }
        }
        modifyUserInfo(userId, map);
        modifyUserExtendInfo(userId, userExtendMap);
    }

    /**
     * 修改用户扩展信息
     */
    private void modifyUserExtendInfo(int userId, Map<String, String> userExtendMap){
        UserExtend userExtend = new UserExtend();
        userExtend.setUserId(userId);
        for (Map.Entry<String, String> entry : userExtendMap.entrySet()) {
            String value = entry.getValue();
            if(StringUtils.equals(value, BaseConstants.DEFAULT_EMPTY)){
                value = "";
            }
            int re = userExtendRepository.updateUserExtendInfo(userId,entry.getKey(),value);
            if(re == 0){
                userExtend.setExtendKey(entry.getKey());
                userExtend.setExtendValue(value);
                userExtend = userExtendRepository.saveAndFlush(userExtend);
            }
            userBiz.cacheUserInfo(userId,entry.getKey(),value);
        }
    }
    /**
     * 修改用户信息
     */
    private void modifyUserInfo(int userId, Map<String, String> userInfoMap) {
        String sex = userInfoMap.get("sex");
        if(!LibSysUtil.isEmpty(sex)){
           userBiz.updateUserSex(userId,sex);
        }

        String signature = userInfoMap.get("signature");
        if(!LibSysUtil.isEmpty(signature)){
            userBiz.updateUserSignature(userId,signature);
        }

        String nickname = userInfoMap.get("nickname");
        if (!LibSysUtil.isEmpty(nickname)) {
            userBiz.updateUserNickname(userId,nickname);
        }

        String account = userInfoMap.get("account");
        if (!LibSysUtil.isEmpty(account)) {
            if(UserResourceUtil.accountRandom){
                userBiz.updateUserAccount(userId,account);
            }else {
                throw new RemindException("account not edit");
            }
        }

        String avatar = userInfoMap.get("avatar");
        if (!LibSysUtil.isEmpty(avatar)) {
            userBiz.updateUserAvatar(userId,avatar);
        }

        String city = userInfoMap.get("city");
        if (!LibSysUtil.isEmpty(city)) {
            userBiz.updateUserCity(userId,city);
        }

        String lng = userInfoMap.get("lng");
        String lat = userInfoMap.get("lat");
        if (!LibSysUtil.isEmpty(lng) && !LibSysUtil.isEmpty(lat)) {
            userBiz.updateUserLngAndLat(userId,lng,lat);
        }

        String birthday = userInfoMap.get("birthday");
        if (!LibSysUtil.isEmpty(birthday)) {
            userBiz.updateUserBirthday(userId,birthday);
        }

        String cid = userInfoMap.get("cid");
        if (!LibSysUtil.isEmpty(cid)) {
            userBiz.updateUserCid(userId,cid);
        }

        String langCode = userInfoMap.get("langCode");
        if (!LibSysUtil.isEmpty(langCode)) {
            userBiz.updateUserLangCode(userId,langCode);
        }
    }


    private UserInfo getUserInfoByUserId(int userId){
        UserInfo userInfo = userBiz.getUserInfo(userId);
        //当用户信息不存在时注册该用户
        if(userInfo == null){
            SecurityUser securityUser = getSecurityUser();
            return userBiz.register(userId,createAccount(userId),securityUser.getNickname()
                    ,securityUser.getAvatar(),createInviteCode(userId),getClientLangCode());
        }
        return userInfo;
    }

    public void verifyUserExist(int userId){
        userBiz.verifyUserExist(userId);
    }

    @Override
    public String getUserInfoByUserId(int userId, String field){
        return userBiz.getUserInfoByUserId(userId,field);
    }

    private UserInfo getUserInfoByAccount(String account){
        UserInfo userInfo = userBiz.getUserInfo(getUserIdByAccount(account));
        if(userInfo == null){
            throw new UserRemindException(UserResultEnum.USER_NOT_EXIST);
        }
        return userInfo;
    }

    private Map<String,String> getUserExtendKeyMap(){
        Map<String,String> userExtendMap = UserResourceUtil.UserExtendKey;
        if(userExtendMap.size() > 0){
            return userExtendMap;
        }
        List<UserExtendKey> list = userExtendKeyRepository.findAll();
        for (UserExtendKey info:list){
            userExtendMap.put(info.getExtendKey(),info.getExtendVerify());
        }
        UserResourceUtil.UserExtendKey = userExtendMap;
        return userExtendMap;
    }

    @Override
    public void pushMsg(String account, String msg){
        int toId = getUserIdByAccount(account);
        msgService.pushSystemRemindMsg(toId,"dynamic",10,msg);
    }
}
