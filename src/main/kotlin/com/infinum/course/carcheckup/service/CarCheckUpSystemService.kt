package com.infinum.course.carcheckup.service


import com.infinum.course.car.repository.CarRepository
import com.infinum.course.carcheckup.dto.CarCheckUpDTO
import com.infinum.course.carcheckup.entity.CarCheckUp

import com.infinum.course.carcheckup.repository.CarCheckUpRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class CarCheckUpSystemService (
    private val carRepository: CarRepository,
    private val carCheckUpRepository: CarCheckUpRepository){



    fun countCheckUps(): MutableMap<String,Long> {
        var list = carCheckUpRepository.findByManufacturer()
        var map = mutableMapOf<String,Long>()
        for (el in list) {
            if (map.putIfAbsent(el, 1) != null)
            map.put(el, map[el]?.plus(1) ?: 1)
            //map[el] = temp
        }
        return map
    }


    fun isCheckUpNecessary(id: UUID): Boolean {
        val list = carCheckUpRepository.findAllByCarId(id)
        val returnValue = list.none{ (it.performedAt.isAfter(LocalDateTime.now().minusYears(1)))}
        if(returnValue){
            println("TREBA")
            return returnValue
        }
        else return false
    }

    fun addCheckUp(carCheckUpRequest: CarCheckUpDTO): CarCheckUp {
        val newCar = carRepository.findById(carCheckUpRequest.carId)
        val newCheckUp = CarCheckUp(
            id = carCheckUpRequest.id,
            performedAt = carCheckUpRequest.performedAt,
            workerName = carCheckUpRequest.workerName,
            price = carCheckUpRequest.price,
            car = newCar
        )
        val carCheckUpResponse = carCheckUpRepository.save(newCheckUp)
        println(carCheckUpRepository.findAll())
        return carCheckUpResponse
    }

    fun findCheckUpByCarId(pageable: Pageable, carId: UUID): Page<CarCheckUp> {
        val newCar = carRepository.findById(carId)
        return carCheckUpRepository.findByCarId(pageable, carId)
    }

}