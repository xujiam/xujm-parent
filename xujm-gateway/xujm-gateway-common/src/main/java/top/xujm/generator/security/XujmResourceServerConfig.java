package top.xujm.generator.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.social.security.SpringSocialConfigurer;
import top.xujm.common.core.encrypt.EncryptFilter;
import top.xujm.generator.security.authentication.mobile.MobileAuthenticationSecurityConfig;

/**
 * @author Xujm
 */
@Configuration
@EnableResourceServer
public class XujmResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final TokenStore tokenStore;

    private final DefaultTokenServices defaultTokenServices;

    private final SecurityProperties securityProperties;

    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    private final AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private MobileAuthenticationSecurityConfig mobileAuthenticationSecurityConfig;

    @Autowired(required = false)
    private SpringSocialConfigurer wekingSpringSocialConfigurer;

    private final XujmAuthenticationEntryPoint xujmAuthenticationEntryPoint;

    @Autowired
    public XujmResourceServerConfig(TokenStore tokenStore, DefaultTokenServices defaultTokenServices, AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler, XujmAuthenticationEntryPoint xujmAuthenticationEntryPoint, SecurityProperties securityProperties) {
        this.tokenStore = tokenStore;
        this.defaultTokenServices = defaultTokenServices;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.xujmAuthenticationEntryPoint = xujmAuthenticationEntryPoint;
        this.securityProperties = securityProperties;
    }


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore).tokenServices(defaultTokenServices);
        resources.authenticationEntryPoint(xujmAuthenticationEntryPoint);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        if(securityProperties.isAnonymous()){
            http.authorizeRequests().and().anonymous();
        }else{
            String[] urls = StringUtils.split(securityProperties.getIgnoreUrl(),",");
            if(securityProperties.getAuthRole() == null){
                http.authorizeRequests()
                    //配置不需要认证页面
                    .antMatchers(urls).permitAll()
                    .anyRequest().authenticated();
            }else{
                http.authorizeRequests()
                    //配置不需要认证页面
                    .antMatchers(urls).permitAll()
                    .anyRequest().hasRole(securityProperties.getAuthRole());
            }
        }
        //如果不支持第三登录
        if(wekingSpringSocialConfigurer != null){
            http.apply(wekingSpringSocialConfigurer);
        }
        http.exceptionHandling().authenticationEntryPoint(xujmAuthenticationEntryPoint).and()
                .formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .loginPage(securityProperties.getLoginPage()).loginProcessingUrl(securityProperties.getProcessingUrl())
                .permitAll()
                .and().addFilterBefore(new EncryptFilter(),SecurityContextPersistenceFilter.class)
                .logout()
                .permitAll()
                //.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and().csrf().disable()
                .apply(mobileAuthenticationSecurityConfig);
    }

}

