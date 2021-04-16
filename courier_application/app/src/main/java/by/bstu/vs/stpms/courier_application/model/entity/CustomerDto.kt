package by.bstu.vs.stpms.courier_application.model.entity

class CustomerDto (
    private val firstName: String,
    private val secondName: String,
    private val email: String,
    private val phone: String,
    private val ordersId: Collection<Long>,
) : AbstractDto()