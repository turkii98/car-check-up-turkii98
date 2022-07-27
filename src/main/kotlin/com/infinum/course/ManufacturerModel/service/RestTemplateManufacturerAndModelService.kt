package com.infinum.course.ManufacturerModel.service

import com.infinum.course.ManufacturerModel.dto.ManufacturerModelDTO
import com.infinum.course.ManufacturerModel.entity.ManufacturerModel
import com.infinum.course.ManufacturerModel.repository.ManufacturerModelRepository
import com.infinum.course.ManufacturerModel.response.ManufacturerResponse
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

@Service
class RestTemplateManufacturerAndModelService(
    private val restTemplate: RestTemplate,
    private val manufacturerModelRepository: ManufacturerModelRepository
): ManufacturerAndModelService {

    override fun getModelAndManufacturer(): MutableList<ManufacturerModelDTO> {
        var lista : MutableList<ManufacturerModelDTO> = mutableListOf()
        restTemplate
            .getForObject<ManufacturerResponse>("/api/v1/cars/1")
            .cars.forEach {
                //println("it "+ it)
                lista.add(ManufacturerModelDTO(models = it.models, manufacturer = it.manufacturer))
            }
        println(lista)
        return lista

    }

    fun updateManufacturerModel(x:MutableList<ManufacturerModelDTO>){
        x.forEach() {
            var manu = it.manufacturer
            it.models.forEach() {
                println(it)
                try {
                    var checkIfAlreadyExists = manufacturerModelRepository.findByManufacturerAndModel(manu, it)
                }
                catch (e:Exception){
                    val newModel = ManufacturerModel(manu,it)
                    manufacturerModelRepository.save(newModel)
            }
            }
            println()
        }
    }

}