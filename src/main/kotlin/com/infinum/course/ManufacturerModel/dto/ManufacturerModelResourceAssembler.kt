package com.infinum.course.ManufacturerModel.dto

import com.infinum.course.ManufacturerModel.controller.ManufacturerModelController
import com.infinum.course.ManufacturerModel.entity.ManufacturerModel
import com.infinum.course.car.entity.Car
import com.infinum.course.carcheckup.controller.CarController
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.stereotype.Component

@Component
class ManufacturerModelResourceAssembler: RepresentationModelAssemblerSupport<ManufacturerModel, ManufacturerModelResource> (
    ManufacturerModelController::class.java, ManufacturerModelResource::class.java
        ) {
    override fun toModel(entity: ManufacturerModel): ManufacturerModelResource {
        return createModelWithId(entity.id, entity).apply {
            add(
                linkTo<CarController> {
                    getCarsOfModel(entity.id, pageable = Pageable.unpaged(), pagedResourcesAssembler = PagedResourcesAssembler<Car>(null, null))
                }.withRel("carsOfThisModel")
            )
        }
    }

    override fun instantiateModel(entity: ManufacturerModel): ManufacturerModelResource {
        return ManufacturerModelResource(
            manufacturer = entity.manufacturer,
            model = entity.model
        )
    }
}


data class ManufacturerModelResource (
    var manufacturer: String,
    var model: String
 ):RepresentationModel<ManufacturerModelResource>()