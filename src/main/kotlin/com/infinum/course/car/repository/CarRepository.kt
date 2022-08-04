package com.infinum.course.car.repository

import com.infinum.course.ManufacturerModel.entity.ManufacturerModel
import com.infinum.course.car.entity.Car
import org.springframework.data.domain.Page
import org.springframework.data.repository.Repository
import org.springframework.data.domain.Pageable
import java.util.UUID

interface CarRepository: Repository<Car, UUID> {
    fun findById(id: UUID): Car

    fun save(car: Car) : Car
    fun findAll(pageable: Pageable): Page<Car>

    fun deleteCarById(id: UUID): String

    fun existsById(id: UUID): Boolean

    fun saveAll(car: Iterable<Car>): Iterable<Car>
    fun findByManufacturerModel(pageable: Pageable,manufacturerModel: ManufacturerModel): Page<Car>

}

