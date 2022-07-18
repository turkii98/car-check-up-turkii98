package com.infinum.course.checkup

class CarCheckUpNotFoundException(id: Long) : RuntimeException("Car check-up ID $id not found")