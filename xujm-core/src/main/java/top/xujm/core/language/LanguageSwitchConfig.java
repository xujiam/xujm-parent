package top.xujm.core.language;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

/**
 * @author Xujm
 * LocaleContextHolder.getLocale()获取当前语言WebMvcConfigurerAdapter WebMvcConfigurationSupport
 */
@Configuration
public class LanguageSwitchConfig{

    /**
     * 配置自己的国际化语言解析器
     */
    @Bean
    public LocaleResolver localeResolver() {
           LangLocalResolver slr = new LangLocalResolver();
//            SessionLocaleResolver slr = new SessionLocaleResolver();
//        AcceptHeaderLocaleResolver slr = new AcceptHeaderLocaleResolver();
//        CookieLocaleResolver slr = new CookieLocaleResolver();
        slr.setDefaultLocale(Language.DEFAULT_LOCALE);
        return slr;
    }

//    @Bean
//    public LocaleChangeInterceptor localeChangeInterceptor() {
//        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
//        lci.setParamName("lang_code");
//        return lci;
//    }
//
//    /**
//     * 配置自己的拦截器
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//       registry.addInterceptor(localeChangeInterceptor());
//    }

    /**
     * 发现如果继承了WebMvcConfigurationSupport，则在yml中配置的相关内容会失效。
     * 需要重新指定静态资源
     * */
//     @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//        super.addResourceHandlers(registry);
//    }

     /**
       * 配置servlet处理
       */
//     @Override
//     public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//         configurer.enable();
//     }

}
