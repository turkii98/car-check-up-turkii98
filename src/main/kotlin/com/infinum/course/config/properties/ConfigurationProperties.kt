package com.infinum.course.config.properties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "car-manufacturer-service")
@ConstructorBinding
data class ConfigurationProperties(
    val baseUrl : String = "https://62d922dd5d893b27b2df0731.mockapi.io"
)