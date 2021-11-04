package com.myMovieList.config.swagger;

import java.util.Arrays;
import java.util.List;

import com.myMovieList.model.User;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import io.swagger.v3.oas.models.security.SecurityScheme.In;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.myMovieList.controller")).paths(PathSelectors.ant("/**"))
                .build().ignoredParameterTypes(User.class)
                .securitySchemes(Arrays.asList(new ApiKey("Token Access", HttpHeaders.AUTHORIZATION, In.HEADER.name())))
                .securityContexts(Arrays.asList(securityContext())).apiInfo(apiInfo());
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("ADMIN", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Token Access", authorizationScopes));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("MyMovieList Documentation")
                .description("A list of movies for organize what you wanna watch").version("1.0.0")
                .license("Apache License Version 2.0").licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
                .contact(new Contact("Pedro", "https://pedrobonfilio.online/", "pedroblimaa@gmail.com")).build();
    }
}
