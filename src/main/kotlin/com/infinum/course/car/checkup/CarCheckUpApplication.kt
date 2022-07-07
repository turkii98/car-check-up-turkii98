package com.infinum.course.car.checkup

import java.time.Duration
import java.time.LocalDateTime

class Car (manufacturer: String, model: String, vin: String){
    private val manufacturer: String
    private val model: String
    private val vin: String

    init {
        this.manufacturer = manufacturer
        this.model = model
        this.vin = vin
    }

    fun getVin(): String{
        return (this.vin)
    }

    fun getModel(): String{
        return (this.model)
    }

    fun getManufacturer(): String{
        return (this.manufacturer)
    }
}

class CarCheckUp (performedAt: LocalDateTime, car: Car){
    private val performedAt: LocalDateTime
    private val car: Car

    init {
        this.performedAt = performedAt
        this.car = car
    }

    fun getPerformedAt(): LocalDateTime{
        return this.performedAt
    }

    fun getCar(): Car{
        return this.car
    }
}

object CarCheckUpSystem {
    private val firstCar: Car
    private val secondCar: Car
    private val thirdCar: Car
    private val fourthCar: Car
    private var checkUpList: MutableList<CarCheckUp>
    private val vinMap: Map<String, Car>

    init {
        firstCar = Car("Porsche", "Taycan", "PRVI")
        secondCar = Car("Mercedes", "A160", "DRUGI")
        thirdCar = Car("Audi", "A3", "TRECI")
        fourthCar = Car("Audi", "TT", "CET")
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
            CarCheckUp(LocalDateTime.of(2021, 7, 5, 20, 12), firstCar),
        )
    }

    fun printList(){
        for (i in checkUpList) {
            println(i.getCar().getVin())
        }
    }
    fun isCheckUpNecessary(vin: String): Boolean{
        for (i in checkUpList.indices.reversed()){
            if (checkUpList[i].getCar().getVin() == vin){
                if(Duration.between(checkUpList[i].getPerformedAt(), LocalDateTime.now()).toDays() >= 365)
                    return true
            }
        }
        return false
    }

    fun addCheckUp(vin: String){
        if(vinMap[vin] == null){
            throw Exception()
        }
        else if(vinMap[vin] != null){
            val newCheckUp = CarCheckUp(LocalDateTime.now(), vinMap[vin]!!)
            checkUpList.add(newCheckUp)
        }
    }

    fun getCheckUps(vin: String): List<CarCheckUp> {
        if(vinMap[vin] == null){
            throw Exception()
        }
        val newList = mutableListOf<CarCheckUp>()
        for (i in checkUpList){
            if (i.getCar().getVin() == vin) newList.add(i)
        }
        return newList
    }

    fun countCheckUps(manufacturer: String): Int{
        var count = 0
        count += checkUpList.count { it.getCar().getManufacturer() == manufacturer}
        return count
    }
}
fun main() {
    // Entry point
    //val porsche = Car("Manu", "Taycan", "adsadas1321")
    if(CarCheckUpSystem.isCheckUpNecessary("PRVI")) println("TREBA")
    CarCheckUpSystem.addCheckUp("PRVI")
    CarCheckUpSystem.printList()
    println("car checkups: "+(CarCheckUpSystem.getCheckUps("DRUGI").last().getCar().getModel()))
    val manufacturer = "Volkswagen"
    val cnt = CarCheckUpSystem.countCheckUps(manufacturer)
    println("there are $cnt cars by $manufacturer")
}