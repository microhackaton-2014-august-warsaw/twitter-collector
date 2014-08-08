package com.ofg.microservice.config

import com.ofg.infrastructure.web.config.SwaggerConfiguration
import com.ofg.infrastructure.web.config.WebInfrastructureConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import([WebInfrastructureConfiguration, SwaggerConfiguration])
class WebAppConfiguration {
}
