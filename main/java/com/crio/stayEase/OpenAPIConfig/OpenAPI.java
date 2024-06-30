package com.org.StayEase.configs.OpenAPIConfig;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "StayEase APIs :", version = "1.0", description = "APIs for Online Hotel Booking Application - Stay Ease", license = @License(name = "Apache 2.0", url = "http://springdoc.org")), servers = @Server(url = "http://localhost:8080"))
public class OpenAPI {
}