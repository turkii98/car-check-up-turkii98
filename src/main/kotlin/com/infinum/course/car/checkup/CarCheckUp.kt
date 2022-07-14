package com.infinum.course.car.checkup

import java.time.LocalDateTime

data class CarCheckUp (
    val id: Long,
    val performedAt: LocalDateTime,
    val car: Car
)

//class CarCheckUpNotFoundException(id: Long) : RuntimeException("Car check-up ID $id not found")