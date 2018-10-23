package top.xujm.modules.user.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Xujm
 */
@Entity
@Table(name = "weking_user_feedback", schema = "weking")
public class UserFeedback extends UserBaseInfo{
    private int id;
    private String content;
    private String contactWay;
    private byte status;
    private long addTime;

    public UserFeedback(){

    }

    public UserFeedback(int id,int userId, String account, String nickname, String avatar,String content,String contactWay,byte status,long addTime){
        super(userId,account,nickname,avatar,(byte)0, (byte) 0, null, (short) 0);
        this.id = id;
        this.content = content;
        this.contactWay = contactWay;
        this.status = status;
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
    @Column(name = "content", nullable = false, length = 250)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "contact_way", nullable = true, length = 20)
    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "add_time", nullable = false, length = 20)
    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()){ return false;}
        UserFeedback that = (UserFeedback) o;
        return id == that.id &&
                userId == that.userId &&
                status == that.status &&
                Objects.equals(content, that.content) &&
                Objects.equals(contactWay, that.contactWay) &&
                Objects.equals(addTime, that.addTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, content, contactWay, status, addTime);
    }
}
