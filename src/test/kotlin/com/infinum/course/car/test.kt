package com.infinum.course.car
import com.infinum.course.checkup.entity.Car
import com.infinum.course.car.entity.CarCheckUp
import com.infinum.course.car.repository.CarCheckUpRepository
import com.infinum.course.car.service.CarCheckUpSystemService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.getBean
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
class test {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var carCheckUpSystemService: CarCheckUpSystemService

    @Test
    fun testGetCar(){
        val expectedCar = carCheckUpSystemService.getCarById(123)
        mockMvc.get("/get-car?id=123")
            .andExpect{
                status { is2xxSuccessful() }
                content {(expectedCar)}
            }
    }
}