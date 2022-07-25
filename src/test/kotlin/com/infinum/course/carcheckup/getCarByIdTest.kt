package com.infinum.course.carcheckup
import com.infinum.course.carcheckup.service.CarCheckUpSystemService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.annotation.Commit
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Commit
class getCarByIdTest {

    @Autowired
    lateinit var jdbcTemplate: NamedParameterJdbcTemplate

    @BeforeEach
    fun setUp() {

        jdbcTemplate.update(
            "INSERT INTO car VALUES (:id, '2001-07-21', 'Mercedes', 'a150', 1998, 'merco' )",
            mapOf("id" to 231214)
        )
    }

@Test
fun testGetCarById(){
    Assertions.assertThat(
        jdbcTemplate.queryForObject(
            "SELECT vin FROM car WHERE id = :id",
            mapOf("id" to 231214),
            String::class.java
        )
    ).isEqualTo("merco")
}
    @Test
    fun testCount() {
        Assertions.assertThat(
            jdbcTemplate.queryForObject(
                "SELECT count(*) FROM car WHERE id = :id",
                mapOf("id" to 231214),
                Int::class.java
            )
        ).isEqualTo(1)
    }

}