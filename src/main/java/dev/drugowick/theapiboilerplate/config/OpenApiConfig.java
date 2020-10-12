package dev.drugowick.theapiboilerplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class OpenApiConfig implements WebMvcConfigurer {

    @Bean
    public Docket openApiDocs() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("dev.drugowick"))
                .build()
                .pathMapping("/")
                .apiInfo(apiInfo())
                .securitySchemes(apiKey());
    }

    private List<? extends SecurityScheme> apiKey() {
            return Arrays.asList(new ApiKey("Bearer", "Authorization", "header"));
    }

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Row Level Security Oauth2")
                .description("This helps me validade a specific security solution where I must give access to resources" +
                        " based on the user having a role with the ID of the resource.")
                .version("1.0")
                .contact(new Contact("Bruno Drugowick", "https://drugo.dev", "bruno.drugowick@gmail.com"))
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("swagger-resources/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }
}
