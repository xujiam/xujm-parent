package top.xujm.generator.security.authentication.mobile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.xujm.core.language.Language;
import top.xujm.core.utils.SpringContextUtil;

/**
 * @author Xujm
 * 2018/9/17
 */
public class MobileAuthenticationProvider implements AuthenticationProvider {

    private Logger logger = LoggerFactory.getLogger(MobileAuthenticationProvider.class);

    private UserDetailsService userDetailsService;

    private PasswordEncoder passwordEncoder;

    public MobileAuthenticationProvider() {
        passwordEncoder = (PasswordEncoder) SpringContextUtil.getBean("passwordEncoder");
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileAuthenticationToken authenticationToken = (MobileAuthenticationToken) authentication;
        UserDetails user = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
        if(user == null){
            throw new InternalAuthenticationServiceException("user not exist");
        }
        String presentedPassword = authentication.getCredentials().toString();
        if (presentedPassword == null) {
            logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(Language.getMsg("AbstractUserDetailsAuthenticationProvider.badCredentials"));
        } else {
            if (!passwordEncoder.matches(presentedPassword, user.getPassword())) {
                logger.debug("Authentication failed: password does not match stored value");
                throw new BadCredentialsException(Language.getMsg("AbstractUserDetailsAuthenticationProvider.badCredentials"));
            }
        }
        MobileAuthenticationToken authenticationResult = new MobileAuthenticationToken(user,presentedPassword,user.getAuthorities());
        //把未认证的信息放到已认证的detail里面
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

}
