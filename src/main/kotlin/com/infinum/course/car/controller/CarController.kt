package com.infinum.course.carcheckup.controller

import com.infinum.course.car.entity.Car
import com.infinum.course.carcheckup.service.CarCheckUpSystemService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class CarController (private val carCheckUpSystemService: CarCheckUpSystemService){

    @PostMapping("/add-car")
    @ResponseBody
    fun addCar(@RequestBody car: Car):ResponseEntity<Car>{
        val newCar = carCheckUpSystemService.addCar(car.manufacturer,car.model, car.productionYear, car.vin)
        println(carCheckUpSystemService.getCheckUps())
        return ResponseEntity(newCar, HttpStatus.OK)

    }


    @GetMapping("/get-car")
    @ResponseBody
    fun getCar(@RequestParam id: Long):ResponseEntity<Car>{
        val newCar = carCheckUpSystemService.getCarById(id)
        val checkNeccessary = carCheckUpSystemService.isCheckUpNecessary(newCar.id)
        if(checkNeccessary) {
            carCheckUpSystemService.getCarById(id).needCheckUp = true
        }
        else {
            carCheckUpSystemService.getCarById(id).needCheckUp = false

        }
        return ResponseEntity(newCar, HttpStatus.OK)
    }

    @ExceptionHandler(value = [Exception::class])
    fun handleException(ex: Exception): ResponseEntity<String> {
        println("There is no such car")
        return ResponseEntity("No CheckUps for that car",HttpStatus.BAD_REQUEST)
    }

}