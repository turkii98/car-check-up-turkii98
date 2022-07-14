package com.infinum.course.car.checkup

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.io.FileSystemResource
import org.springframework.core.io.Resource

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
class ApplicationConfig{
    @Bean
    fun resourceFun(dataSource: DataSource): Resource = FileSystemResource(dataSource.dbName)
}