package top.xujm.modules.security.service;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @author Xujm
 */
public interface AccountAuthorityService {

    List<GrantedAuthority> getAccountAuthorityList(int userId);

}
