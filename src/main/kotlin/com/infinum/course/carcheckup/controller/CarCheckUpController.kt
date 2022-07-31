package com.infinum.course.carcheckup.controller


import com.infinum.course.carcheckup.CarNotFoundException
import com.infinum.course.carcheckup.dto.CarCheckUpDTO
import com.infinum.course.carcheckup.entity.CarCheckUp
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
private val carCheckUpSystemService: CarCheckUpSystemService){

    @GetMapping("/get-car-checkup/{uuid}")
    @ResponseBody
    fun findByCarId(pageable: Pageable, @PathVariable uuid: UUID):ResponseEntity<Page<CarCheckUp>> {
        return ResponseEntity.ok(carCheckUpSystemService.findCheckUpByCarId(pageable, uuid))
    }

    @PostMapping("/add-checkup")
    @ResponseBody
    fun addCheckUp(@RequestBody carCheckUpRequest: CarCheckUpDTO):ResponseEntity<CarCheckUp>{
        val carCheckUpResponse = carCheckUpSystemService.addCheckUp(carCheckUpRequest)
        return ResponseEntity(carCheckUpResponse, HttpStatus.OK)

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