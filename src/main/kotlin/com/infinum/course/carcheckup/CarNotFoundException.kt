package com.infinum.course.carcheckup

import java.util.UUID

class CarNotFoundException(id: UUID) : RuntimeException("Car check-up ID $id not found")
