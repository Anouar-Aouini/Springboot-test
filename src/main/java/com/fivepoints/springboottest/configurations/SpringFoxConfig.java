package com.fivepoints.springboottest.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

@Configuration
@Import(springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .produces(new LinkedHashSet<>(Arrays.asList("application/json", "application/xml")))
                .protocols(new HashSet<>(Arrays.asList("http","https")))
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    // Describe your apis
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring boot test")
                .description("This page lists all the rest apis for Spring boot test.")
                .version("1.0.0")
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .termsOfServiceUrl("https://gitee.com/log4j/pig")
                .contact(new Contact("Anouar Aouini","https://github.com/Anouar_Aouini","anoirawini1571@gmail.com"))
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }
}