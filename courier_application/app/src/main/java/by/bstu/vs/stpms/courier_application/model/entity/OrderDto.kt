package by.bstu.vs.stpms.courier_application.model.entity

import java.sql.Timestamp

class OrderDto (
    private val address: String,
    private val info: String? = null,
    private val state: String,
    private val customer: CustomerDto,
    private val courierId: Long? = null,
    private val ordered: Collection<OrderedDto>,
    private val orderedAt: Timestamp? = null,
    private val deliveredAt: Timestamp? = null
) : AbstractDto()