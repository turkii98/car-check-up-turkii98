package com.infinum.course.carcheckup.controller

import com.infinum.course.car.CarDTO
import com.infinum.course.car.entity.Car
import com.infinum.course.car.repository.JdbcCarRepository
import com.infinum.course.carcheckup.CarNotFoundException
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
        println("--------------------------")
       // println(carCheckUpSystemService.getCheckUps())
        //println(carCheckUpSystemService.cars)
        println("--------------------------")

        return ResponseEntity(newCar, HttpStatus.OK)

    }


    @GetMapping("/get-car")
    @ResponseBody
    fun getCar(@RequestParam("id", defaultValue = "123") id: Long):ResponseEntity<CarDTO>{
        println("pukne0")
        val newCar = carCheckUpSystemService.getCarById(id)
        println("pukne1")
        //val checkNeccessary = carCheckUpSystemService.isCheckUpNecessary(newCar.id)
        println("pukne2")
        val newCarDTO = CarDTO(newCar)
        println("pukne3")
        //if(checkNeccessary) {
            //newCarDTO.needCheckUp = true
        //}
        //else {
            newCarDTO.needCheckUp = false

       //  }
        return ResponseEntity(newCarDTO, HttpStatus.OK)
    }

    @ExceptionHandler(value = [CarNotFoundException::class])
    fun handleException(ex: CarNotFoundException): ResponseEntity<String> {
        println("There is no such car")
        //println(ex.message)
        //println(ex.cause)
        return ResponseEntity("No CheckUps for that car",HttpStatus.BAD_REQUEST)
    }

}