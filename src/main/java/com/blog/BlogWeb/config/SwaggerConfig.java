package com.blog.BlogWeb.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(getInfo())
        .securityContexts(getSecurityContexts())
        .securitySchemes(Arrays.asList(apiKeys()))
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo getInfo() {
    return new ApiInfo("Blog Backend Application",
        "Blog Backend Application using SpringBoot and MySQL", "1.0", "Terms of Service",
        new Contact("Ravikumar", "", "ravikumarrajhnas11@yahoo.com"), "License of APIs",
        "API License URL",
        Collections.emptyList());
  }
  private ApiKey apiKeys(){
    return new ApiKey("JWT",Constants.AUTHORIZATION_HEADER,"header");
  }

  private List<SecurityContext> getSecurityContexts(){
   return Arrays.asList(SecurityContext.builder().securityReferences(getSecurityReferences()).build());
  }
  private List<SecurityReference> getSecurityReferences(){
    AuthorizationScope scope = new AuthorizationScope("global","accessEverything");
    return Arrays.asList(new SecurityReference("JWT",new AuthorizationScope[]{scope}));
  }
}
