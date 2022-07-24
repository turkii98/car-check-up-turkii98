package com.infinum.course.carcheckup.service

import com.infinum.course.car.CarCheckUpNotFoundException
import com.infinum.course.car.CarDTO
import com.infinum.course.carcheckup.CarNotFoundException
import com.infinum.course.car.entity.Car
import com.infinum.course.car.repository.JdbcCarRepository
import com.infinum.course.carcheckup.entity.CarCheckUp
import com.infinum.course.carcheckup.repository.CarCheckUpRepository
import com.infinum.course.carcheckup.repository.JdbcCarCheckUpRepo
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import java.sql.ResultSet
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class CarCheckUpSystemService (
    var jdbcTemplate: NamedParameterJdbcTemplate,
    private val carRepository: JdbcCarRepository,
    private val carCheckupRepository: JdbcCarCheckUpRepo){



    fun countCheckUps(): MutableMap<String,Long> {
        var map = carCheckupRepository.getManufacturerCount()
        return map
    }


    fun isCheckUpNecessary(id: Long): Boolean {
        val list = carCheckupRepository.getCheckUpsById(id)
        val returnValue = list.none{ (it.performedAt.isAfter(LocalDateTime.now().minusYears(1)))}
        if(returnValue){
            println("TREBA")
            return returnValue
        }
        else return false
    }


}