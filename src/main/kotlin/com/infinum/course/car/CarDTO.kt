package com.infinum.course.car

import com.infinum.course.car.entity.Car
import com.infinum.course.carcheckup.entity.CarCheckUp

data class CarDTO (
    val car:Car,
    var checkUps: MutableList<CarCheckUp> = mutableListOf(),
    var needCheckUp: Boolean = false
)