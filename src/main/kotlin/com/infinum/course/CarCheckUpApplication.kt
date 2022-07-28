package com.infinum.course

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class CarCheckUpApplication {}
    fun main() {
        runApplication<CarCheckUpApplication>()

    }
