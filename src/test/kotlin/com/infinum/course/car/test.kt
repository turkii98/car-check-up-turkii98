package com.infinum.course.car
import com.infinum.course.checkup.service.CarCheckUpSystemService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

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