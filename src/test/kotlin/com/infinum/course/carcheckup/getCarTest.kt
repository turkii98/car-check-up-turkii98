package com.infinum.course.carcheckup

import com.infinum.course.carcheckup.service.CarCheckUpSystemService
import com.infinum.course.car.entity.Car
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.LocalDate

@WebMvcTest
class getCarTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var carCheckUpSystemService: CarCheckUpSystemService

    private val cars = mutableMapOf<Long, Car>(
        123.toLong() to Car(123, LocalDate.of(2019, 6,13) , "Audi", "TT", "2022", "PRVI"),
        1.toLong() to Car(1, LocalDate.of(2022, 6,13) , "Ford", "Mustang", "2019", "DRUGI")
    )

    @BeforeEach
    fun setUp(){
            every { carCheckUpSystemService.getCarById(123) } answers {Car(123, LocalDate.of(2019, 6,13) , "Audi", "TT", "2022", "PRVI")}
    }
    @Test
    fun testGetCar(){
        val expectedCar = cars[123].toString()
        println("---------------------"+expectedCar)
        mockMvc.get("/get-car?id=123")
            .andExpect{
                status { is2xxSuccessful() }
                content { (expectedCar) }
            }
    }
}