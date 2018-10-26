package top.xujm.core.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xujm
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    @Value("${swagger.enable:true}")
    private boolean enableSwagger;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enableSwagger)
                .pathMapping("/")
                .apiInfo(buildApiInf())
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.xujm.modules"))
                .paths(PathSelectors.any())
                .build().securitySchemes(securitySchemes()).globalOperationParameters(setHeaderToken());
    }

    private List<ApiKey> securitySchemes() {
        List<ApiKey> list = new ArrayList<>();
        list.add(new ApiKey("Authorization", "Authorization", "header"));
        return list;
    }

    private ApiInfo buildApiInf(){
        return new ApiInfoBuilder()
                .title("问津项目接口文档")
                .description("spring-boot swagger2")
                .termsOfServiceUrl("")
                .contact(new Contact("xujm", "/api.htm", "xujm@163.com"))
                .build();
    }

    private List<Parameter> setHeaderToken() {
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name("Accept-Language").defaultValue("zh_CN").description("语言").modelRef(new ModelRef("string")).parameterType("header").required(false);
        List<Parameter> aParameters = new ArrayList<>();
        aParameters.add(aParameterBuilder.build());
        aParameterBuilder.name("data").defaultValue("").description("参数加密后数据").modelRef(new ModelRef("string")).parameterType("query").required(false);
        aParameters.add(aParameterBuilder.build());
        return aParameters;
    }

}
