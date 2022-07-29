package com.infinum.course.car.dto

import com.infinum.course.car.entity.Car
import com.infinum.course.carcheckup.entity.CarCheckUp
import java.util.*

data class CarDTO (
    val car:Car,
    var checkUps: MutableList<CarCheckUp> = mutableListOf(),
    var needCheckUp: Boolean = false
): java.io.Serializable