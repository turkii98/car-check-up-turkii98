package com.infinum.course.car

class CarNotFoundException(id: Long) : RuntimeException("Car check-up ID $id not found")
