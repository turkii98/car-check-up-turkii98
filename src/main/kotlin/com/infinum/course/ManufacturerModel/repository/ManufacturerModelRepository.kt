package com.infinum.course.ManufacturerModel.repository

import com.infinum.course.ManufacturerModel.entity.ManufacturerModel
import org.springframework.data.repository.Repository
import java.util.*


interface ManufacturerModelRepository: Repository<ManufacturerModel, UUID> {
    fun findById(id: UUID): ManufacturerModel
    fun save(manufacturerModel: ManufacturerModel) : ManufacturerModel
    fun findByManufacturerAndModel(manufacturer: String, model: String): ManufacturerModel

}