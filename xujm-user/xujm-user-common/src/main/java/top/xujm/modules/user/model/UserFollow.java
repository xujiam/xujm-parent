package top.xujm.modules.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.xujm.core.base.BaseConstants;
import top.xujm.modules.user.common.UserConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Xujm
 */
@ApiModel(value = "用户关注")
@Entity
@Table(name = UserConstants.MODULE_PREFIX+"_follow", schema = BaseConstants.DATABASE_SCHEMA)
public class UserFollow extends UserBaseInfo implements Serializable {
    @JsonIgnore
    private int id;

    @JsonIgnore
    private int userId;

    @JsonIgnore
    private int toId;
    @ApiModelProperty(value = "关注状态(0未关注1关注2互关注)")
    private byte followState;
    @ApiModelProperty(value = "关注时间")
    private long addTime;

    public UserFollow(){

    }

    public UserFollow(int id,int userId,int toId,String account,String nickname,String avatar,long addTime,short role,byte followState){
        super(userId,account,nickname,avatar,(byte)0, (byte)0,null, role);
        this.id = id;
        this.toId = toId;
        this.addTime = addTime;
        this.followState = followState;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    @Basic
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "to_id", nullable = false)
    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    @Basic
    @Column(name = "follow_state", nullable = false)
    public byte getFollowState() {
        return followState;
    }

    public void setFollowState(byte followState) {
        this.followState = followState;
    }

    @Basic
    @Column(name = "add_time", nullable = false)
    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        UserFollow that = (UserFollow) o;
        return id == that.id &&
                userId == that.userId &&
                toId == that.toId &&
                followState == that.followState &&
                addTime == that.addTime;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, toId, followState, addTime);
    }
}
