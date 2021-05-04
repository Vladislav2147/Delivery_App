package by.bstu.vs.stpms.courier_application.model.database.entity.converters

import androidx.room.TypeConverter
import by.bstu.vs.stpms.courier_application.model.database.entity.enums.OrderState
import java.sql.Timestamp

class OrderStateConverter {

    @TypeConverter
    fun fromEnum(orderState: OrderState?): String? {
        return orderState?.name
    }

    @TypeConverter
    fun toEnum(orderStateString: String): OrderState? {
        return orderStateString?.let { OrderState.valueOf(it) }
    }
}
