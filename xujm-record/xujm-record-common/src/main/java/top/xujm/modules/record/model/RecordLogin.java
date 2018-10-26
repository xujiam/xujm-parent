package top.xujm.modules.record.model;

import top.xujm.core.base.BaseConstants;
import top.xujm.modules.record.common.RecordConstants;
import top.xujm.modules.user.model.UserBaseInfo;

import javax.persistence.*;

/**
 * @author Xujm
 */
@Entity
@Table(name = RecordConstants.MODULE_PREFIX + "_login", schema = BaseConstants.DATABASE_SCHEMA)
public class RecordLogin extends UserBaseInfo {
    private int id;
    private String clientIp;
    private long loginTime;
    private String appCode;
    private String loginCode;
    private String deviceModel;
    private String imei;
    private String appVersion;

    public RecordLogin(){}

    public RecordLogin(int userId, String account, String nickname, String avatar, short role,int id,String clientIp,long loginTime,String appCode,String loginCode,String deviceModel,String imei,String appVersion){
        super(userId, account, nickname, avatar, (byte)0, (byte)0, (String)null, role);
        this.id = id;
        this.clientIp = clientIp;
        this.loginTime = loginTime;
        this.appCode = appCode;
        this.loginCode = loginCode;
        this.deviceModel = deviceModel;
        this.imei = imei;
        this.appVersion = appVersion;
        this.clientIp = clientIp;
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
    @Column(name = "client_ip", nullable = true, length = 16)
    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    @Basic
    @Column(name = "login_time", nullable = false)
    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(long loginTime) {
        this.loginTime = loginTime;
    }

    @Basic
    @Column(name = "app_code", nullable = true, length = 50)
    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    @Basic
    @Column(name = "login_code", nullable = true, length = 50)
    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    @Basic
    @Column(name = "device_model", nullable = true, length = 100)
    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    @Basic
    @Column(name = "imei", nullable = true, length = 50)
    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    @Basic
    @Column(name = "app_version", nullable = true, length = 10)
    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

}
