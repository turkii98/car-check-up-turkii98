package com.infinum.course.ManufacturerModel.dto

import com.infinum.course.ManufacturerModel.controller.ManufacturerModelController
import com.infinum.course.ManufacturerModel.entity.ManufacturerModel
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
                linkTo<ManufacturerModelController> {
                    getManufacturerAndModel()
                }.withRel("other_manufacturer_models")
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