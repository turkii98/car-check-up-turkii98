package com.infinum.course.car.checkup
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.getBean
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import java.time.LocalDateTime

@SpringJUnitConfig(ApplicationConfig::class)
class test @Autowired constructor(
    private val applicationContext: ApplicationContext,
    private val carCheckUpSystem: CarCheckUpSystem
){
    @Test
    fun test1(){
        assertThat(applicationContext.getBean<CarCheckUpSystem>()).isNotNull
    }

    @Test
    fun test2(){
        assertThat(applicationContext.getBean<CarCheckUpSystem>()).isNotNull
    }

    @Test
    fun testCheckUp(){
        val auto = Car("Audi","TT", "adaw")
        val chUp = CarCheckUp(1, LocalDateTime.of(2022,7,14,17,1,22,535798800),auto)
        println(chUp)
        println(carCheckUpSystem.getCarById(1))
        assertThat(carCheckUpSystem.getCarById(1)).isEqualTo(chUp)
    }
}

class unitTest {
    lateinit var carCheckUpSystem : CarCheckUpSystem
    private val carCheckUpRepository = mockk<CarCheckUpRepository>()

    @BeforeEach
    fun setUp(){
        carCheckUpSystem = CarCheckUpSystem(carCheckUpRepository)
    }
    @Test
    @DisplayName("should return correct name")
    fun unitTest(){
        every { carCheckUpRepository.findById(any())} returns CarCheckUp(id = 1, performedAt = LocalDateTime.of(2022,7,14,17,1,22,535798800),
        car = Car("Audi", "TT", "afaw"))

        val expectChUp = carCheckUpSystem.getCarById(1)
        assertThat(expectChUp).isEqualTo(CarCheckUp(1, LocalDateTime.of(2022,7,14,17,1,22,535798800),
            Car("Audi", "TT", "afaw")))
        verify(exactly = 0) {carCheckUpRepository.findAll()  }

    }
}