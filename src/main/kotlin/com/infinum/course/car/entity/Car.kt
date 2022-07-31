package com.infinum.course.car.entity

import com.infinum.course.ManufacturerModel.entity.ManufacturerModel
import com.infinum.course.carcheckup.entity.CarCheckUp
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "car")
class Car (

    @Id
    val id: UUID = UUID.randomUUID(),

    val addedDate: LocalDate = LocalDate.now(),

    @ManyToOne
    @JoinColumn(name = "model_id")
    val manufacturerModel: ManufacturerModel,

    val productionYear: String,

    val vin: String,
    )