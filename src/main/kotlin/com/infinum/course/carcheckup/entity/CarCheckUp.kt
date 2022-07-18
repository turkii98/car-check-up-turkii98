package com.infinum.course.carcheckup.entity

import java.time.LocalDateTime

data class CarCheckUp (
    val id: Long,
    val performedAt: LocalDateTime = LocalDateTime.now(),
    val workerName: String,
    val price: Long,
    val carId: Long
)

//class CarCheckUpNotFoundException(id: Long) : RuntimeException("Car check-up ID $id not found")