package com.infinum.course.car.dto

import com.infinum.course.ManufacturerModel.controller.ManufacturerModelController
import com.infinum.course.car.entity.Car
import com.infinum.course.carcheckup.controller.CarCheckUpController
import com.infinum.course.carcheckup.controller.CarController
import com.infinum.course.carcheckup.entity.CarCheckUp
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.*

@Component
class CarResourceAssembler: RepresentationModelAssemblerSupport<Car, CarResource>(
    CarController::class.java,
    CarResource::class.java
) {
    private val noPagination = Pageable.unpaged()
    private val nullAssembler = PagedResourcesAssembler<CarCheckUp>(null, null)

    override fun toModel(entity: Car): CarResource {
        return createModelWithId(entity.id, entity).apply {
            add(
                linkTo<CarCheckUpController> {
                    findByCarId(noPagination, entity.id, nullAssembler, "yo")
                }.withRel("checkups"),
                linkTo<ManufacturerModelController> {
                    getManuAndModel(entity.manufacturerModel.id)
                }.withRel("manufacturerAndModel")
            )
        }
    }

    override fun instantiateModel(entity: Car): CarResource {
        return CarResource(
            id = entity.id,
            addedDate = entity.addedDate,
            modelId = entity.manufacturerModel.id,
            productionYear = entity.productionYear,
            vin = entity.vin,
            needCheckUp = true
        )
    }
}

data class CarResource(
    val id: UUID,
    val addedDate: LocalDate,
    val modelId: UUID,
    val productionYear : String,
    val vin: String,
    var needCheckUp : Boolean
): RepresentationModel<CarResource>()