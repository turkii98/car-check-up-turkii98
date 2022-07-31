package com.infinum.course.carcheckup.entity

import com.infinum.course.car.entity.Car
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

    @ManyToOne
    @JoinColumn(name="car_id",)
    val car: Car
)

//class CarCheckUpNotFoundException(id: Long) : RuntimeException("Car check-up ID $id not found")