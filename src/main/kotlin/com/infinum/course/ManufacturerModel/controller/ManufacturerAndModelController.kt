package com.infinum.course.ManufacturerModel.controller

import com.infinum.course.ManufacturerModel.dto.ManufacturerModelDTO
import com.infinum.course.ManufacturerModel.service.RestTemplateManufacturerAndModelService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.MediaType.*
import org.springframework.http.ResponseEntity


@RestController
@RequestMapping("/api/v1", produces = [APPLICATION_JSON_VALUE])
class ManufacturerAndModelController(
    private val restTemplateManufacturerAndModelService: RestTemplateManufacturerAndModelService
) {

    @GetMapping("/cars")
    fun getManufacturerAndModel(): ResponseEntity<List<ManufacturerModelDTO>> {
        val newManuAndModel = restTemplateManufacturerAndModelService.getModelAndManufacturer()
        restTemplateManufacturerAndModelService.updateManufacturerModel(newManuAndModel)
        return ResponseEntity.ok(newManuAndModel)
    }
}