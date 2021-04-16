package by.bstu.vs.stpms.courier_application.model.entity

class Customer (
     val firstName: String,
     val secondName: String,
     val email: String,
     val phone: String,
     val ordersId: Collection<Long>,
) : AbstractEntity()