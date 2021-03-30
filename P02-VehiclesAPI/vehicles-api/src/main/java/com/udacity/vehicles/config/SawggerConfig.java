package com.udacity.vehicles.config;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.service.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;


@Configuration
@EnableSwagger2
@ApiResponses(value = {
        @ApiResponse(code = 400,message = "Bad Request"),
        @ApiResponse(code = 401,message = "Not Authorized to access this resource"),
        @ApiResponse(code = 500,message = "Server Internal Error"),
})
public class SawggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Vehickes API",
                "This API returns a list of car locations and pricing.",
                "1.0",
                "http://www.udacity.com/tos",
                new Contact("Mohammed Alotaibi", "www.udacity.com", "donatscript@gmail.com"),
                "License of API", "http://www.udacity.com/license", Collections.emptyList());
    }

}
