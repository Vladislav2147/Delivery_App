package by.bstu.vs.stpms.courier_application.model.entity

class UserDto (
    private val firstName: String,
    private val secondName: String,
    private val email: String,
    private val phone: String,
    private val password: String,
    private val confirmPassword: String?,
    private val ordersId: Collection<Long>,
    private val roles: Set<RoleDto>
) : AbstractDto()