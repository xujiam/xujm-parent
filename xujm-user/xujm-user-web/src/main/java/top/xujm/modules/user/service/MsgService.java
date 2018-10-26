package top.xujm.modules.user.service;

import com.alibaba.fastjson.JSONObject;
import top.xujm.msg.model.ImBaseInfo;
import top.xujm.msg.model.ImBasePushInfo;

import java.util.List;

/**
 * @author Xujm
 */
public interface MsgService {

    ImBaseInfo getImBaseInfo(int userId, int toId, int imCode, String msg);

    /**
     * 创建IM房间
     * @param roomId 房间IM
     * @param account 主播account
     * @return boolean
     */
    boolean createRoom(int roomId, String account);

    boolean sendRoomMsg(int roomId, String sendAccount, String content);

    boolean joinRoom(int roomId, String account);

    boolean exitRoom(int roomId, String account);

    <T extends ImBaseInfo>T sendRoomMsg(int userId, int liveId, int imCode, String msg, T imContent);

    <T extends ImBaseInfo>T sendRoomMsg(int userId, int liveId, int imCode, T imContent);

    /**
     * ==================推送===================
     */
    void pushSingleUserMsg(int userId, int toId, int imCode, String msg, ImBasePushInfo imContent);

    /**
     * 推送系统消息
     * @param toId 接收用户ID
     * @param sourceCode 消息来源代号
     * @param objectId 消息来源对象ID
     * @param msg 消息
     */
    void pushSystemRemindMsg(int toId, String sourceCode, int objectId, String msg);

    /**
     * 推送信息给列表用户
     */
    void pushListUserMsg(int userId, List<String> cidList, int imCode, String msg, ImBaseInfo imContent);

    /**
     * 推送信息给所有用户
     */
    void pushAllUserMsg(int userId, int imCode, String msg, ImBaseInfo imContent);

    JSONObject msgSwitchList();

    void msgSwitch(int imCode, byte isClose);
}
