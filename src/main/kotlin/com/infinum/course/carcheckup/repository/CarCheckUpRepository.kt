package com.infinum.course.carcheckup.repository

import com.infinum.course.car.entity.Car
import com.infinum.course.carcheckup.entity.CarCheckUp
import java.time.LocalDateTime

//@Component
interface CarCheckUpRepository {
    fun insert(performedAt: LocalDateTime, car: Car): Long
    fun findById(id: Long): CarCheckUp
    fun deleteById(id: Long): CarCheckUp
    fun findAll(): Map<Long, CarCheckUp>
}