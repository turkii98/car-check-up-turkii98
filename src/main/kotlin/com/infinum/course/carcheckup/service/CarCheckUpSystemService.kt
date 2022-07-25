package com.infinum.course.carcheckup.service


import com.infinum.course.car.repository.CarRepository

import com.infinum.course.carcheckup.repository.CarCheckUpRepository
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class CarCheckUpSystemService (
    var jdbcTemplate: NamedParameterJdbcTemplate,
    private val carRepository: CarRepository,
    private val carCheckupRepository: CarCheckUpRepository){



    fun countCheckUps(): MutableMap<String,Long> {
        var map = carCheckupRepository.findManufacturer()
        println(map.keys)
        println(map.values)
        return map
    }


    fun isCheckUpNecessary(id: UUID): Boolean {
        val list = carCheckupRepository.findAllByCarId(id)
        val returnValue = list.none{ (it.performedAt.isAfter(LocalDateTime.now().minusYears(1)))}
        if(returnValue){
            println("TREBA")
            return returnValue
        }
        else return false
    }


}