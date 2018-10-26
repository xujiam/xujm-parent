package top.xujm.modules.user.model;

import top.xujm.core.base.BaseConstants;
import top.xujm.modules.user.common.UserConstants;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Xujm
 */
@Entity
@Table(name = UserConstants.MODULE_PREFIX + "_role", schema = BaseConstants.DATABASE_SCHEMA)
public class UserRole {
    private int roleId;
    private String roleName;
    private String roleCode;

    @Id
    @Column(name = "role_id", nullable = false)
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "role_name", nullable = false, length = 20)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Basic
    @Column(name = "role_code", nullable = false, length = 10)
    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        UserRole userRole = (UserRole) o;
        return roleId == userRole.roleId &&
                Objects.equals(roleName, userRole.roleName) &&
                Objects.equals(roleCode, userRole.roleCode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(roleId, roleName, roleCode);
    }
}
