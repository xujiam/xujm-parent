package top.xujm.modules.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import top.xujm.config.resource.ResourceConfig;
import top.xujm.core.base.BaseResultEnum;
import top.xujm.core.exception.RemindException;
import top.xujm.core.utils.LibBeanUtil;
import top.xujm.core.utils.LibConverterUtil;
import top.xujm.modules.user.biz.UserBiz;
import top.xujm.modules.user.common.UserConstants;
import top.xujm.modules.user.common.UserIdTypeEnum;
import top.xujm.modules.user.common.UserImCodeEnum;
import top.xujm.modules.user.common.UserResourceUtil;
import top.xujm.modules.user.model.MsgSwitch;
import top.xujm.modules.user.model.UserBaseInfo;
import top.xujm.modules.user.repository.MsgSwitchRepository;
import top.xujm.modules.user.service.MsgService;
import top.xujm.msg.MsgApi;
import top.xujm.msg.PushMsgApi;
import top.xujm.msg.model.ImBaseInfo;
import top.xujm.msg.model.ImBasePushInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Xujm
 */
@Component
public class MsgServiceImpl extends UserBaseService implements MsgService {

    @Autowired
    private UserBiz userBiz;

    @Autowired(required = false)
    private Map<String, MsgApi> msgApiMap;
    @Autowired(required = false)
    private Map<String, PushMsgApi> pushMsgApiMap;
    @Autowired
    private MsgSwitchRepository msgSwitchRepository;

    @Override
    public ImBaseInfo getImBaseInfo(int userId, int toId, int imCode, String msg) {
        UserBaseInfo userBaseInfo;
        if(userId == UserIdTypeEnum.SYSTEM.getUserId()){
            //系统
            userBaseInfo = LibBeanUtil.entityCopy(JSONObject.parseObject(ResourceConfig.getConfig("system.push.user.info")),UserBaseInfo.class);
        }else if(userId == UserIdTypeEnum.VISITOR.getUserId()){
            //游客
            userBaseInfo = LibBeanUtil.entityCopy(JSONObject.parseObject(ResourceConfig.getConfig("visitor.push.user.info")),UserBaseInfo.class);
        }else {
            userBaseInfo = userBiz.getUserBaseInfoByUserId(userId);
            if(userBaseInfo == null){
                return null;
            }
        }
        ImBaseInfo imBaseInfo = new ImBaseInfo();
        if(toId != 0){
            imBaseInfo.setReceiveAccount(userBiz.getUserInfoByUserId(toId,"account"));
        }
        imBaseInfo.setAccount(userBaseInfo.getAccount());
        imBaseInfo.setAvatar(userBaseInfo.getAvatar());
        imBaseInfo.setImCode(imCode);
        imBaseInfo.setNickname(userBaseInfo.getNickname());
        imBaseInfo.setRoleName(UserResourceUtil.getUserRoleName(userBaseInfo.getRole()));
        imBaseInfo.setMsg(StringUtils.replace(msg,"%nickname%",userBaseInfo.getNickname()));
        return imBaseInfo;
    }

    @Async
    @Override
    public void pushSystemRemindMsg(int toId, String sourceCode, int objectId, String msg){
        ImBasePushInfo imSystem = new ImBasePushInfo();
        imSystem.setSourceCode(sourceCode);
        imSystem.setObjectId(objectId);
        pushSingleUserMsg(0,toId, UserImCodeEnum.SYSTEM.getCode(),msg,imSystem);
    }

    @Override
    public boolean createRoom(int roomId, String account) {
        MsgApi msgApi = getMsgApi();
        if(msgApi == null){
            return false;
        }
        return msgApi.createRoom(LibConverterUtil.toString(roomId),account);
    }

    @Override
    public boolean joinRoom(int roomId, String account) {
        return true;
    }

    @Override
    public boolean exitRoom(int roomId, String account){
        return true;
    }

    @Override
    public boolean sendRoomMsg(int roomId, String sendAccount, String content) {
        MsgApi msgApi = getMsgApi();
        if(msgApi == null){
            return false;
        }
        return msgApi.sendRoomMsg(LibConverterUtil.toString(roomId),sendAccount,content);
    }

    @Override
    public <T extends ImBaseInfo>T sendRoomMsg(int userId, int liveId, int imCode, T imContent) {
        return sendRoomMsg(userId,liveId,imCode ,null,imContent);
    }

