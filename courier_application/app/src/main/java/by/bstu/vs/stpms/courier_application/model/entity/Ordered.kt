package by.bstu.vs.stpms.courier_application.model.entity

class Ordered (
     val amount: Int,
     val orderId: Long,
     val product: Product,
) : AbstractEntity()