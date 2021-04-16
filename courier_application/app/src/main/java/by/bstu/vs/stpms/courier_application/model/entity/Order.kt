package by.bstu.vs.stpms.courier_application.model.entity

import java.sql.Timestamp

class Order (
     val address: String,
     val info: String? = null,
     val state: String,
     val customer: Customer,
     val courierId: Long? = null,
     val ordered: Collection<Ordered>,
     val orderedAt: Timestamp? = null,
     val deliveredAt: Timestamp? = null
) : AbstractEntity()