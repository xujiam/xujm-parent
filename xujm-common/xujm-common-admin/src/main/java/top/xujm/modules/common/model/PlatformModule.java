package top.xujm.modules.common.model;

import top.xujm.core.base.BaseConstants;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author ZhengYP
 */
@Entity
@Table(name = "xujm_platform_module", schema = BaseConstants.DATABASE_SCHEMA)
public class PlatformModule {
    private int id;
    private String moduleName;
    private String moduleValue;
    private String pluginCode;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "module_name")
    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    @Basic
    @Column(name = "module_value")
    public String getModuleValue() {
        return moduleValue;
    }

    public void setModuleValue(String moduleValue) {
        this.moduleValue = moduleValue;
    }

    @Basic
    @Column(name = "plugin_code")
    public String getPluginCode() {
        return pluginCode;
    }

    public void setPluginCode(String pluginCode) {
        this.pluginCode = pluginCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlatformModule that = (PlatformModule) o;
        return id == that.id &&
                Objects.equals(moduleName, that.moduleName) &&
                Objects.equals(moduleValue, that.moduleValue) &&
                Objects.equals(pluginCode, that.pluginCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, moduleName, moduleValue, pluginCode);
    }
}
