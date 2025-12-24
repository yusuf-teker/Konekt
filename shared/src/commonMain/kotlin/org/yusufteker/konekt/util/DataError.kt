package org.yusufteker.konekt.util


sealed interface DataError : Error {
    enum class Remote : DataError {
        REQUEST_TIMEOUT, TOO_MANY_REQUESTS, NO_INTERNET, SERVER, SERIALIZATION,       FORBIDDEN,UNAUTHORIZED, MISSING_TOKEN,UNKNOWN
    }

    enum class Local : DataError {
        DISK_FULL, UNKNOWN
    }
}
