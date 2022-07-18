package com.infinum.course.car.service

import com.infinum.course.checkup.CarCheckUpNotFoundException
import com.infinum.course.car.CarNotFoundException
import com.infinum.course.checkup.entity.Car
import com.infinum.course.car.entity.CarCheckUp
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class CarCheckUpSystemService (var cars : MutableMap<Long, Car>, var carCheckUpsMap: MutableMap<Long, CarCheckUp>, var ctr: Long = 0){
    //val checkUpList: MutableList<CarCheckUp>

    init {
        carCheckUpsMap[321] = CarCheckUp(1, LocalDateTime.now(), "Ivan", 20000, 321 )
        carCheckUpsMap[123] = CarCheckUp(2, LocalDateTime.of(2019, 6,13,17,30), "Marko", 37000, 123)

        ctr = 3
        cars[1] = Car(123, LocalDate.of(2019, 6,13) , "Audi", "TT", "2022", "PRVI", mutableListOf(carCheckUpsMap[123] ?: throw CarNotFoundException(123)))
        cars[2] = Car(321, LocalDate.now(), "Ford", "Mustang", "2019", "DRUGI", mutableListOf(carCheckUpsMap[321] ?: throw CarNotFoundException(321)))

    }



    fun getCheckUps(): Map<Long, CarCheckUp> {
        return carCheckUpsMap
    }


    fun addCar(manufacturer: String, model:String, year:String, vin: String) : Car {
        val checkUpList: MutableList<CarCheckUp> = mutableListOf()
        ctr+=1
        val id = (Math.random()*1000).toLong()
        val car = Car(id, LocalDate.now(), manufacturer, model, year, vin, checkUpList)
        cars.put(id,car)
        println(cars)
        return car
    }


    fun removeCheckUp(id : Long){
        carCheckUpsMap.remove(id)
    }


    fun addCheckUp(worker: String, price: Long, carId: Long): CarCheckUp {
        if(cars.none(){carId == it.value.id}){
            throw CarNotFoundException(carId)
        }
        ctr += 1
        val newCheckUp = CarCheckUp((Math.random()*1000).toLong(), LocalDateTime.now(),worker, price, carId)
        carCheckUpsMap.put(ctr, newCheckUp)

        for (car in cars) {
            if (car.value.id == carId) {
                car.value.checkUps.add(newCheckUp)
                break
            }
        }

        println(cars)
        println("-------------------")
        println(carCheckUpsMap)
        return newCheckUp
    }

    fun getCarCheckUpByCarId(id: Long): MutableList<CarCheckUp> {
        val newList = mutableListOf<CarCheckUp> ()
        (carCheckUpsMap[id] ?: throw CarCheckUpNotFoundException(id))
        return newList

    }

    fun getCarById(id: Long): Car {
        val newCars = cars.filter() { it.value.id == id }
        val newCar = newCars.values
        println("NEW CARS"+newCar)
        return (newCar.first() ?: throw CarCheckUpNotFoundException(id))
    }

    fun countCheckUps(manufacturer: String): Int {
        val all = carCheckUpsMap
        val count = all.count() { cars[it.value.carId]?.manufacturer == manufacturer }
        return count
    }


    fun isCheckUpNecessary(id: Long): Boolean {
        val all = carCheckUpsMap
        val returnValue = all.none{ (it.value.carId == id) && (it.value.performedAt.isAfter(LocalDateTime.now().minusYears(1)))}
        if(returnValue){
            println("TRIBA"+cars[id])
            cars[id]?.needCheckUp = true
            return returnValue
        }
        else return false
    }


}