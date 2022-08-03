package com.infinum.course.config

import com.infinum.course.config.properties.ConfigurationProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class RestConfiguration {

    @Bean
    fun provideRestTemplate(
        restTemplateBuilder: RestTemplateBuilder,
        configurationProperties: ConfigurationProperties
    ): RestTemplate {
        return restTemplateBuilder.rootUri(configurationProperties.baseUrl).build()

    }


}