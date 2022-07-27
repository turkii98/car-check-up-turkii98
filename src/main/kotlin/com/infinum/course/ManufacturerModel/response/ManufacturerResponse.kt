package com.infinum.course.ManufacturerModel.response

import com.fasterxml.jackson.annotation.JsonProperty

data class ManufacturerResponse (
    @JsonProperty("cars") val cars: MutableList<CarManufacturerModel>
        )

data class CarManufacturerModel(
    @JsonProperty() val manufacturer: String,
    @JsonProperty() val models: MutableList<String>
)