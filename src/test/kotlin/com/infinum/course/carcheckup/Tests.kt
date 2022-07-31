package com.infinum.course.carcheckup
import com.infinum.course.car.entity.Car
import com.infinum.course.car.repository.CarRepository
import com.infinum.course.carcheckup.controller.CarController
import com.infinum.course.carcheckup.entity.CarCheckUp
import com.infinum.course.carcheckup.repository.CarCheckUpRepository
import com.infinum.course.carcheckup.service.CarCheckUpSystemService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import java.util.*

val randUUID = UUID.randomUUID()
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Commit
class getCarByIdTest @Autowired constructor(
    val carRepository: CarRepository,
    val carCheckUpRepository: CarCheckUpRepository,
   // val carCheckUpSystemService: CarCheckUpSystemService
){
    @BeforeEach
    fun setup(){
        carCheckUpRepository.deleteAll()
        carRepository.deleteAll()


        val a3 = Car(id = randUUID, manufacturer = "Audi", model = "a3", productionYear = "2021", vin = "special" )
        val tt = Car(manufacturer = "Audi", model = "tt", productionYear = "2019", vin = "novi" )
        val taycan = Car(manufacturer = "Porsche", model = "taycan", productionYear = "2021", vin = "novi" )
        val a150 = Car(manufacturer = "Mercedes", model = "a150", productionYear = "2021", vin = "novi" )
        val a160 = Car(manufacturer = "Mercedes", model = "a160", productionYear = "2021", vin = "novi" )
        val benz = Car(manufacturer = "Mercedes", model = "benz", productionYear = "2017", vin = "novi" )

        val cars = listOf(a3,tt,taycan,a150,a160,benz)
        carRepository.saveAll(cars)

        val cc = CarCheckUp(workerName = "Marko", carId = randUUID, price = 1986)
        val ccTwo = CarCheckUp(workerName = "Ivan", carId = randUUID, price = 2001)
    }

    @Test
    fun findById() {
        assertThat(carRepository.findById(randUUID).vin).isEqualTo(Car(id = randUUID, manufacturer = "Audi",
            model = "a3", productionYear = "2021", vin = "special" ).vin)
    }

    @Test
    fun paged() {
        val pageable = PageRequest.of(0,2)
        val allCars = carRepository.findAll(pageable)
        assertThat(allCars.totalPages).isEqualTo(3)
    }
/*
    @Test
    fun stats(){
        assertThat(carCheckUpSystemService.countCheckUps()["Porsche"]).isEqualTo(1)
        assertThat(carCheckUpSystemService.countCheckUps()["Audi"]).isEqualTo(2)
        assertThat(carCheckUpSystemService.countCheckUps()["Mercedes"]).isEqualTo(3)
    }
    */

}