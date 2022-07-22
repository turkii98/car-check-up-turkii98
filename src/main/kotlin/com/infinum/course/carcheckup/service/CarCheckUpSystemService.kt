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


/*
    fun getCheckUps(): Map<Long, CarCheckUp> {
        val map = mutableMapOf<Long, CarCheckUp>()
        jdbcTemplate.query(
            "select * from checkup")

        }
        )
    }

*/
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
        val check = getCarById(carId)
        val id = (Math.random()*1000).toLong()
        val performedAt = LocalDateTime.now()
        val newCheckUp = CarCheckUp(id, performedAt,worker, price, carId)
        /*
        for (car in cars) {
            if (car.value.id == carId) {
                car.value.checkUps.add(newCheckUp)
                break
            }
        }
*/
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
        val all = carCheckUpsMap
        println("pukneeeee1")
        val car = cars[id] ?: throw CarNotFoundException(id)
        println("pukneeeee2")
        val returnValue = all.none{ (it.value.carId == id) && (it.value.performedAt.isAfter(LocalDateTime.now().minusYears(1)))}
        println("pukneeeeee3")
        if(returnValue){
            println("TRIBA"+cars[id])
           // cars[id]?.needCheckUp = true
            //carDTO.needCheckUp = true
            return returnValue
        }
        else return false
    }


}