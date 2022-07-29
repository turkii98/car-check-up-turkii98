package com.infinum.course.config

import com.infinum.course.ManufacturerModel.service.RestTemplateManufacturerAndModelService
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling


@Configuration
@EnableScheduling
class SchedulerConfiguration(private val restTemplateManufacturerAndModelService: RestTemplateManufacturerAndModelService){

}