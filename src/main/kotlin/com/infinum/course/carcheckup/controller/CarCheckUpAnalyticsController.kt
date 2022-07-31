package com.infinum.course.carcheckup.controller

import com.infinum.course.carcheckup.service.CarCheckUpSystemService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/stats")
class CarCheckUpAnalyticsController (
    private val carCheckUpSystemService: CarCheckUpSystemService
        ){


    @GetMapping()
    @ResponseBody
    fun getStats(): ResponseEntity<Map<String, Long>> {
        return ResponseEntity(carCheckUpSystemService.countCheckUps(), HttpStatus.OK)
    }
}