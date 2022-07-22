package com.infinum.course.carcheckup.controller

import com.infinum.course.car.CarDTO
import com.infinum.course.car.entity.Car
import com.infinum.course.car.repository.CarRepository
import com.infinum.course.car.repository.JdbcCarRepository
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import com.infinum.course.carcheckup.CarNotFoundException
import com.infinum.course.carcheckup.repository.JdbcCarCheckUpRepo
import com.infinum.course.carcheckup.service.CarCheckUpSystemService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class CarController (
    private val carCheckUpSystemService: CarCheckUpSystemService,
    private val carRepository: JdbcCarRepository,
    private val carCheckUpRepository: JdbcCarCheckUpRepo
){

    @PostMapping("/add-car")
    @ResponseBody
    fun addCar(@RequestBody car: Car):ResponseEntity<Car>{
        val newCar = carRepository.addCar(car.manufacturer,car.model, car.productionYear, car.vin)
        return ResponseEntity(newCar, HttpStatus.OK)

    }

    @GetMapping("/get-stats")
    @ResponseBody
    fun getStats(): ResponseEntity<Map<String?, Long?>> {
        return ResponseEntity(carCheckUpSystemService.countCheckUps(), HttpStatus.OK)
    }

    @GetMapping("/get-car")
    @ResponseBody
    fun getCar(@RequestParam("id", defaultValue = "123") id: Long):ResponseEntity<CarDTO>{
        val newCar = carRepository.findCarById(id)
        val newCarDTO = CarDTO(newCar ?: throw CarNotFoundException(id))
        val list =carCheckUpRepository.getCheckUpsById(id)
        newCarDTO.checkUps = list
        val checkNeccessary = carCheckUpSystemService.isCheckUpNecessary(id)
        newCarDTO.needCheckUp = checkNeccessary
        return ResponseEntity(newCarDTO, HttpStatus.OK)
    }

    @ExceptionHandler(value = [CarNotFoundException::class])
    fun handleException(ex: CarNotFoundException): ResponseEntity<String> {
        println("There is no such car")
        return ResponseEntity("No CheckUps for that car",HttpStatus.BAD_REQUEST)
    }

}