package com.infinum.course.ManufacturerModel.service

import com.infinum.course.ManufacturerModel.dto.ManufacturerModelDTO
import com.infinum.course.ManufacturerModel.entity.ManufacturerModel
import com.infinum.course.ManufacturerModel.repository.ManufacturerModelRepository
import com.infinum.course.ManufacturerModel.response.ManufacturerResponse
import org.springframework.cache.annotation.CacheEvict
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.util.concurrent.TimeUnit

@Service
class RestTemplateManufacturerAndModelService(
    private val restTemplate: RestTemplate,
    private val manufacturerModelRepository: ManufacturerModelRepository
): ManufacturerAndModelService {

    override fun getModelAndManufacturer(): MutableList<ManufacturerModelDTO> {
        val lista : MutableList<ManufacturerModelDTO> = mutableListOf()
        restTemplate
            .getForObject<ManufacturerResponse>("/api/v1/cars/1")
            .cars.forEach {
                lista.add(ManufacturerModelDTO(models = it.models, manufacturer = it.manufacturer))
            }
        println(lista)
        return lista

    }

    fun updateManufacturerModel(x:MutableList<ManufacturerModelDTO>){
        x.forEach() {
            val manu = it.manufacturer
            it.models.forEach() {
                println(it)
                val checkIfAlreadyExists = manufacturerModelRepository.existsByManufacturerAndModel(manufacturer = manu, model = it)
                if(!checkIfAlreadyExists) {
                    val newModel = ManufacturerModel(manu,it)
                    manufacturerModelRepository.save(newModel)
                }
            }
            println()
        }
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.DAYS)
    @CacheEvict("manufacturerModel")
    fun repeatable() {
        updateManufacturerModel(getModelAndManufacturer())
    }

}