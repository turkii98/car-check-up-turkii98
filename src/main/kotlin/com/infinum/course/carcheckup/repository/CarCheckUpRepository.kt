package com.infinum.course.carcheckup.repository

import com.infinum.course.car.entity.Car
import com.infinum.course.car.repository.CarRepository
import com.infinum.course.carcheckup.entity.CarCheckUp
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

interface CarCheckUpRepository : JpaRepository<CarCheckUp, Long>{

    @Query(nativeQuery = true, value = "select manufacturer from carcheckup join car on car.id = carcheckup.car_id")
    fun findByManufacturer(): List<String>
    fun save(carcheckup: CarCheckUp): CarCheckUp
    fun findByCarId(pageable: Pageable, uuid: UUID): Page<CarCheckUp>
    fun findAllByCarId(carId: UUID): MutableList<CarCheckUp>

    //fun deleteAll()

}

