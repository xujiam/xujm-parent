package top.xujm.modules.security.model;

import top.xujm.core.base.BaseConstants;
import top.xujm.modules.security.common.SecurityConstants;

import javax.persistence.*;

/**
 * @author Xujm
 */
@Entity
@Table(name = SecurityConstants.MODULE_PREFIX + "_account", schema = BaseConstants.DATABASE_SCHEMA)
public class UserAccount {
    private int userId;
    private String username;
    private String password;
    private String mobile;
    private short insideRole;
    private byte isBlack;
    private String areaCode;
    private String registerCode;
    private long addTime;

    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "username", nullable = true, length = 20)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 64)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "mobile", nullable = true, length = 20)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "inside_role", nullable = false)
    public short getInsideRole() {
        return insideRole;
    }

    public void setInsideRole(short insideRole) {
        this.insideRole = insideRole;
    }

    @Basic
    @Column(name = "is_black", nullable = false)
    public byte getIsBlack() {
        return isBlack;
    }

    public void setIsBlack(byte isBlack) {
        this.isBlack = isBlack;
    }

    @Basic
    @Column(name = "area_code", nullable = false, length = 3)
    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @Basic
    @Column(name = "register_code", nullable = false, length = 15)
    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }
    @Basic
    @Column(name = "add_time", nullable = false, length = 15)
    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }
}
