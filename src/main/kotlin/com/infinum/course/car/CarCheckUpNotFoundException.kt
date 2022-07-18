package com.infinum.course.car

class CarCheckUpNotFoundException(id: Long) : RuntimeException("Car check-up ID $id not found")