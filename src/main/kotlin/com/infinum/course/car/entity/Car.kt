package com.infinum.course.car.entity

import com.infinum.course.carcheckup.entity.CarCheckUp
import java.time.LocalDate

data class Car (
    val id: Long=123,
    val addedDate: LocalDate= LocalDate.now(),
    val manufacturer: String,
    val model: String,
    val productionYear: String,
    val vin: String,
    val checkUps: MutableList<CarCheckUp> = mutableListOf(),

)