package com.infinum.course.car.checkup

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class DataSource(
   @Value("\${db-name}") val dbName: String,
   @Value("\${username}") val username: String,
   @Value("\${password}") val password: String
)
