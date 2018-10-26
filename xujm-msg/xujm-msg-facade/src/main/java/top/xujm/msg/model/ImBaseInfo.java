package top.xujm.msg.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.xujm.core.utils.LibDateUtil;

import java.util.UUID;

/**
 * @author Xujm
 */
@ApiModel("IM消息基本实体")
public class ImBaseInfo {
    @ApiModelProperty("IM消息码")
    private int imCode;
    @ApiModelProperty("发送者ACCOUNT")
    private String account;
    @ApiModelProperty("发送者昵称")
    private String nickname;
    @ApiModelProperty("发送者头像")
    private String avatar;
    @ApiModelProperty("发送时间")
    private long sendTime = LibDateUtil.getNowTime();
    @ApiModelProperty("发送消息")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String msg;
    @ApiModelProperty("消息ID")
    private String msgId = UUID.randomUUID().toString();
    @ApiModelProperty("接收方ACCOUNT")
    private String receiveAccount;
    @ApiModelProperty("角色名称")
    private String roleName;

    public int getImCode() {
        return imCode;
    }

    public void setImCode(int imCode) {
        this.imCode = imCode;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getSendTime() {
        return sendTime;
    }

//    public void setSendTime(long sendTime) {
//        this.sendTime = sendTime;
//    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public String getReceiveAccount() {
        return receiveAccount;
    }

    public void setReceiveAccount(String receiveAccount) {
        this.receiveAccount = receiveAccount;
    }

    public void copy(ImBaseInfo imBaseInfo){
        this.account = imBaseInfo.getAccount();
        this.avatar = imBaseInfo.getAvatar();
        this.imCode = imBaseInfo.getImCode();
        this.nickname = imBaseInfo.getNickname();
        this.msg = imBaseInfo.getMsg();
        this.sendTime = imBaseInfo.getSendTime();
        this.msgId = imBaseInfo.getMsgId();
        this.roleName = imBaseInfo.getRoleName();
        this.receiveAccount = imBaseInfo.getReceiveAccount();
    }
}
