package com.infinum.course.carcheckup.controller

import com.infinum.course.carcheckup.CarNotFoundException
import com.infinum.course.carcheckup.entity.CarCheckUp
import com.infinum.course.carcheckup.repository.CarCheckUpRepository
import com.infinum.course.carcheckup.repository.JdbcCarCheckUpRepo
import com.infinum.course.carcheckup.service.CarCheckUpSystemService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class CarCheckUpController (
private val carCheckUpRepository: JdbcCarCheckUpRepo){

    @PostMapping("/add-checkup")
    @ResponseBody
    fun addCheckUp(@RequestBody checkUp: CarCheckUp):ResponseEntity<CarCheckUp>{
        val newCheckUp = carCheckUpRepository.addCheckUp(checkUp.workerName, checkUp.price, checkUp.carId)
        println(carCheckUpRepository.getCheckUps())
        return ResponseEntity(newCheckUp, HttpStatus.OK)

    }

    @ExceptionHandler(value = [(CarNotFoundException::class)])
    fun handleException(ex: CarNotFoundException): ResponseEntity<String> {
        println("There is no such car")
        return ResponseEntity("No CheckUps for that car",HttpStatus.BAD_REQUEST)
    }


}