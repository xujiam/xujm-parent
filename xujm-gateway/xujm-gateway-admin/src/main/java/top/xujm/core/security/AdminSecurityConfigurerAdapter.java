package top.xujm.core.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.stereotype.Component;
import top.xujm.generator.security.SecurityConfigurerAdapter;

/**
 * @author Xujm
 */
@Component
public class AdminSecurityConfigurerAdapter implements SecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) {

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/json/**","/layui/**","/src/**",
                "/index.html","/favicon.ico");
    }
}
