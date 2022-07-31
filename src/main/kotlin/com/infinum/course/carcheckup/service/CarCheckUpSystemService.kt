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
        //println(carCheckupRepository.findCarManufacturerCountCarManufacturerGroupByCarManufacturer())
        //var mapa = carCheckupRepository.findByManufacturerr()

        var list = carCheckupRepository.findByManufacturer()
        var map = mutableMapOf<String,Long>()
        for (el in list) {
            if (map.putIfAbsent(el, 1) != null)
            map.put(el, map[el]?.plus(1) ?: 1)
            //map[el] = temp
        }
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