package by.bstu.vs.stpms.courier_application.model.entity

class ProductDto (
    private val name: String,
    private val weight: Double,
    private val price: Double,
    private val orderedIds: Collection<Long>?
) : AbstractDto()