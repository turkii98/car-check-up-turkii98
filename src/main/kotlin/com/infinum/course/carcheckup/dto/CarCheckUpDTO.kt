package com.infinum.course.carcheckup.dto

import java.time.LocalDateTime
import java.util.*

class CarCheckUpDTO (
    val id: Long = (Math.random()*1000).toLong(),
    val performedAt: LocalDateTime = LocalDateTime.now(),
    val workerName: String,
    val price: Long,
    val carId: UUID
)