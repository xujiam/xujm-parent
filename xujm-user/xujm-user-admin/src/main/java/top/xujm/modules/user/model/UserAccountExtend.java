package top.xujm.modules.user.model;

import top.xujm.modules.security.model.UserAccount;

public class UserAccountExtend extends UserAccount {

    private String roleKeys;

    private String roleNames;

    public String getRoleKeys() {
        return roleKeys;
    }

    public void setRoleKeys(String roleKeys) {
        this.roleKeys = roleKeys;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }
}
