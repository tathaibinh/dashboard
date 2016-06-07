package com.excilys.shooflers.dashboard.server;


import com.excilys.shooflers.dashboard.server.security.interceptor.CorsInterceptor;
import com.google.common.base.Predicates;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration SpringBoot.
 */
@SpringBootApplication
@EnableSwagger2
@EnableAspectJAutoProxy
@EnableConfigurationProperties
public class SpringConfiguration extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(SpringConfiguration.class, args);
    }

    @Bean
    public Docket newsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.excilys.shooflers.dashboard.server.rest"))
                .paths(Predicates.not(PathSelectors.regex("/")))
                .build()
                .apiInfo(apiInfo());
    }

    /**
     * CorsInterceptor factory.
     * 
     * @return CorsInterceptor
     */
    @Bean
    public CorsInterceptor corsInterceptor() {
        return new CorsInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(corsInterceptor());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Dashboard REST Api Documentations with Swagger")
                .description("Dashboard REST Api Documentations with Swagger")
                .contact("Groupe Excilys")
                .version("1.0")
                .build();
    }
    
}