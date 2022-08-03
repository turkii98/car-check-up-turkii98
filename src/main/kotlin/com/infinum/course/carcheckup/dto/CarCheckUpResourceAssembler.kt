package com.infinum.course.carcheckup.dto

import com.infinum.course.carcheckup.controller.CarCheckUpController
import com.infinum.course.carcheckup.controller.CarController
import com.infinum.course.carcheckup.entity.CarCheckUp
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class CarCheckUpResourceAssembler: RepresentationModelAssemblerSupport<CarCheckUp, CarCheckUpResource> (
    CarCheckUpController::class.java, CarCheckUpResource::class.java
        ) {
    override fun toModel(entity: CarCheckUp): CarCheckUpResource {
        return createModelWithId(entity.id, entity).apply {
            add(
                linkTo<CarController> {
                    getCar(entity.car.id)
                }.withRel("car")
            )
        }
    }

    override fun instantiateModel(entity: CarCheckUp): CarCheckUpResource {
        return CarCheckUpResource(
            id = entity.id,
            performedAt = entity.performedAt,
            workerName = entity.workerName,
            price = entity.price,
            carId = entity.car.id
        )
    }
}


data class CarCheckUpResource(
    val id: Long ,
    val performedAt: LocalDateTime ,
    val workerName: String,
    val price: Long,
    val carId: UUID
): RepresentationModel<CarCheckUpResource>()