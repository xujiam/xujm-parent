package top.xujm.core.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import top.xujm.core.base.BaseResultEnum;
import top.xujm.modules.security.common.SecurityRemindException;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author Xujm
 */
@Component
public class XujmAccessDecisionManager implements AccessDecisionManager {

    /**
     * 判定是否拥有权限的决策方法
     * @param authentication 是释CustomUserService中循环添加到 GrantedAuthority 对象中的权限信息集合.
     * @param object 包含客户端发起的请求的requset信息，可转换为
     *               HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
     * @param collection 为MyInvocationSecurityMetadataSource的getAttributes(Object object)
     *                   这个方法返回的结果，此方法是为了判定用户请求的url 是否在权限表中，
     *                   如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。
     *                   如果不在权限表中则放行。
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        if(null== collection || collection.size() <=0) {
            return;
        }
        ConfigAttribute c;
        String needRole;
        for (ConfigAttribute aCollection : collection) {
            c = aCollection;
            needRole = c.getAttribute();
            //authentication 为在注释1 中循环添加到 GrantedAuthority 对象中的权限信息集合
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if(needRole.trim().equals(ga.getAuthority())) {
                    return;
                }
            }
        }
        throw new SecurityRemindException(BaseResultEnum.ACCESS_DENIED.getCode(),BaseResultEnum.ACCESS_DENIED.getMessage());
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}
