package com.peazy.supplier.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringfoxConfiguration {
    @Bean
    public Docket apiV1() {
        return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(getApiInfo())
        .groupName("api-1.0")
        .select()
        // .apis((Predicate<RequestHandler>) RequestHandlerSelectors.any())
        .apis(RequestHandlerSelectors.basePackage("com.peazy.supplier.restcontroller"))
        .paths(PathSelectors.any())
        .build(); 
    }
    
    private ApiInfo getApiInfo() {
        var builder = new ApiInfoBuilder();
        builder.title("peazy-ausupplierth");
        builder.description("Supplier");
        builder.version("1.0");
        return builder.build();
    }
    
}
