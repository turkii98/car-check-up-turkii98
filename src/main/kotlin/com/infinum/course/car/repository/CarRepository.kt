package com.infinum.course.car.repository

import com.infinum.course.car.entity.Car
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

interface CarRepository {
    fun findCarById(id: Long): Car
    //fun getAllCars(): Map<Long, Car>
}

@Repository
class JdbcCarRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate
    ): CarRepository {

    override fun findCarById(id: Long): Car {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM Car WHERE id = :id",
            mapOf("id" to id),
            Car::class.java
        )
    }

    /*
    override fun getAllCars(): Map<Long, Car> {
        val cars = mutableMapOf<Long,Car>()
        return jdbcTemplate.queryForObject(
            "SELECT * FROM Car"
        ) {
                resultSet,_ ->
            Car(
                id = resultSet.getLong("id"),
                addedDate = resultSet.getDate("addeddate").toLocalDate(),
                manufacturer = resultSet.getString("manufacturer"),
                model = resultSet.getString("model"),
                productionYear = resultSet.getString("productionyear"),
                vin = resultSet.getString("vin")
            )
        }
    }


    }

     */
}