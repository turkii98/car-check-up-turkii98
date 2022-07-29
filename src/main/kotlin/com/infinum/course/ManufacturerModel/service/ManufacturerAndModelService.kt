package com.infinum.course.ManufacturerModel.service

import com.infinum.course.ManufacturerModel.dto.ManufacturerModelDTO

interface ManufacturerAndModelService {
    fun getModelAndManufacturer(): List<ManufacturerModelDTO>

    //fun existsManufacturerModel(manufacturer: String, model: String): Boolean

}