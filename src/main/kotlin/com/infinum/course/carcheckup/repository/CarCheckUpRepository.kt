package com.infinum.course.carcheckup.repository

import com.infinum.course.car.entity.Car
import com.infinum.course.car.repository.JdbcCarRepository
import com.infinum.course.carcheckup.entity.CarCheckUp
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

interface CarCheckUpRepository {
    fun addCheckUp(worker: String, price: Long, carId: Long): CarCheckUp
    fun getCheckUps(): Map<Long, CarCheckUp>
    fun getCheckUpsById(carId: Long): MutableList<CarCheckUp>
    fun getManufacturerCount(): MutableMap<String, Long>
}

@Repository
class JdbcCarCheckUpRepo (
    private val jdbcTemplate: NamedParameterJdbcTemplate,
    private val carRepository: JdbcCarRepository
        ) : CarCheckUpRepository {

    override fun addCheckUp(worker: String, price: Long, carId: Long): CarCheckUp {
        val check = carRepository.findCarById(carId)    //invokes NoCarException if there is no such car
        val id = (Math.random()*1000).toLong()
        val performedAt = LocalDateTime.now()
        val newCheckUp = CarCheckUp(id, performedAt,worker, price, carId)

        jdbcTemplate.update(
            "insert into carcheckup values (:id, :performedat, :workername, :price, :carid)",
            mapOf("id" to id, "workername" to worker, "price" to price, "carid" to carId, "performedat" to performedAt)
        )
        return newCheckUp
    }

    override fun getCheckUps(): Map<Long, CarCheckUp> {
        println("inside getCheckUps")
        val map = mutableMapOf<Long, CarCheckUp>()
        jdbcTemplate.query(
            "select * from carcheckup")
        {
                resultSet, _ ->
            CarCheckUp(
                id=resultSet.getLong("id"),
                performedAt = resultSet.getTimestamp("performedat").toLocalDateTime(),
                workerName = resultSet.getString("workername"),
                price = resultSet.getLong("price"),
                carId = resultSet.getLong("carid"))
            map.put(key = resultSet.getLong("id"), value = CarCheckUp(
                id=resultSet.getLong("id"),
                performedAt = resultSet.getTimestamp("performedat").toLocalDateTime(),
                workerName = resultSet.getString("workername"),
                price = resultSet.getLong("price"),
                carId = resultSet.getLong("carid")))
            println("inside getCheckUps")
        }
        return map
    }

    override fun getCheckUpsById(carId: Long): MutableList<CarCheckUp> {
        println("inside getCheckUpsById")
        val list = mutableListOf<CarCheckUp>()
        jdbcTemplate.query(
            "select * from carcheckup")
        {
                resultSet, _ ->
            println("inside getCheckUps")
            CarCheckUp(
                id=resultSet.getLong("id"),
                performedAt = resultSet.getTimestamp("performedat").toLocalDateTime(),
                workerName = resultSet.getString("workername"),
                price = resultSet.getLong("price"),
                carId = resultSet.getLong("carid"))
            if(carId == resultSet.getLong("carid")) {
                list.add(
                    CarCheckUp(
                        id = resultSet.getLong("id"),
                        performedAt = resultSet.getTimestamp("performedat").toLocalDateTime(),
                        workerName = resultSet.getString("workername"),
                        price = resultSet.getLong("price"),
                        carId = resultSet.getLong("carid")
                    )
                )
                println("inside getCheckUps")
            }
        }
        return list
    }

    override fun getManufacturerCount(): MutableMap<String, Long>{
        val map = mutableMapOf<String, Long>()
            jdbcTemplate.query(
                "select manufacturer, count(manufacturer) from carcheckup join car on car.id = carcheckup.carid group by manufacturer"
            ) {
                resultSet, _ ->
                println("inside count")
                map[resultSet.getString("manufacturer")] = resultSet.getLong("count")

            }
        return map
    }





        }