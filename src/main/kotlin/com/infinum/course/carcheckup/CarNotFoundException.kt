package com.infinum.course.carcheckup

class CarNotFoundException(id: Long) : RuntimeException("Car check-up ID $id not found")
