package com.infinum.course.car.checkup

import org.springframework.beans.factory.getBean
import org.springframework.context.annotation.AnnotationConfigApplicationContext

fun main() {
    // Entry point
    val applicationContext = AnnotationConfigApplicationContext(ApplicationConfig::class.java)
    val service = applicationContext.getBean<CarCheckUpSystem>()
    //service.removeCheckUp(4)
    //service.printList()
    val auto = Car("Audi","TT","adaw")
    service.addCheckUp(auto)
    service.printList()
    println("get car by id: "+service.getCarById(1))
    println("is checkup neccesary?" + service.isCheckUpNecessary("adaw"))
    println("number of checkups "+ service.countCheckUps("Audi"))

}