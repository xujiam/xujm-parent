package top.xujm.modules.security.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;
import top.xujm.core.utils.LibConverterUtil;
import top.xujm.core.utils.LibSysUtil;
import top.xujm.modules.security.model.SecurityUser;
import top.xujm.modules.security.model.UserAccount;
import top.xujm.modules.security.model.Userconnection;
import top.xujm.modules.security.repository.UserAccountRepository;
import top.xujm.modules.security.repository.UserconnectionRepository;
import top.xujm.modules.security.service.AccountAuthorityService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author Xujm
 */
@Component
public class SecurityUserServiceImpl implements UserDetailsService, SocialUserDetailsService {

    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private UserconnectionRepository userconnectionRepository;
    @Autowired(required = false)
    private AccountAuthorityService accountAuthorityService;
    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserAccount user;
        if(LibSysUtil.isInteger(username)){
            user = userAccountRepository.findFirstByMobile(username);
        }else {
            user = userAccountRepository.findFirstByUsername(username);
        }
        if(user == null){
            throw new UsernameNotFoundException("user not found");
        }
        String loginCode = getLoginCode();
        return getSecurityUser(LibConverterUtil.toString(user.getUserId()), user.getPassword(), LibConverterUtil.toString(user.getUserId()),loginCode,
                null,null,user.getIsBlack()==0,user.getInsideRole());
    }

    private String getLoginCode(){
        String requestUri = request.getRequestURI();
        if(requestUri == null){
            return "";
        }
        String[] uriArr = StringUtils.split(requestUri,"/");
        return uriArr[uriArr.length-1];
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        Userconnection userconnection = userconnectionRepository.findFirstByUserId(userId);
        if(userconnection == null){
            throw new UsernameNotFoundException("user not found");
        }
        UserAccount user = userAccountRepository.findFirstByUserId(LibConverterUtil.toInt(userId));
        if(user == null){
            throw new UsernameNotFoundException("user not found");
        }
        return getSecurityUser(userId, "", userId,userconnection.getProviderId(),
                userconnection.getDisplayName(),userconnection.getImageUrl(),true,user.getInsideRole());
    }

    private SecurityUser getSecurityUser(String username, String password, String userId, String loginWay,
                                         String nickname, String avatar, boolean enable, short insideRole){
        List<GrantedAuthority> authList = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
        if(accountAuthorityService != null && insideRole == 2){
            List<GrantedAuthority> list = accountAuthorityService.getAccountAuthorityList(LibConverterUtil.toInt(userId));
            if(list != null){
                authList.addAll(list);
            }
        }
        return new SecurityUser(username, password, userId,loginWay,nickname,avatar,enable,authList);
    }
}
