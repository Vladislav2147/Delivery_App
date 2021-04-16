package by.bstu.vs.stpms.courier_application.model.entity

class Product (
     val name: String,
     val weight: Double,
     val price: Double,
     val orderedIds: Collection<Long>?
) : AbstractEntity()