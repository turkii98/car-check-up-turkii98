package com.infinum.course.car.checkup

import java.time.Duration
import java.time.LocalDateTime

object CarCheckUpSystem {
    var checkUpList: MutableList<CarCheckUp>
    val vinMap: Map<String, Car>

    init {
        val firstCar = Car("Porsche", "Taycan", "PRVI")
        val secondCar = Car("Mercedes", "A160", "DRUGI")
        val thirdCar = Car("Audi", "A3", "TRECI")
        val fourthCar = Car("Audi", "TT", "CET")
        vinMap = mapOf(Pair("PRVI", firstCar), Pair("DRUGI", secondCar), Pair("TRECI", thirdCar), Pair("CET", fourthCar))
        checkUpList = mutableListOf(
            CarCheckUp(LocalDateTime.of(2022, 7, 5, 17, 30), firstCar),
            CarCheckUp(LocalDateTime.of(2021, 7, 5, 17, 30), secondCar),
            CarCheckUp(LocalDateTime.of(2021, 7, 5, 17, 30), thirdCar),
            CarCheckUp(LocalDateTime.of(2019, 7, 5, 17, 30), fourthCar),
            CarCheckUp(LocalDateTime.of(2022, 7, 5, 17, 30), firstCar),
            CarCheckUp(LocalDateTime.of(2022, 7, 5, 17, 30), thirdCar),
            CarCheckUp(LocalDateTime.of(2022, 7, 5, 17, 30), secondCar),
            CarCheckUp(LocalDateTime.of(2022, 7, 5, 17, 30), firstCar),
            CarCheckUp(LocalDateTime.of(2020, 7, 5, 17, 30), fourthCar),
            CarCheckUp(LocalDateTime.of(2021, 7, 8, 22, 12), firstCar),
        )
    }

    fun printList(){
        for (i in checkUpList) {
            println(i.car.vin)
        }
    }


    fun isCheckUpNecessary(vin: String): Boolean = !checkUpList.none {(it.car.vin == vin) &&
            (Duration.between(it.performedAt, LocalDateTime.now()).toDays() >= 365)
             }


    fun addCheckUp(vin: String){
        val newCheckUp = CarCheckUp(LocalDateTime.now(), vinMap[vin] ?: throw Exception())
        checkUpList.add(newCheckUp)
        }


    fun getCheckUps(vin: String): List<CarCheckUp> {
        if(vinMap[vin] == null){
            throw Exception()
        }

        return checkUpList.filter { it.car.vin == vin }

    }

    fun countCheckUps(manufacturer: String): Int = checkUpList.count { it.car.manufacturer == manufacturer}


}