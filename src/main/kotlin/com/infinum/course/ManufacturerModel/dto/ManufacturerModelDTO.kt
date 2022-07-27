package com.infinum.course.ManufacturerModel.dto

data class ManufacturerModelDTO (
    var manufacturer: String,
    var models: MutableList<String>

    ): java.io.Serializable