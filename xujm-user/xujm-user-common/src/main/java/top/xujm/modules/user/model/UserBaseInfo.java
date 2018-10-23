package top.xujm.modules.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import top.xujm.modules.user.common.UserResourceUtil;

/**
 * @author Xujm
 */
@ApiModel(value = "用户基础实体")
public class UserBaseInfo {

    @JsonIgnore
    @ApiParam(hidden = true)
    public int userId;

    @ApiModelProperty(value = "用户account")
    @ApiParam(hidden = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String account;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户昵称")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String nickname;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户性别(1男2女)")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public byte sex;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户头像")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String avatar;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户等级")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public byte level;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户签名")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String signature;

    @ApiParam(hidden = true)
    @ApiModelProperty(value = "用户角色(0普通用户)")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public short role;

    @ApiModelProperty("用户角色名称")
    @ApiParam(hidden = true)
    public String roleName;

    public UserBaseInfo(){

    }

    public UserBaseInfo(int userId, String account, String nickname, String avatar, byte sex, byte level, String signature, short role){
        this.userId = userId;
        this.account = account;
        this.nickname = nickname;
        this.avatar = avatar;
        this.sex = sex;
        this.level = level;
        this.signature = signature;
        this.role = role;
        this.roleName = UserResourceUtil.getUserRoleName(role);
    }

    public void copy(UserBaseInfo userBaseInfo){
        this.userId = userBaseInfo.getUserId();
        this.account = userBaseInfo.getAccount();
        this.nickname = userBaseInfo.getNickname();
        this.avatar = userBaseInfo.getAvatar();
        this.sex = userBaseInfo.getSex();
        this.level = userBaseInfo.getLevel();
        this.signature = userBaseInfo.getSignature();
        this.role = userBaseInfo.getRole();
        this.roleName = UserResourceUtil.getUserRoleName(role);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public short getRole() {
        return role;
    }

    public void setRole(short role) {
        this.role = role;
        this.roleName = UserResourceUtil.getUserRoleName(role);
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
