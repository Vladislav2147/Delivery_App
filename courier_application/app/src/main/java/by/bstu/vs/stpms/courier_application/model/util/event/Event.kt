package by.bstu.vs.stpms.courier_application.model.util.event

data class Event<out T>(val status: Status, val data: T?, val t: Throwable?) {

    companion object {
        fun <T> loading(): Event<T> {
            return Event(Status.LOADING, null, null)
        }

        fun <T> success(data: T?): Event<T> {
            return Event(Status.SUCCESS, data, null)
        }

        fun <T> error(t: Throwable?): Event<T> {
            return Event(Status.ERROR, null, t)
        }
    }
}
