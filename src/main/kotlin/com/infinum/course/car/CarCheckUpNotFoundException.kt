package com.infinum.course.car

import java.util.*

class CarCheckUpNotFoundException(id: UUID) : RuntimeException("Car check-up ID $id not found")