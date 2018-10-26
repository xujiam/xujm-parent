package top.xujm.modules.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Xujm
 */
@ApiModel("消息开关实体")
@Entity
@Table(name = "weking_user_msg_switch", schema = "weking")
public class MsgSwitch {
    @JsonIgnore
    private int id;
    @JsonIgnore
    private int userId;
    @ApiModelProperty("消息IM_CODE")
    private int msgCode;
    @ApiModelProperty("消息是否关闭")
    private byte isClose;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "msg_code", nullable = false)
    public int getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(int msgCode) {
        this.msgCode = msgCode;
    }

    @Basic
    @Column(name = "is_close", nullable = false)
    public byte getIsClose() {
        return isClose;
    }

    public void setIsClose(byte isClose) {
        this.isClose = isClose;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        MsgSwitch msgSwitch = (MsgSwitch) o;
        return id == msgSwitch.id &&
                userId == msgSwitch.userId &&
                isClose == msgSwitch.isClose &&
                Objects.equals(msgCode, msgSwitch.msgCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, msgCode, isClose);
    }
}
