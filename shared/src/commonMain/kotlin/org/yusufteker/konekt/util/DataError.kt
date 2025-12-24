package org.yusufteker.konekt.util


sealed interface DataError : Error {

    data class Remote(
        val type: RemoteType,
        val message: String? = null
    ) : DataError

    enum class RemoteType {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER,
        SERIALIZATION,
        BAD_REQUEST,
        FORBIDDEN,
        UNAUTHORIZED,
        MISSING_TOKEN,
        UNKNOWN
    }

    enum class Local : DataError {
        DISK_FULL,
        UNKNOWN
    }
}

