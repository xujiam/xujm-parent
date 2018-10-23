package top.xujm.generator.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;

/**
 * @author Xujm
 */
public interface SecurityConfigurerAdapter {

    void configure(HttpSecurity http) throws Exception;


    void configure(WebSecurity web);

}
