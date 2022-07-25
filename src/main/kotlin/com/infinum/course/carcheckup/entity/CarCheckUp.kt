package com.infinum.course.carcheckup.entity

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "carcheckup")
class CarCheckUp (
    @Id
    val id: Long = (Math.random()*1000).toLong(),
    val performedAt: LocalDateTime = LocalDateTime.now(),
    val workerName: String,
    val price: Long,
    val carId: UUID
)

//class CarCheckUpNotFoundException(id: Long) : RuntimeException("Car check-up ID $id not found")