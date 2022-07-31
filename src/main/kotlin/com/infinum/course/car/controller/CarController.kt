package com.infinum.course.carcheckup.controller

import com.infinum.course.car.dto.CarDTO
import com.infinum.course.car.dto.CarRequestDTO
import com.infinum.course.car.dto.CarResource
import com.infinum.course.car.dto.CarResourceAssembler
import com.infinum.course.car.entity.Car
import com.infinum.course.car.repository.CarRepository
import com.infinum.course.car.service.CarService
import com.infinum.course.carcheckup.CarNotFoundException
import com.infinum.course.carcheckup.service.CarCheckUpSystemService
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.PagedModel
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@Controller
@RequestMapping("/car")
class CarController (
    private val carCheckUpSystemService: CarCheckUpSystemService,
    private val carRepository: CarRepository,
    private val carService: CarService,
    private val carResourceAssembler: CarResourceAssembler
){

    @PostMapping
    @ResponseBody
    fun addCar(@RequestBody carRequest: CarRequestDTO):ResponseEntity<CarResource>{
        val newCar = carService.addCar(carRequest)
        val location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(mapOf("id" to newCar?.id))
            .toUri()
        return ResponseEntity.created(location).body(carResourceAssembler.toModel(newCar))
    }

    @GetMapping
    @ResponseBody
    fun getCars(
        pageable: Pageable,
        pagedResourcesAssembler: PagedResourcesAssembler<Car>
    ): ResponseEntity<PagedModel<CarResource>>
    {
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(carService.getAllCars(pageable), carResourceAssembler))
    }


    @GetMapping("/{id}")
    @ResponseBody
    fun getCar(
        @PathVariable("id") id: UUID
        ):ResponseEntity<CarResource>{
        var a= ResponseEntity(carResourceAssembler.toModel(carService.getCar(id)), HttpStatus.OK)
        a.body.needCheckUp=false
        return a
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