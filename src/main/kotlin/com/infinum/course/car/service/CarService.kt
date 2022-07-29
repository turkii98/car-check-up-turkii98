package com.infinum.course.car.service

import com.infinum.course.ManufacturerModel.repository.ManufacturerModelRepository
import com.infinum.course.car.dto.CarDTO
import com.infinum.course.car.dto.CarRequestDTO
import com.infinum.course.car.entity.Car
import com.infinum.course.car.repository.CarRepository
import com.infinum.course.carcheckup.repository.CarCheckUpRepository
import com.infinum.course.carcheckup.service.CarCheckUpSystemService
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CachePut
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import java.util.*
import org.springframework.cache.annotation.Cacheable

@Service
class CarService(
    private val carRepository: CarRepository,
    private val carCheckUpRepository: CarCheckUpRepository,
    private val carCheckUpSystemService: CarCheckUpSystemService,
    private val manufacturerModelRepository: ManufacturerModelRepository){

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun getCarDTO(id: UUID): CarDTO? {
        println(id)
        val newCar = carRepository.findById(id)
        val newCarDTO = CarDTO(newCar)
        val list = carCheckUpRepository.findAllByCarId(id)
        newCarDTO.checkUps = list
        val checkNeccessary = carCheckUpSystemService.isCheckUpNecessary(id)
        newCarDTO.needCheckUp = checkNeccessary
        println(newCarDTO)
        logger.info("Caching car")
        return newCarDTO
    }

    fun addCar(carRequest: CarRequestDTO): Car? {
        val newCarr = Car(manufacturerModel = manufacturerModelRepository.findById(carRequest.modelId),
            productionYear = carRequest.productionYear,
            vin = carRequest.vin,
            id = carRequest.id,
            addedDate = carRequest.addedDate)
        val newCar = carRepository.save(newCarr)
        //val newCarDTO = CarDTO(newCar)
        logger.info("Caching car")
        //println(newCarDTO)
        return newCar
    }

}