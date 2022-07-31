package com.infinum.course.carcheckup.controller

import com.infinum.course.carcheckup.CarCheckUpDTO
import com.infinum.course.carcheckup.CarNotFoundException
import com.infinum.course.carcheckup.entity.CarCheckUp
import com.infinum.course.carcheckup.repository.CarCheckUpRepository
import com.infinum.course.carcheckup.service.CarCheckUpSystemService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.UUID

@Controller
class CarCheckUpController (
private val carCheckUpRepository: CarCheckUpRepository){

    @GetMapping("/get-car-checkup/{uuid}")
    @ResponseBody
    fun findByCarId(pageable: Pageable, @PathVariable uuid: UUID) = ResponseEntity.ok(carCheckUpRepository.findByCarId(pageable, uuid))

    @PostMapping("/add-checkup")
    @ResponseBody
    fun addCheckUp(@RequestBody carCheckUpRequest: CarCheckUpDTO):ResponseEntity<CarCheckUpDTO>{
        val carCheckUpResponse = carCheckUpRepository.save(carCheckUpRequest)
        println(carCheckUpRepository.findAll())
        return ResponseEntity(carCheckUpResponse, HttpStatus.OK)

    }

    @ExceptionHandler(value = [(CarNotFoundException::class)])
    fun handleException(ex: CarNotFoundException): ResponseEntity<String> {
        println("There is no such car")
        return ResponseEntity("No CheckUps for that car",HttpStatus.BAD_REQUEST)
    }


}