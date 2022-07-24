package com.infinum.course.car.checkup

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CarCheckUpSystem (@Qualifier("inFileCarCheckUpRepository") val repo: CarCheckUpRepository){
    //val checkUpList: MutableList<CarCheckUp>
    //val vinMap: Map<String, Car>


    fun printList(){
        val all = repo.findAll()
        for (i in all) println(i)
    }


    fun removeCheckUp(id : Long){
        repo.deleteById(id)
    }

    fun addCheckUp(car: Car){
        repo.insert(LocalDateTime.now(),car)
    }

    fun getCarById(id: Long): CarCheckUp{
        return repo.findById(id)
    }

    fun countCheckUps(manufacturer: String): Int {
        val all = repo.findAll()
        val count = all.count() { it.value.car.manufacturer ==manufacturer }
        return count
    }


    fun isCheckUpNecessary(vin: String): Boolean {
        val all = repo.findAll()
        return all.none{(vin == it.value.car.vin) && (it.value.performedAt.isAfter(LocalDateTime.now().minusYears(1)))}

    }


}