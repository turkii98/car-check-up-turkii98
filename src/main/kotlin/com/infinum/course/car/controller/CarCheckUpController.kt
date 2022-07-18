package com.infinum.course.car.controller

import com.infinum.course.checkup.entity.Car
import com.infinum.course.car.entity.CarCheckUp
import com.infinum.course.car.service.CarCheckUpSystemService
import com.infinum.course.checkup.CarCheckUpNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class CarCheckUpController (private val carCheckUpSystemService: CarCheckUpSystemService){

    @PostMapping("/add-checkup")
    @ResponseBody
    fun addCheckUp(@RequestBody checkUp: CarCheckUp):ResponseEntity<CarCheckUp>{
        val newCheckUp = carCheckUpSystemService.addCheckUp(checkUp.workerName, checkUp.price, checkUp.carId)
        println(carCheckUpSystemService.getCheckUps())
        return ResponseEntity(newCheckUp, HttpStatus.OK)

    }

    @ExceptionHandler(value = [(Exception::class)])
    fun handleException(ex: Exception): ResponseEntity<String> {
        println("There is no such car")
        return ResponseEntity("No CheckUps for that car",HttpStatus.BAD_REQUEST)
    }


}