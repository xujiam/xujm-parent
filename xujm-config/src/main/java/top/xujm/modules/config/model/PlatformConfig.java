package top.xujm.modules.config.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Xujm
 */
@Entity
@Table(name = "weking_platform_config", schema = "weking")
public class PlatformConfig {
    private int id;
    private String configKey;
    private String configValue;
    private byte configType;
    private byte configLocal;
    private String configMark;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "config_key", nullable = false, length = 50)
    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    @Basic
    @Column(name = "config_value", nullable = true, length = 300)
    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    @Basic
    @Column(name = "config_type", nullable = false)
    public byte getConfigType() {
        return configType;
    }

    public void setConfigType(byte configType) {
        this.configType = configType;
    }

    @Basic
    @Column(name = "config_local", nullable = false)
    public byte getConfigLocal() {
        return configLocal;
    }

    public void setConfigLocal(byte configLocal) {
        this.configLocal = configLocal;
    }

    @Basic
    @Column(name = "config_mark", nullable = false, length = 300)
    public String getConfigMark() {
        return configMark;
    }

    public void setConfigMark(String configMark) {
        this.configMark = configMark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){ return true;}
        if (o == null || getClass() != o.getClass()){ return false;}
        PlatformConfig that = (PlatformConfig) o;
        return id == that.id &&
                configType == that.configType &&
                Objects.equals(configKey, that.configKey) &&
                Objects.equals(configValue, that.configValue) &&
                Objects.equals(configMark, that.configMark);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, configKey, configValue, configType, configMark);
    }
}
