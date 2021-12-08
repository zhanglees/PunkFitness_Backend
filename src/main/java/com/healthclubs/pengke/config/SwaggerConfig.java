package com.healthclubs.pengke.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi(){

        ParameterBuilder tokenPar = new ParameterBuilder();
        ParameterBuilder tokenPartime = new ParameterBuilder();
        ParameterBuilder tokenParsession = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();

        tokenPar.name("authorization").description("令牌").modelRef(new ModelRef("string"))
                .parameterType("header").required(false).build();

        tokenPartime.name("timestamp").description("时间戳").modelRef(new ModelRef("string"))
                .parameterType("header").required(false).build();

        tokenParsession.name("sessionkey").description("sessonkey").modelRef(new ModelRef("string"))
                .parameterType("header").required(false).build();

        pars.add(tokenPar.build());
        pars.add(tokenPartime.build());
        pars.add(tokenParsession.build());


        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.healthclubs.pengke.controller"))
                //.apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build()// ;
         .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("pengke API Doc")
                .description("This is a restful api document of pengke.")
                .version("1.0")
                .build();
    }

}