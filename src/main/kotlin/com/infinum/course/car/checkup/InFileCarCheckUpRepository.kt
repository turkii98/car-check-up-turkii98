package com.infinum.course.car.checkup

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component
import java.io.FileOutputStream
import java.time.LocalDateTime

@Component
@Qualifier("inFile")
class InFileCarCheckUpRepository(
    private val carCheckUpsFileResource: Resource // will be provided through dependency injection
) : CarCheckUpRepository {
    init {
        if (carCheckUpsFileResource.exists().not()) {
            carCheckUpsFileResource.file.createNewFile()
        }
    }
    override fun insert(performedAt: LocalDateTime, car: Car): Long {
        val file = carCheckUpsFileResource.file
        val id = (file.readLines()
            .filter { it.isNotEmpty() }.maxOfOrNull { line ->
                line.split(",").first().toLong() } ?: 0) + 1
        file.appendText("$id,${car.vin},${car.manufacturer},${car.model},$performedAt\n")
        return id
    }
    override fun findById(id: Long): CarCheckUp {
        return carCheckUpsFileResource.file.readLines()
            .filter { it.isNotEmpty() }
            .find { line -> line.split(",").first().toLong() == id }
            ?.convertToCarCheckUp()
            ?: throw CarCheckUpNotFoundException(id)
    }
    override fun deleteById(id: Long): CarCheckUp {
        val checkUpLines = carCheckUpsFileResource.file.readLines()
        var lineToDelete: String? = null
        FileOutputStream(carCheckUpsFileResource.file)
            .writer()
            .use { fileOutputWriter ->
                checkUpLines.forEach { line ->
                    if (line.split(",").first().toLong() == id) {
                        lineToDelete = line
                    } else {
                        fileOutputWriter.appendLine(line)
                    }
                }
            }
        return lineToDelete?.convertToCarCheckUp() ?: throw
        CarCheckUpNotFoundException(id)
    }
    override fun findAll(): Map<Long, CarCheckUp> {
        return carCheckUpsFileResource.file.readLines()
            .map { line -> line.convertToCarCheckUp() }
            .associateBy { it.id }
    }
    private fun String.convertToCarCheckUp(): CarCheckUp {
        val tokens = split(",")
        return CarCheckUp(
            id = tokens[0].toLong(),
            performedAt = LocalDateTime.parse(tokens[4]),
            car = Car(
                vin = tokens[1],
                manufacturer = tokens[2],
                model = tokens[3]
            )
        )
    }
}
