package top.xujm.generator.security.authentication.mobile;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Xujm
 * 2018/9/17
 */
public class MobilePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String WEKING_FORM_MOBILE_KEY = "username";
    private static final String WEKING_FORM_PASSWORD_KEY = "password";
    private String mobileParameter = WEKING_FORM_MOBILE_KEY;
    private String passwordParameter = WEKING_FORM_PASSWORD_KEY;
    private boolean postOnly = true;

    public MobilePasswordAuthenticationFilter() {
        super(new AntPathRequestMatcher("/user/login/mobile", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if(this.postOnly && !"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String username = this.obtainMobile(request);
            String password = this.obtainPassword(request);
            if(username == null) {
                username = "";
            }
            username = username.trim();
            MobileAuthenticationToken authRequest = new MobileAuthenticationToken(username,password);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }

    /**
     * 获取手机号的方法
     */
    private String obtainMobile(HttpServletRequest request) {
        return request.getParameter(this.mobileParameter);
    }

    /**
     * 获取密码的方法
     */
    private String obtainPassword(HttpServletRequest request) {
        return request.getParameter(this.passwordParameter);
    }

    private void setDetails(HttpServletRequest request, MobileAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public String getMobileParameter() {
        return mobileParameter;
    }

    public void setMobileParameter(String mobileParameter) {
        this.mobileParameter = mobileParameter;
    }

}
