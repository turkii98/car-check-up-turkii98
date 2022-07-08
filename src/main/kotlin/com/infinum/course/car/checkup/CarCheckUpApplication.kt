package com.infinum.course.car.checkup



fun main() {
    // Entry point
    //val porsche = Car("Manu", "Taycan", "adsadas1321")
    if(CarCheckUpSystem.isCheckUpNecessary("PRVI")) println("TREBA1")
    CarCheckUpSystem.addCheckUp("TRECI")
    CarCheckUpSystem.printList()
    println("car checkups: "+(CarCheckUpSystem.getCheckUps("DRUGI")))
    val manufacturer = "Mercedes"
    val cnt = CarCheckUpSystem.countCheckUps(manufacturer)
    println("there are $cnt cars by $manufacturer")
}