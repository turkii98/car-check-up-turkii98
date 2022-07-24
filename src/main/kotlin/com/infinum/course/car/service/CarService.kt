package com.infinum.course.car.service

import com.infinum.course.car.CarDTO
import com.infinum.course.car.entity.Car
import com.infinum.course.car.repository.JdbcCarRepository
import com.infinum.course.carcheckup.CarNotFoundException
import com.infinum.course.carcheckup.repository.JdbcCarCheckUpRepo
import com.infinum.course.carcheckup.service.CarCheckUpSystemService
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service

@Service
class CarService(
var jdbcTemplate: NamedParameterJdbcTemplate,
private val carRepository: JdbcCarRepository,
private val carCheckUpRepository: JdbcCarCheckUpRepo,
private val carCheckUpSystemService: CarCheckUpSystemService){

    fun getCarDTO(id: Long): CarDTO {
        val newCar = carRepository.findCarById(id)
        val newCarDTO = CarDTO(newCar ?: throw CarNotFoundException(id))
        val list = carCheckUpRepository.getCheckUpsById(id)
        newCarDTO.checkUps = list
        val checkNeccessary = carCheckUpSystemService.isCheckUpNecessary(id)
        newCarDTO.needCheckUp = checkNeccessary
        return newCarDTO
    }
}