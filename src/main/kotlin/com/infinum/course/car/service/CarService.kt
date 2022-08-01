package com.infinum.course.car.service

import com.infinum.course.ManufacturerModel.ManufacturerModelNotFoundException
import com.infinum.course.ManufacturerModel.repository.ManufacturerModelRepository
import com.infinum.course.car.dto.CarDTO
import com.infinum.course.car.dto.CarRequestDTO
import com.infinum.course.car.dto.CarResource
import com.infinum.course.car.dto.CarResourceAssembler
import com.infinum.course.car.entity.Car
import com.infinum.course.car.repository.CarRepository
import com.infinum.course.carcheckup.repository.CarCheckUpRepository
import com.infinum.course.carcheckup.service.CarCheckUpSystemService
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.PagedModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class CarService(
    private val carRepository: CarRepository,
    private val carCheckUpRepository: CarCheckUpRepository,
    private val carCheckUpSystemService: CarCheckUpSystemService,
    private val manufacturerModelRepository: ManufacturerModelRepository,
    private val carResourceAssembler: CarResourceAssembler
    ){

    private val logger = LoggerFactory.getLogger(this::class.java)
    private val noPageable = Pageable.unpaged()

    fun checkUpNeccessary(id: UUID): ResponseEntity<CarResource> {
        val response = ResponseEntity(carResourceAssembler.toModel(getCar(id)), HttpStatus.OK)
        response.body?.needCheckUp = carCheckUpSystemService.findCheckUpByCarSorted(noPageable, id, "desc")
            .get()
            .findFirst()
            .get().performedAt
            .isBefore(LocalDateTime.now().minusYears(1))
        return response
    }


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

    fun addCar(carRequest: CarRequestDTO): Car {
        val manuModel = manufacturerModelRepository.findById(carRequest.modelId)
        if(!manufacturerModelRepository.existsByManufacturerAndModel(manuModel.manufacturer,manuModel.model )) throw ManufacturerModelNotFoundException()
        val newCarr = Car(manufacturerModel = manuModel,
            productionYear = carRequest.productionYear,
            vin = carRequest.vin,
            id = carRequest.id,
            addedDate = carRequest.addedDate)
        val newCar = carRepository.save(newCarr)
        logger.info("Caching car")
        return newCar
    }

    fun getCar(id: UUID): Car {
        return carRepository.findById(id)
    }

    fun getAllCars(pageable: Pageable, pagedResourcesAssembler: PagedResourcesAssembler<Car>): ResponseEntity<PagedModel<CarResource>> {
        var responseUpdatedNeedCheckUp = ResponseEntity.ok(pagedResourcesAssembler.toModel(carRepository.findAll(pageable), carResourceAssembler))
        responseUpdatedNeedCheckUp.body.content.forEach {
            try {
                it.needCheckUp = checkUpNeccessary(it.id)?.body?.needCheckUp ?: true //throws NoSuchElementException when there are no checkups for a car
            }
            catch (exc: NoSuchElementException) {
                it.needCheckUp = true
            }
        }
        return responseUpdatedNeedCheckUp
    }





}