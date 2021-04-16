package by.bstu.vs.stpms.courier_application.model.entity

class OrderedDto (
    private val amount: Int,
    private val orderId: Long,
    private val product: ProductDto,
) : AbstractDto()