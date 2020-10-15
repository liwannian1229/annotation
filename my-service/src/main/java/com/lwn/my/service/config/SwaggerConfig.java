package com.lwn.my.service.config;

import com.lwn.my.service.context.UserContext;
import com.lwn.repo.model.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@Profile({"dev", "test"})
public class SwaggerConfig {

    @Value("${spring.profiles.active}")
    private String profilesActive;

    @Autowired
    private UserContext userContext;

    @Bean
    public Docket createRestApi() {

        UserInfo userInfo = new UserInfo();
        userInfo.setId(1000L);
        userInfo.setPassword("12345678");
        userInfo.setName("测试");
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder
                .parameterType("header")
                .name("token")
                .defaultValue(userContext.login(userInfo)) // 公钥加密默认值
                .description("header中token 模拟admin用户登录")
                .modelRef(new ModelRef("string"))//指定参数值的类型
                .required(false).build(); //非必需，这里是全局配置
        List<Parameter> aParameters = new ArrayList<>();
        aParameters.add(aParameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(!profilesActive.equalsIgnoreCase("prod"))
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lwn.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(aParameters);

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("学习")
                .description("自我学习")
                .termsOfServiceUrl("http://empet.shenyi.com")
                //创建人
                .contact(new Contact("lwn", "www.baidu.com", ""))
                .version("1.0")
                .build();
    }
}
