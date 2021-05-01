package by.bstu.vs.stpms.courier_application.model.database.entity.converters

import androidx.room.TypeConverter
import java.sql.Timestamp

class TimestampConverter {

    @TypeConverter
    fun fromTimestamp(timestamp: Timestamp?): Long? {
        return timestamp?.time
    }

    @TypeConverter
    fun toTimestamp(long: Long?): Timestamp? {
        return long?.let { Timestamp(long) }
    }

}