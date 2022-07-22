package com.infinum.course.carcheckup.service

import com.infinum.course.car.CarCheckUpNotFoundException
import com.infinum.course.car.CarDTO
import com.infinum.course.carcheckup.CarNotFoundException
import com.infinum.course.car.entity.Car
import com.infinum.course.carcheckup.entity.CarCheckUp
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import java.sql.ResultSet
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class CarCheckUpSystemService (var cars : MutableMap<Long, Car>, var carCheckUpsMap: MutableMap<Long, CarCheckUp>, var ctr: Long = 0,val jdbcTemplate: NamedParameterJdbcTemplate){
    //val checkUpList: MutableList<CarCheckUp>





    fun addCar(manufacturer: String, model:String, year:String, vin: String) : Car {
        ctr+=1
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


    //fun removeCheckUp(id : Long){
      //  carCheckUpsMap.remove(id)
    //}


    fun addCheckUp(worker: String, price: Long, carId: Long): CarCheckUp {
        val check = getCarById(carId)    //invokes NoCarException if there is no such car
        val id = (Math.random()*1000).toLong()
        val performedAt = LocalDateTime.now()
        val newCheckUp = CarCheckUp(id, performedAt,worker, price, carId)

        jdbcTemplate.update(
            "insert into carcheckup values (:id, :performedat, :workername, :price, :carid)",
            mapOf("id" to id, "workername" to worker, "price" to price, "carid" to carId, "performedat" to performedAt)
        )
        return newCheckUp
    }

    fun getCarCheckUpByCarId(id: Long): MutableList<CarCheckUp> {
        val newList = mutableListOf<CarCheckUp> ()
        (carCheckUpsMap[id] ?: throw CarCheckUpNotFoundException(id))
        return newList

    }

    fun getCheckUps(): Map<Long, CarCheckUp> {
        println("inside getCheckUps")
        val map = mutableMapOf<Long, CarCheckUp>()
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

    fun getCheckUpsById(carId: Long): MutableList<CarCheckUp> {
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


    fun getCarById(id: Long): Car {

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
        catch (ex:EmptyResultDataAccessException) {
            throw CarNotFoundException(id)
        }
    }

    fun countCheckUps(manufacturer: String): Int {
        val all = carCheckUpsMap
        val count = all.count() { cars[it.value.carId]?.manufacturer == manufacturer }
        return count
    }


    fun isCheckUpNecessary(id: Long): Boolean {
        val list = getCheckUpsById(id)
        println("pukneeeee1")

        val returnValue = list.none{ (it.performedAt.isAfter(LocalDateTime.now().minusYears(1)))}
        println("pukneeeeee3")
        if(returnValue){
            println("TRIBA"+cars[id])
            return returnValue
        }
        else return false
    }


}