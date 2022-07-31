package com.infinum.course.ManufacturerModel.controller

import com.infinum.course.ManufacturerModel.dto.ManufacturerModelDTO
import com.infinum.course.ManufacturerModel.dto.ManufacturerModelResource
import com.infinum.course.ManufacturerModel.dto.ManufacturerModelResourceAssembler
import com.infinum.course.ManufacturerModel.entity.ManufacturerModel
import com.infinum.course.ManufacturerModel.repository.ManufacturerModelRepository
import com.infinum.course.ManufacturerModel.service.RestTemplateManufacturerAndModelService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.MediaType.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import java.util.UUID


@RestController
@RequestMapping("manumodel", produces = [APPLICATION_JSON_VALUE])
class ManufacturerModelController(
    private val restTemplateManufacturerAndModelService: RestTemplateManufacturerAndModelService,
    private val carManufacturerModelRepository: ManufacturerModelRepository,
    private val manufacturerModelResourceAssembler: ManufacturerModelResourceAssembler
) {

    @GetMapping
    fun getManufacturerAndModel(): ResponseEntity<List<ManufacturerModelDTO>> {
        val newManuAndModel = restTemplateManufacturerAndModelService.getModelAndManufacturer()
        restTemplateManufacturerAndModelService.updateManufacturerModel(newManuAndModel)
        return ResponseEntity.ok(newManuAndModel)
    }

    @GetMapping("/{id}")
    fun getManuAndModel(@PathVariable id: UUID): ResponseEntity<ManufacturerModelResource> {
        return ResponseEntity.ok(manufacturerModelResourceAssembler.toModel(carManufacturerModelRepository.findById(id)))
    }
}