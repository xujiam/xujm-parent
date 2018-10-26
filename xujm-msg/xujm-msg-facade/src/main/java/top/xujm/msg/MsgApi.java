package top.xujm.msg;

/**
 * @author Xujm
 */
public interface MsgApi {

    boolean registerAccount(String account);

    /**
     * 创建房间
     * @param roomId 房间号
     * @param account 房主账号
     * @return boolean
     */
    boolean createRoom(String roomId, String account);

    /**
     * 加入房间
     * @param roomId 房间号
     * @param account 加入者账号
     * @return boolean
     */
    boolean joinRoom(String roomId, String account);

    /**
     * 发送房间消息
     * @param roomId 房间ID
     * @param sendAccount 发送者
     * @param content 发送内容
     * @return boolean
     */
    boolean sendRoomMsg(String roomId, String sendAccount, String content);

}
