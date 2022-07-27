package com.infinum.course.carcheckup.controller

import com.infinum.course.car.CarCheckUpNotFoundException
import com.infinum.course.car.repository.CarRepository
import com.infinum.course.carcheckup.CarNotFoundException
import com.infinum.course.carcheckup.entity.CarCheckUp
import com.infinum.course.carcheckup.repository.CarCheckUpRepository
import com.infinum.course.carcheckup.service.CarCheckUpSystemService
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.UUID

@Controller
class CarCheckUpController (
private val carCheckUpRepository: CarCheckUpRepository,
private val carRepository: CarRepository){

    @GetMapping("/get-car-checkup")
    @ResponseBody
    fun findByCarId(pageable: Pageable, @RequestParam uuid: UUID):ResponseEntity<Page<CarCheckUp>> {
        val newCar = carRepository.findById(uuid)
        val zero: Long = 0
        if(ResponseEntity.ok(carCheckUpRepository.findByCarId(pageable, uuid)).body.totalElements == zero) throw EmptyResultDataAccessException(0)
        return ResponseEntity.ok(carCheckUpRepository.findByCarId(pageable, uuid))
    }

    @PostMapping("/add-checkup")
    @ResponseBody
    fun addCheckUp(@RequestBody checkUp: CarCheckUp):ResponseEntity<CarCheckUp>{
        val newCar = carRepository.findById(checkUp.carId)
        val newCheckUp = carCheckUpRepository.save(checkUp)
        println(carCheckUpRepository.findAll())
        return ResponseEntity(newCheckUp, HttpStatus.OK)

    }

    @ExceptionHandler(value = [(CarNotFoundException::class)])
    fun handleException(ex: CarNotFoundException): ResponseEntity<String> {
        println("There is no such car")
        return ResponseEntity("No CheckUps for that car",HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [(EmptyResultDataAccessException::class)])
    fun handleException(ex: EmptyResultDataAccessException): ResponseEntity<String> {
        println("There is no such car")
        return ResponseEntity("Car with such ID does not exist",HttpStatus.BAD_REQUEST)
    }


}