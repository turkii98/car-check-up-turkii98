package com.infinum.course.carcheckup.controller
import com.infinum.course.carcheckup.CarNotFoundException
import com.infinum.course.carcheckup.dto.CarCheckUpDTO
import com.infinum.course.carcheckup.dto.CarCheckUpResource
import com.infinum.course.carcheckup.dto.CarCheckUpResourceAssembler
import com.infinum.course.carcheckup.entity.CarCheckUp
import com.infinum.course.carcheckup.service.CarCheckUpSystemService
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.PagedModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.UUID

@Controller
@RequestMapping("/carcheckup")
class CarCheckUpController (
private val carCheckUpSystemService: CarCheckUpSystemService,
private val carCheckUpResourceAssembler: CarCheckUpResourceAssembler){

    @GetMapping("/{uuid}")
    @ResponseBody
    fun findByCarId(
        pageable: Pageable, @PathVariable uuid: UUID,
        pagedResourcesAssembler: PagedResourcesAssembler<CarCheckUp>,
        @RequestParam order: String
    ):ResponseEntity<PagedModel<CarCheckUpResource>> {
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(carCheckUpSystemService.findCheckUpByCarSorted(pageable, uuid, order), carCheckUpResourceAssembler))
    }

    @PostMapping()
    @ResponseBody
    fun addCheckUp(
        @RequestBody carCheckUpRequest: CarCheckUpDTO
    ):ResponseEntity<CarCheckUpResource>{
        val carCheckUpResponse = carCheckUpSystemService.addCheckUp(carCheckUpRequest)
        val location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(mapOf("id" to carCheckUpResponse.id))
            .toUri()
        return ResponseEntity.created(location).body(carCheckUpResourceAssembler.toModel(carCheckUpResponse))

    }

    @ExceptionHandler(value = [(CarNotFoundException::class)])
    fun handleException(ex: CarNotFoundException): ResponseEntity<String> {
        println("There is no such car")
        return ResponseEntity("No CheckUps for that car",HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [(EmptyResultDataAccessException::class)])
    fun handleException(ex: EmptyResultDataAccessException): ResponseEntity<String> {
        println("There is no such car")
        return ResponseEntity("Car with such ID does not exist",HttpStatus.BAD_REQUEST)
    }


}