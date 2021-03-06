package com.healthclubs.pengke;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@ComponentScan(basePackages = {"com.healthclubs.pengke.*"})
@MapperScan("com.healthclubs.pengke.mapper")
//@SpringBootApplication(exclude = {MultipartAutoConfiguration.class}
@SpringBootApplication
public class PengkeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PengkeApplication.class, args);
    }

}
