package com.infinum.course.car.service

import com.infinum.course.car.dto.CarDTO
import com.infinum.course.car.repository.CarRepository
import com.infinum.course.carcheckup.repository.CarCheckUpRepository
import com.infinum.course.carcheckup.service.CarCheckUpSystemService
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class CarService(
    var jdbcTemplate: NamedParameterJdbcTemplate,
    private val carRepository: CarRepository,
    private val carCheckUpRepository: CarCheckUpRepository,
    private val carCheckUpSystemService: CarCheckUpSystemService){

    fun getCarDTO(id: UUID): CarDTO? {
        println(id)
        val newCar = carRepository.findById(id)
        val newCarDTO = CarDTO(newCar)
        val list = carCheckUpRepository.findAllByCarId(id)
        newCarDTO.checkUps = list
        val checkNeccessary = carCheckUpSystemService.isCheckUpNecessary(id)
        newCarDTO.needCheckUp = checkNeccessary
        println(newCarDTO)
        return newCarDTO
    }

}