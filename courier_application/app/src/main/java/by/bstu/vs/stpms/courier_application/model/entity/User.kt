package by.bstu.vs.stpms.courier_application.model.entity

class User (
     val firstName: String,
     val secondName: String,
     val email: String,
     val phone: String,
     val password: String,
     val confirmPassword: String?,
     val ordersId: Collection<Long>,
     val roles: Set<Role>
) : AbstractEntity()