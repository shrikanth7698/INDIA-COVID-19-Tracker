package me.shrikanthravi.india.covid19app.data.local

data class Resource<out T>(
    val status: Status, val data: T?, val message: String? = null, val pageNo: Int = -1,
    val totalPages: Int = 0, val totalRecords: Int = 0, val error: Exception? = null
) {
    enum class Status {
        SUCCESS,
        OFFLINE_ERROR,
        EMPTY,
        ERROR,
        LOADING,
        AUTHENTICATION_FAILED
    }
    companion object {
        fun <T> loading(data: T? = null, pageNo: Int = -1): Resource<T> {
            return Resource(Status.LOADING, data, null, pageNo)
        }
        fun <T> success(data: T, pageNo: Int = -1, totalPages: Int = 0, totalRecords: Int = 0): Resource<T> {
            return Resource(Status.SUCCESS, data, null, pageNo, totalPages, totalRecords)
        }
        fun <T> empty(data: T? = null, pageNo: Int = -1): Resource<T> {
            return Resource(Status.EMPTY, data, null, pageNo)
        }
        fun <T> error(error: Exception? = null, message: String? = null, data: T? = null, pageNo: Int = -1): Resource<T> {
            return Resource(Status.ERROR, data, message, pageNo, error = error)
        }
        fun <T> offlineError(message: String? = null, data: T? = null, pageNo: Int = -1): Resource<T> {
            return Resource(Status.OFFLINE_ERROR, data, message, pageNo)
        }
        fun <T> authError(message: String? = null, data: T? = null, pageNo: Int = -1): Resource<T> {
            return Resource(Status.AUTHENTICATION_FAILED, data, message, pageNo)
        }
    }
}
