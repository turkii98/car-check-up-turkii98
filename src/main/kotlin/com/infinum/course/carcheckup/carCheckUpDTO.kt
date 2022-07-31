package com.infinum.course.carcheckup

import java.time.LocalDateTime
import java.util.UUID


class CarCheckUpDTO (
    val id: Long = (Math.random()*1000).toLong(),
    val performedAt: LocalDateTime = LocalDateTime.now(),
    val workerName: String,
    val price: Long,
    val carId: UUID
)