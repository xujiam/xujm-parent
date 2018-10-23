package top.xujm.modules.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Xujm
 */
@ApiModel("拉黑实体")
@Entity
@Table(name = "weking_user_black", schema = "weking")
public class UserBlack extends UserBaseInfo{
    @JsonIgnore
    private int id;
    @JsonIgnore
    private int toId;
    @ApiModelProperty("拉黑时间")
    private long addTime;

    public UserBlack(){

    }

    public UserBlack(int id,String account,String nickname,String avatar,long addTime){
        this.id = id;
        this.account = account;
        this.nickname = nickname;
        this.avatar = avatar;
        this.addTime = addTime;
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
        UserBlack userBlack = (UserBlack) o;
        return id == userBlack.id &&
                userId == userBlack.userId &&
                toId == userBlack.toId &&
                addTime == userBlack.addTime;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, toId, addTime);
    }
}
