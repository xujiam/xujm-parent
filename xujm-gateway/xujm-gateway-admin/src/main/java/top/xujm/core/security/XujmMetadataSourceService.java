package top.xujm.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import top.xujm.modules.auth.model.AdminMenu;
import top.xujm.modules.auth.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Xujm
 */
@Component
public class XujmMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private AuthService authService;

    private HashMap<String, Collection<ConfigAttribute>> map = null;

    /**
     * 加载权限表中所有权限
     */
    private void loadResourceDefine(){
        map = new HashMap<>(0);
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        Map<Integer, AdminMenu> adminMenusMap = authService.getAllAdminMenuMap();
        for (Map.Entry<Integer,AdminMenu> entry:adminMenusMap.entrySet()){
            array = new ArrayList<>();
            for (String roleCode:entry.getValue().getRoleCodeList()){
                if(roleCode == null){
                    roleCode = "ROLE_ADMIN";
                }
                cfg = new SecurityConfig(roleCode);
                array.add(cfg);
            }
            map.put(entry.getValue().getMenuUrl(), array);
        }
    }

    /**
     * 此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，
     * 则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if(map ==null || map.size() == 0) {
            loadResourceDefine();
        }
        //object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        for (String s : map.keySet()) {
            resUrl = s;
            matcher = new AntPathRequestMatcher(resUrl);
            if (matcher.matches(request)) {
                return map.get(resUrl);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
