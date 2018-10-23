package top.xujm.modules.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import top.xujm.modules.user.common.UserConstants;

import javax.persistence.*;

/**
 * @author Xujm
 */
@ApiModel("用户信息实体")
@Entity
@Table(name = UserConstants.MODULE_PREFIX + "_info", schema = "weking")
public class UserInfo extends UserBaseInfo{

    @ApiModelProperty("用户城市")
    private String city;
    @ApiModelProperty("用户出生年日")
    private int birthday;
    @ApiModelProperty("推送标识")
    private String cid;
    @ApiModelProperty("用户经验")
    private int experience;
    @JsonIgnore
    private double lng;
    @JsonIgnore
    private double lat;
    @JsonIgnore
    private short insideRole;
    @ApiModelProperty("邀请码")
    private String inviteCode;
    @JsonIgnore
    private int parentId;
    @ApiModelProperty("用户在线状态")
    private byte userState;
    @JsonIgnore
    private String langCode;
    @JsonIgnore
    private short sorts;
    @JsonIgnore
    private long addTime;
    @JsonIgnore
    private byte isRecommend;
    @ApiModelProperty("粉丝数")
    private int fansNum;
    @ApiModelProperty("关注数")
    private int followNum;

    public UserInfo(){}

    public UserInfo(String account, String nickname, String avatar, byte sex, byte level, String signature, short role) {
        super(0,account,nickname,avatar,sex,level,signature,role);
    }

    @Override
    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "city", nullable = true, length = 100)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "birthday", nullable = false)
    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "cid", nullable = true, length = 60)
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Basic
    @Column(name = "experience", nullable = false)
    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    @Basic
    @Column(name = "lng", nullable = false, precision = 12)
    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Basic
    @Column(name = "lat", nullable = false, precision = 12)
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Basic
    @Column(name = "inside_role", nullable = true)
    public short getInsideRole() {
        return insideRole;
    }

    public void setInsideRole(short insideRole) {
        this.insideRole = insideRole;
    }

    @Basic
    @Column(name = "invite_code", nullable = true, length = 8)
    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    @Basic
    @Column(name = "parent_id", nullable = false)
    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "user_state", nullable = false)
    public byte getUserState() {
        return userState;
    }

    public void setUserState(byte userState) {
        this.userState = userState;
    }

    @Basic
    @Column(name = "lang_code", nullable = false, length = 10)
    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    @Basic
    @Column(name = "sorts", nullable = false)
    public short getSorts() {
        return sorts;
    }

    public void setSorts(short sorts) {
        this.sorts = sorts;
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
    @Basic
    @Column(name = "account", nullable = true, length = 30)
    public String getAccount() {
        return account;
    }

    @Override
    @Basic
    @Column(name = "nickname", nullable = true, length = 100)
    public String getNickname() {
        return nickname;
    }

    @Override
    @Basic
    @Column(name = "sex", nullable = false)
    public byte getSex() {
        return sex;
    }


    @Override
    @Basic
    @Column(name = "avatar", nullable = true, length = 150)
    public String getAvatar() {
        return avatar;
    }

    @Override
    @Basic
    @Column(name = "signature", nullable = true, length = 255)
    public String getSignature() {
        return signature;
    }


    @Override
    @Basic
    @Column(name = "level", nullable = false)
    public byte getLevel() {
        return level;
    }

    @Override
    @Basic
    @Column(name = "role", nullable = true)
    public short getRole() {
        return role;
    }

    @Override
    public void setRole(short role) {
        this.role = role;
    }

    @Basic
    @Column(name = "is_recommend", nullable = false)
    public byte getRecommend() {
        return isRecommend;
    }

    public void setRecommend(byte recommend) {
        isRecommend = recommend;
    }

    @Basic
    @Column(name = "fans_num", nullable = false)
    public int getFansNum() {
        return fansNum;
    }

    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }
    @Basic
    @Column(name = "follow_num", nullable = false)
    public int getFollowNum() {
        return followNum;
    }

    public void setFollowNum(int followNum) {
        this.followNum = followNum;
    }

    public void copy(UserInfo userInfo){
        super.copy(userInfo);
        this.city = userInfo.getCity();
        this.birthday = userInfo.getBirthday();
        this.cid = userInfo.getCid();
        this.experience = userInfo.getExperience();
        this.lng = userInfo.getLng();
        this.lat = userInfo.getLat();
        this.insideRole = userInfo.getInsideRole();
        this.inviteCode = userInfo.getInviteCode();
        this.parentId = userInfo.getParentId();
        this.userState = userInfo.getUserState();
        this.langCode = userInfo.getLangCode();
        this.sorts = userInfo.getSorts();
        this.addTime = userInfo.getAddTime();
        this.isRecommend = userInfo.getRecommend();
        this.fansNum = userInfo.getFansNum();
        this.followNum = userInfo.getFollowNum();
    }
}
