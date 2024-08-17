package com.example.campingontop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.awt.print.Pageable;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalOperationParameters(Arrays.asList(
                        new ParameterBuilder()
                                .name("Authorization")
                                .description("Bearer {token}")
                                .modelRef(new ModelRef("string"))
                                .parameterType("header")
                                .required(false)
                                .build()
                ))
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .groupName("All")
                .ignoredParameterTypes(AuthenticationPrincipal.class, Pageable.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.campingontop"))
                .build()
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket houseApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("House")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.campingontop.house"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket userApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("User")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.campingontop.user"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket likesApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Likes")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.campingontop.likes"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket cartApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Cart")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.campingontop.cart"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket ordersApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Orders")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.campingontop.orders"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket ordersHouseApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("OrderedHouse")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.campingontop.orderedHouse"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket reviewApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Review")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.campingontop.review"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot Project - campingOnTop")
                .description("Hanwha_SW CAMP 2기 - campingOnTop  " +
                        "오른쪽 Authorize 클릭 후 User의 login을 통해 로그인을 우선 해야 합니다." +
                        "User tab의 로그인 후 받은 token을 'Bearer ' + token값을 입력해야 합니다.")
                .version("1.0.0")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }


}