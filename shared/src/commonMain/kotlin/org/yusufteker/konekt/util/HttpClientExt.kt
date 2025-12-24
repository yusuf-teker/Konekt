package org.yusufteker.konekt.util

import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import org.yusufteker.konekt.data.network.util.ErrorResponse
import org.yusufteker.konekt.util.security.AuthEvent
import org.yusufteker.konekt.util.security.AuthEventBus
import kotlin.coroutines.coroutineContext

//Safa Call Http istegi atma
suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, DataError.Remote> {
    val response: HttpResponse = try {
        execute()
    } catch (e: SocketTimeoutException) {
        return Result.Error(DataError.Remote(DataError.RemoteType.REQUEST_TIMEOUT))
    } catch (e: UnresolvedAddressException) {
        return Result.Error(DataError.Remote(DataError.RemoteType.NO_INTERNET))
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return Result.Error(DataError.Remote(DataError.RemoteType.UNKNOWN))
    }
    return responseToResult(response)
}

// Gelen response u Result sinifina cevir
suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, DataError.Remote> {

    return when (response.status.value) {

        in 200..299 -> {
            try {
                Result.Success(response.body<T>())
            } catch (e: NoTransformationFoundException) {
                Result.Error(
                    DataError.Remote(
                        type = DataError.RemoteType.SERIALIZATION,
                        message = "Serialization error"
                    )
                )
            }
        }

        400 -> {
            val error = try { response.body<ErrorResponse>() } catch (_: Exception) { null }

            Result.Error(
                DataError.Remote(
                    type = DataError.RemoteType.BAD_REQUEST,
                    message = error?.message ?: error?.error ?: "Geçersiz istek"
                )
            )
        }

        401 -> {
            val error = try { response.body<ErrorResponse>() } catch (_: Exception) { null }

            try {
                AuthEventBus.emit(AuthEvent.LoggedOut)
            } catch (_: Exception) {}

            Result.Error(
                DataError.Remote(
                    type = DataError.RemoteType.UNAUTHORIZED,
                    message = error?.message ?: error?.error ?: "Yetkisiz erişim"
                )
            )
        }

        403 -> Result.Error(
            DataError.Remote(
                type = DataError.RemoteType.FORBIDDEN,
                message = "Bu işlemi yapma izniniz yok"
            )
        )

        408 -> Result.Error(DataError.Remote(DataError.RemoteType.REQUEST_TIMEOUT))

        429 -> Result.Error(DataError.Remote(DataError.RemoteType.TOO_MANY_REQUESTS))

        in 500..599 -> Result.Error(
            DataError.Remote(
                type = DataError.RemoteType.SERVER,
                message = "Sunucu hatası"
            )
        )

        else -> Result.Error(
            DataError.Remote(
                type = DataError.RemoteType.UNKNOWN,
                message = "Bilinmeyen hata"
            )
        )
    }
}
