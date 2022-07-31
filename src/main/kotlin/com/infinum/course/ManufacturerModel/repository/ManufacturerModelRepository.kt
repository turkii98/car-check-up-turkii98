package com.infinum.course.ManufacturerModel.repository

import com.infinum.course.ManufacturerModel.entity.ManufacturerModel
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.Repository
import java.util.*


interface ManufacturerModelRepository: Repository<ManufacturerModel, UUID> {
    fun findById(id: UUID): ManufacturerModel
    fun save(manufacturerModel: ManufacturerModel) : ManufacturerModel
    fun findByManufacturerAndModel(manufacturer: String, model: String): ManufacturerModel
    @Cacheable("manufacturerModel")
    fun existsByManufacturerAndModel(manufacturer:String, model:String): Boolean
    fun findAll(): ManufacturerModel


}