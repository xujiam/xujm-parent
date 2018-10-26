package top.xujm.msg;

import java.util.List;
import java.util.Map;

/**
 * @author Xujm
 */
public interface PushMsgApi {

    /**
     * 发送单个用户消息
     * @param cid 用户标识
     * @param imMap 内容
     */
    void pushSingleUserMsg(String cid, Map<String, String> imMap);

    /**
     * 发送用户列表消息
     * @param dataPayload 内容MAP
     * @param list 用户标识列表
     */
    void pushListUserMsg(Map<String, String> dataPayload, List<String> list);

    /**
     * 发送所有用户消息
     * @param dataPayload 内容MAP
     */
    void pushAllUserMsg(Map<String, String> dataPayload);



}
