package top.xujm.modules.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import top.xujm.modules.user.common.UserConstants;

import javax.persistence.*;

@Entity
@Table(name = UserConstants.MODULE_PREFIX + "_account")
public class UserAccountEntity extends UserBaseInfo{

    private int userId;
    private String username;
    @JsonIgnore
    private String password;
    private String mobile;
    private Short insideRole;
    private Byte isBlack;
    private String areaCode;
    private String areaCode1;

    private Long addTime;

    private String avatarExtend;

    public UserAccountEntity(){

    }

    public UserAccountEntity(String mobile,Short insideRole,Byte isBlack,Long addTime,int userId, String account, String nickname, String avatar,Byte sex, Byte level, String signature, Short role){
        super(userId,account,nickname,avatar,sex,level, signature, role);
        this.userId = userId;
        this.mobile = mobile;
        this.insideRole = insideRole;
        this.isBlack = isBlack;
        this.addTime = addTime;
    }

    @Transient
    public String getAvatarExtend() {
        return avatarExtend;
    }

    public void setAvatarExtend(String avatarExtend) {
        this.avatarExtend = avatarExtend;
    }

    @Transient
    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    @Override
    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "inside_role")
    public Short getInsideRole() {
        return insideRole;
    }

    public void setInsideRole(Short insideRole) {
        this.insideRole = insideRole;
    }

    public String getAreaCode1() {
        return areaCode1;
    }

    public void setAreaCode1(String areaCode1) {
        this.areaCode1 = areaCode1;
    }

    @Basic
    @Column(name = "is_black")
    public Byte getIsBlack() {
        return isBlack;
    }

    public void setIsBlack(Byte isBlack) {
        this.isBlack = isBlack;
    }

    @Basic
    @Column(name = "area_code")
    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

}
