package top.xujm.modules.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import top.xujm.modules.auth.model.AdminUserRole;
import top.xujm.modules.auth.repository.AdminUserRoleRepository;
import top.xujm.modules.security.service.AccountAuthorityService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xujm
 */
@Component
public class AccountAuthorityServiceImpl implements AccountAuthorityService {

    @Autowired
    private AdminUserRoleRepository adminUserRoleRepository;

    @Override
    public List<GrantedAuthority> getAccountAuthorityList(int userId) {
        List<AdminUserRole> userRole = adminUserRoleRepository.selectUserRoleByUserId(userId);
        if(userRole == null){
            return null;
        }
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_INSIDE"));
        for (AdminUserRole role:userRole){
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRoleCode());
            list.add(grantedAuthority);
        }
        return list;
    }
}
