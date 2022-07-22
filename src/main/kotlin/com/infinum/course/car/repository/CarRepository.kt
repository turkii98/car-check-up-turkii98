package com.infinum.course.car.repository

import com.infinum.course.car.entity.Car
import com.infinum.course.carcheckup.CarNotFoundException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import java.time.LocalDate

interface CarRepository {
    fun findCarById(id: Long): Car?
    fun addCar(manufacturer: String, model:String, year:String, vin: String) : Car
    fun findCar(): Map<Long, Car>
}

@Repository
class JdbcCarRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate
    ): CarRepository {

    override fun findCar(): MutableMap<Long, Car> {
        val map = mutableMapOf<Long,Car>()
             jdbcTemplate.query(
                "SELECT * FROM Car"
            ) { resultSet, _ ->
                Car(
                    id = resultSet.getLong("id"),
                    addedDate = resultSet.getDate("addeddate").toLocalDate(),
                    manufacturer = resultSet.getString("manufacturer"),
                    model = resultSet.getString("model"),
                    productionYear = resultSet.getString("productionyear"),
                    vin = resultSet.getString("vin")
                )
                map.put(key = resultSet.getLong("id"), value = Car(
                    id = resultSet.getLong("id"),
                    addedDate = resultSet.getDate("addeddate").toLocalDate(),
                    manufacturer = resultSet.getString("manufacturer"),
                    model = resultSet.getString("model"),
                    productionYear = resultSet.getString("productionyear"),
                    vin = resultSet.getString("vin")
                ))
            }

        return map
    }
    override fun findCarById(id: Long): Car? {

        try {
            return jdbcTemplate.queryForObject(
                "SELECT * FROM Car WHERE id = :id",
                mapOf("id" to id)
            ) { resultSet, _ ->
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
        catch (ex: EmptyResultDataAccessException) {
            throw CarNotFoundException(id)
        }
    }

    override fun addCar(manufacturer: String, model:String, year:String, vin: String) : Car {
        val id = (Math.random()*1000).toLong()
        val car = Car(id, LocalDate.now(), manufacturer, model, year, vin)
        val addedDate = LocalDate.now()
        // cars.put(id,car)
        // println(cars)
        jdbcTemplate.update(
            "insert into car values (:id, :addeddate, :manufacturer, :model, :year, :vin)",
            mapOf("id" to id, "addeddate" to addedDate, "manufacturer" to manufacturer, "year" to year, "vin" to vin , "model" to model)
        )
        return car
    }

}