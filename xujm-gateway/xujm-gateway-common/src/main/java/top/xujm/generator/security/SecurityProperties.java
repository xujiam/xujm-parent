package top.xujm.generator.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Xujm
 */
@Component
@ConfigurationProperties(prefix = "xujm.security")
public class SecurityProperties {

    private String ignoreUrl = "/user/login/*";

    private String loginPage = "/user/signIn";

    private String processingUrl = "/user/login/username";

    private boolean anonymous = true;

    private String authRole = null;

    public String getIgnoreUrl() {
        return ignoreUrl;
    }

    public void setIgnoreUrl(String ignoreUrl) {
        this.ignoreUrl = ignoreUrl;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public String getProcessingUrl() {
        return processingUrl;
    }

    public void setProcessingUrl(String processingUrl) {
        this.processingUrl = processingUrl;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public String getAuthRole() {
        return authRole;
    }

    public void setAuthRole(String authRole) {
        this.authRole = authRole;
    }
}
