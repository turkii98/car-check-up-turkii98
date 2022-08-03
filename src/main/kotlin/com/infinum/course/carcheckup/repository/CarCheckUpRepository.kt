package com.infinum.course.carcheckup.repository


import com.infinum.course.car.entity.Car
import com.infinum.course.carcheckup.dto.JpaQueryCountBean
import com.infinum.course.carcheckup.entity.CarCheckUp
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository
import java.time.LocalDateTime
import java.util.*

interface CarCheckUpRepository : Repository<CarCheckUp, Long> {

    @Query(value= "SELECT new com.infinum.course.carcheckup.dto.JpaQueryCountBean(mm.manufacturer, count(mm)) " +
            "from CarCheckUp cc join Car c on c.id = cc.car.id " +
            "join ManufacturerModel mm on mm.id = c.manufacturerModel.id " +
            "group by mm.manufacturer")
    fun findCarCheckUpCount(): List<JpaQueryCountBean>


    fun save(carcheckup: CarCheckUp): CarCheckUp
    fun findByCarId(pageable: Pageable, uuid: UUID): Page<CarCheckUp>
    fun findByCarIdOrderByPerformedAtAsc(pageable: Pageable, uuid: UUID): Page<CarCheckUp>
    fun findByCarIdOrderByPerformedAtDesc(pageable: Pageable, uuid: UUID): Page<CarCheckUp>
    fun findAllByCarId(carId: UUID): MutableList<CarCheckUp>
    fun findById(id: Long): CarCheckUp
    fun existsByCar(car: Car): Boolean
    fun existsByCarAndPerformedAtBefore(car:Car, performedAt: LocalDateTime): Boolean

    fun deleteCarCheckUpById(id:Long)

    fun existsById(id:Long): Boolean

    //fun deleteAll()

}

