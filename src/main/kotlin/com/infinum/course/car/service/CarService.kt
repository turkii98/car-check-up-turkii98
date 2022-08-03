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
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.hateoas.PagedModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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
    fun checkUpNeccessary(id: UUID): ResponseEntity<CarResource> {
        val response = ResponseEntity(carResourceAssembler.toModel(getCar(id)), HttpStatus.OK)
        response.body?.needCheckUp = carCheckUpRepository
            .existsByCarAndPerformedAtBefore(carRepository.findById(response.body.id), LocalDateTime.now().minusYears(1))
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

    @Transactional
    fun deleteCar(id: UUID): HttpStatus {
        if(carRepository.existsById(id)){
            carCheckUpRepository.deleteByCar(carRepository.findById(id))
            carRepository.deleteCarById(id)
            return HttpStatus.NO_CONTENT
        }
        else
            return HttpStatus.BAD_REQUEST

    }

    fun getAllCars(pageable: Pageable): Page<Car> {
        return carRepository.findAll(pageable)
    }

    fun getCarsOfModel(pageable: Pageable, modelId: UUID): Page<Car> {
        return carRepository.findByManufacturerModel(pageable,manufacturerModelRepository.findById(modelId))
    }

    fun allCarsValidated(responseUpdatedNeedCheckUp: ResponseEntity<PagedModel<CarResource>>): ResponseEntity<PagedModel<CarResource>> {
        responseUpdatedNeedCheckUp.body?.content?.forEach {
            if(carCheckUpRepository.existsByCar(Car(it.id, it.addedDate, manufacturerModelRepository.findById(it.modelId), it.productionYear, it.vin)))
                it.needCheckUp = checkUpNeccessary(it.id).body.needCheckUp
            else
                it.needCheckUp = true

        }
        return responseUpdatedNeedCheckUp
    }





}