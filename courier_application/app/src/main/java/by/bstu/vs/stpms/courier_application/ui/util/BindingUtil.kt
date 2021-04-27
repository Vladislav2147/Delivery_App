package by.bstu.vs.stpms.courier_application.ui.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

object BindingUtil {
    private val formatter = SimpleDateFormat("HH:mm dd.MM.yyyy")
    private val timeOnlyFormatter = SimpleDateFormat("HH:mm ")
    @JvmStatic
    @BindingAdapter("android:text")
    fun setCalendar(textView: TextView, timestamp: Timestamp?) {
        if (timestamp == null) {
            textView.text = "-"
            return;
        }
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp.time
        val now = Calendar.getInstance()
        val delta = calendar[Calendar.DAY_OF_YEAR] - now[Calendar.DAY_OF_YEAR]
        val dateTimeString = StringBuilder()
        if (delta > 7) {
            dateTimeString.append(formatter.format(calendar.time))
        } else {
            dateTimeString.append(timeOnlyFormatter.format(calendar.time))
            when (delta) {
                0 -> dateTimeString.append("Today")
                1 -> dateTimeString.append("Tomorrow")
                else -> dateTimeString.append(
                        arrayOf(-1, "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")[calendar.get(Calendar.DAY_OF_WEEK)]
                )
            }
        }
        textView.text = dateTimeString.toString()
    }

    @JvmStatic
    @BindingAdapter("android:text")
    fun setDouble(textView: TextView, double: Double) {
        textView.text = double.format(2)
    }

    fun Double.format(digits: Int) = "%.${digits}f".format(this)

}