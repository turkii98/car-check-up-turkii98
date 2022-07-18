package com.infinum.course.checkup.repository

import com.infinum.course.car.entity.Car
import com.infinum.course.checkup.entity.CarCheckUp
import java.time.LocalDateTime

//@Component
interface CarCheckUpRepository {
    fun insert(performedAt: LocalDateTime, car: Car): Long
    fun findById(id: Long): CarCheckUp
    fun deleteById(id: Long): CarCheckUp
    fun findAll(): Map<Long, CarCheckUp>
}