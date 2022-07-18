package com.infinum.course.checkup

/*
@Component
@Qualifier("inMemory")
class InMemoryCarCheckUpRepository : CarCheckUpRepository {
    private val carCheckUpMap = mutableMapOf<Long, CarCheckUp>()
    override fun insert(performedAt: LocalDateTime, car: Car): Long {
        val id = (carCheckUpMap.keys.maxOrNull() ?: 0) + 1
        carCheckUpMap[id] = CarCheckUp(id = id, performedAt = performedAt, car = car)
        return id
    }
    override fun findById(id: Long): CarCheckUp {
        return carCheckUpMap[id] ?: throw CarCheckUpNotFoundException(id)
    }
    override fun deleteById(id: Long): CarCheckUp {
        return carCheckUpMap.remove(id) ?: throw CarCheckUpNotFoundException(id)
    }
    override fun findAll(): Map<Long, CarCheckUp> {
        return this.carCheckUpMap.toMap()
    }
}

*/