package top.xujm.generator.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.*;
import sun.misc.BASE64Decoder;
import top.xujm.common.core.encrypt.EncryptFilter;

import javax.annotation.PostConstruct;

/**
 * @author Xujm
 */
@Configuration
@EnableAuthorizationServer
public class XujmAuthenticationServerConfig  extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenStore tokenStore;

    @Qualifier("securityUserServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private WebResponseExceptionTranslator customWebResponseExceptionTranslator;

    /**
     * 配置TokenEndpoint 是  /oauth/token处理的入口点
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        final TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        //tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer, jwtAccessTokenConverter));
        endpoints.tokenStore(tokenStore)
                .tokenEnhancer(tokenEnhancerChain)
                .authenticationManager(authenticationManager).userDetailsService(userDetailsService);
        endpoints.exceptionTranslator(customWebResponseExceptionTranslator);

    }

    /**
     * 功能：认证服务器会给哪些第三方应用发令牌
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        builder.withClient("xujm")
                .secret(passwordEncoder.encode("xujm"))
                .authorizedGrantTypes("password","refresh_token")
                .scopes("all","read","write")
                //token有效期
                .accessTokenValiditySeconds(720000)
                 //refresh_token 有效期
                .refreshTokenValiditySeconds(720000);
    }



    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        //oauthServer.authenticationEntryPoint()
        oauthServer.addTokenEndpointAuthenticationFilter(new EncryptFilter());
        //oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }
}