    @Override
    public <T extends ImBaseInfo>T sendRoomMsg(int userId, int liveId, int imCode , String msg, T imContent) {
        imContent.copy(getImBaseInfo(userId,0,imCode,msg));
        if(UserConstants.SERVER_SEND_MSG){
            sendRoomMsg(liveId,imContent.getAccount(), JSON.toJSONString(imContent));
            return null;
        }
        return imContent;
    }
    @Async
    @Override
    public void pushSingleUserMsg(int userId, int toId, int imCode , String msg, ImBasePushInfo imContent) {
        PushMsgApi pushMsgApi = getPushMsgApi();
        if(pushMsgApi == null){
            return;
        }
        String cid = userBiz.getUserInfoByUserId(toId,"cid");
        imContent.copy(getImBaseInfo(userId,toId,imCode,msg));
        if(getMsgCloseList(toId).contains(imCode)){
            //若用户关闭消息提醒
            imContent.setShield(true);
        }
        pushMsgApi.pushSingleUserMsg(cid, LibBeanUtil.objectToMap(imContent));
    }
    @Async
    @Override
    public void pushListUserMsg(int userId, List<String> cidList, int imCode , String msg, ImBaseInfo imContent) {
        PushMsgApi pushMsgApi = getPushMsgApi();
        if(pushMsgApi == null){
            return;
        }
        imContent.copy(getImBaseInfo(userId,0,imCode,msg));
        pushMsgApi.pushListUserMsg(LibBeanUtil.objectToMap(imContent),cidList);
    }
    @Async
    @Override
    public void pushAllUserMsg(int userId, int imCode , String msg, ImBaseInfo imContent) {
        PushMsgApi pushMsgApi = getPushMsgApi();
        if(pushMsgApi == null){
            return;
        }
        imContent.copy(getImBaseInfo(userId,0,imCode,msg));
        pushMsgApi.pushAllUserMsg(LibBeanUtil.objectToMap(imContent));
    }


    private MsgApi getMsgApi(){
        if(msgApiMap == null){
            return null;
        }
        String provider = ResourceConfig.getConfig("msg.im.provider");
        MsgApi msgApi = msgApiMap.get(String.format("%sMsgApiImpl",provider));
        if(msgApi == null){
            throw new RemindException(BaseResultEnum.ERROR.getCode(),"消息发送提供商不存在");
        }
        return msgApi;
    }

    private PushMsgApi getPushMsgApi(){
        if(pushMsgApiMap == null){
            return null;
        }
        String provider = ResourceConfig.getConfig("msg.push.provider");
        PushMsgApi msgApi = pushMsgApiMap.get(String.format("%sPushMsgApiImpl",provider));
        if(msgApi == null){
            throw new RemindException(BaseResultEnum.ERROR.getCode(),"消息推送提供商不存在");
        }
        return msgApi;
    }

    @Override
    public JSONObject msgSwitchList(){
        int userId = getLoginUserId();
        String[] switchCodeArr = getMsgSwitchCodeArr();
        List<Integer> list = getMsgCloseList(userId);
        JSONObject jsonObject =  new JSONObject();
        for (String imCode:switchCodeArr){
            if(list.contains(LibConverterUtil.toInt(imCode))){
                jsonObject.put(imCode,1);
            }else {
                jsonObject.put(imCode,0);
            }
        }
        return jsonObject;
    }

    @Override
    public void msgSwitch(int imCode, byte isClose){
        int userId = getLoginUserId();
        String[] switchCodeArr = getMsgSwitchCodeArr();
        if(Arrays.asList(switchCodeArr).contains(LibConverterUtil.toString(imCode))){
            int re = msgSwitchRepository.updateIsCloseByUserIdAndMsgCode(userId,imCode,isClose);
            if(re == 0){
                MsgSwitch msgSwitch = new MsgSwitch();
                msgSwitch.setMsgCode(imCode);
                msgSwitch.setIsClose(isClose);
                msgSwitch.setUserId(userId);
                msgSwitchRepository.save(msgSwitch);
            }
        }
    }

    private List<Integer> getMsgCloseList(int userId){
        return msgSwitchRepository.selectMsgCodeByUserId(userId);
    }

    private String[] getMsgSwitchCodeArr(){
        String switchCode = ResourceConfig.getConfig("msg.push.switch.code");
        return StringUtils.split(switchCode,",");
    }
}
