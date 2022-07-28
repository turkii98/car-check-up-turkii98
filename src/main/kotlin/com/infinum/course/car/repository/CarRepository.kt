package com.infinum.course.car.repository

import com.infinum.course.car.entity.Car
import org.springframework.data.domain.Page
import org.springframework.data.repository.Repository
import org.springframework.data.domain.Pageable
import java.util.UUID

interface CarRepository: Repository<Car, UUID> {
    fun findById(id: UUID): Car

    fun save(car: Car) : Car
    fun findAll(pageable: Pageable): Page<Car>

    fun deleteAll()

    fun saveAll(car: Iterable<Car>): Iterable<Car>

}

