package org.yusufteker.konekt.data.network.util

@kotlinx.serialization.Serializable
data class ErrorResponse(
    val error: String? = null,
    val message: String? = null,
    val code: String? = null
)