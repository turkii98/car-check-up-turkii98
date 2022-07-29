package com.infinum.course.car.dto

import java.time.LocalDate
import java.util.UUID

class CarRequestDTO(
    val id: UUID = UUID.randomUUID(),
    val addedDate: LocalDate = LocalDate.now(),
    val modelId: UUID,
    val productionYear : String,
    val vin: String
)
