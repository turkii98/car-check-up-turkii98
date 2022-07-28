package com.infinum.course.config

import com.infinum.course.ManufacturerModel.service.RestTemplateManufacturerAndModelService
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.util.concurrent.TimeUnit

@Configuration
@EnableScheduling
class SchedulerConfiguration(private val restTemplateManufacturerAndModelService: RestTemplateManufacturerAndModelService){

   @Scheduled (fixedDelay = 1, timeUnit = TimeUnit.DAYS )
   fun updateManufacturerModel() = restTemplateManufacturerAndModelService.updateManufacturerModel(restTemplateManufacturerAndModelService.getModelAndManufacturer())
}