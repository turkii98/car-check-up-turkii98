package com.infinum.course.ManufacturerModel.entity

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="manufacturermodel")
data class ManufacturerModel(
    var manufacturer: String,
    var model: String,

    @Id
    var id: UUID = UUID.randomUUID()
)
