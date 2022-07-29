package com.infinum.course.carcheckup.controller

import com.infinum.course.ManufacturerModel.repository.ManufacturerModelRepository
import com.infinum.course.car.dto.CarDTO
import com.infinum.course.car.dto.CarRequestDTO
import com.infinum.course.car.entity.Car
import com.infinum.course.car.repository.CarRepository
import com.infinum.course.car.service.CarService
import com.infinum.course.carcheckup.CarNotFoundException
import com.infinum.course.carcheckup.service.CarCheckUpSystemService
import org.springframework.dao.EmptyResultDataAccessException
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
    private val carService: CarService,
    private val manufacturerModelRepository: ManufacturerModelRepository
){

    @PostMapping("/add-car")
    @ResponseBody
    fun addCar(@RequestBody carRequest: CarRequestDTO):ResponseEntity<Car>{
        val newMmdb = manufacturerModelRepository.findById(carRequest.modelId)
        val newCar = carService.addCar(carRequest)
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

    @GetMapping("/get-car/{id}")
    @ResponseBody
    fun getCar(@PathVariable("id") id: UUID):ResponseEntity<CarDTO>{
        return ResponseEntity(carService.getCarDTO(id), HttpStatus.OK)
    }

    @ExceptionHandler(value = [CarNotFoundException::class])
    fun handleException(ex: CarNotFoundException): ResponseEntity<String> {
        println("There is no such car")
        println(ex.stackTrace)
        return ResponseEntity("No CheckUps for that car",HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [EmptyResultDataAccessException::class])
    fun handleException(ex: EmptyResultDataAccessException): ResponseEntity<String> {
        println("There is no such model")
        println(ex.printStackTrace())
        return ResponseEntity("No such model",HttpStatus.BAD_REQUEST)
    }

}