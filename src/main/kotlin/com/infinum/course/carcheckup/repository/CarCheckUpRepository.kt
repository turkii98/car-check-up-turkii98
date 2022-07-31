package com.infinum.course.carcheckup.repository


import com.infinum.course.carcheckup.dto.JpaQueryCountBean
import com.infinum.course.carcheckup.entity.CarCheckUp
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface CarCheckUpRepository : JpaRepository<CarCheckUp, Long>{

    @Query(value= "SELECT new com.infinum.course.carcheckup.dto.JpaQueryCountBean(mm.manufacturer, count(mm)) " +
            "from CarCheckUp cc join Car c on c.id = cc.car.id " +
            "join ManufacturerModel mm on mm.id = c.manufacturerModel.id " +
            "group by mm.manufacturer")
    fun findCarCheckUpCount(): List<JpaQueryCountBean>


    fun save(carcheckup: CarCheckUp): CarCheckUp
    fun findByCarId(pageable: Pageable, uuid: UUID): Page<CarCheckUp>
    fun findAllByCarId(carId: UUID): MutableList<CarCheckUp>

    //fun deleteAll()

}

