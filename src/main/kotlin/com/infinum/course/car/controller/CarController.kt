package com.infinum.course.carcheckup.controller

import com.infinum.course.car.CarDTO
import com.infinum.course.car.entity.Car
import com.infinum.course.car.repository.CarRepository
import com.infinum.course.car.service.CarService
import com.infinum.course.carcheckup.CarNotFoundException
import com.infinum.course.carcheckup.service.CarCheckUpSystemService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*
import org.springframework.data.domain.Pageable

@Controller
class CarController (
    private val carCheckUpSystemService: CarCheckUpSystemService,
    private val carRepository: CarRepository,
    private val carService: CarService
){

    @PostMapping("/add-car")
    @ResponseBody
    fun addCar(@RequestBody car: Car):ResponseEntity<Car>{
        val newCar = carRepository.save(car)
        return ResponseEntity(newCar, HttpStatus.OK)

    }

    @GetMapping("/get-cars")
    @ResponseBody
    fun getCars(pageable: Pageable) = ResponseEntity.ok(carRepository.findAll(pageable))


    @GetMapping("/get-stats")
    @ResponseBody
    fun getStats(): ResponseEntity<Map<String, Long>> {

        return ResponseEntity(carCheckUpSystemService.countCheckUps(), HttpStatus.OK)
    }

    @GetMapping("/get-car")
    @ResponseBody
    fun getCar(@RequestParam("id") id: UUID):ResponseEntity<CarDTO>{
        return ResponseEntity(carService.getCarDTO(id), HttpStatus.OK)
    }

    @ExceptionHandler(value = [CarNotFoundException::class])
    fun handleException(ex: CarNotFoundException): ResponseEntity<String> {
        println("There is no such car")
        return ResponseEntity("No CheckUps for that car",HttpStatus.BAD_REQUEST)
    }

}