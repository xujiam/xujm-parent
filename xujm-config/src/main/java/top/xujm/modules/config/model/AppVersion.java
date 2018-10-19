package top.xujm.modules.config.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * @author Xujm
 */
@Entity
@Table(name = "weking_platform_app_version", schema = "weking")
public class AppVersion {
    @JsonIgnore
    private int id;

    private String appCode;

    private String appVersion;
    @JsonIgnore
    private String versionName;

    private String appUrl;

    private String updateMsg;

    private String appSize;

    private byte forceType;
    @JsonIgnore
    private long addTime;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "app_code", nullable = false, length = 10)
    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    @Basic
    @Column(name = "app_version", nullable = false, length = 10)
    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    @Basic
    @Column(name = "version_name", nullable = false, length = 10)
    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    @Basic
    @Column(name = "app_url", nullable = false, length = 100)
    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    @Basic
    @Column(name = "update_msg", length = 300)
    public String getUpdateMsg() {
        return updateMsg;
    }

    public void setUpdateMsg(String updateMsg) {
        this.updateMsg = updateMsg;
    }

    @Basic
    @Column(name = "app_size", length = 10)
    public String getAppSize() {
        return appSize;
    }

    public void setAppSize(String appSize) {
        this.appSize = appSize;
    }

    @Basic
    @Column(name = "force_type", nullable = false)
    public byte getForceType() {
        return forceType;
    }

    public void setForceType(byte forceType) {
        this.forceType = forceType;
    }

    @Basic
    @Column(name = "add_time", nullable = false)
    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

}
