package top.xujm.modules.security.model;

import top.xujm.modules.security.common.SecurityConstants;

import javax.persistence.*;

/**
 * @author Xujm
 */
@Entity
@Table(name = SecurityConstants.MODULE_PREFIX +"_sms", schema = "weking")
public class UserSms {
    private int id;
    private String captcha;
    private String mobile;
    private byte type;
    private long sendTime;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "captcha", nullable = true, length = 8)
    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    @Basic
    @Column(name = "mobile", nullable = false, length = 30)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "type", nullable = false)
    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    @Basic
    @Column(name = "send_time", nullable = false)
    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

}
