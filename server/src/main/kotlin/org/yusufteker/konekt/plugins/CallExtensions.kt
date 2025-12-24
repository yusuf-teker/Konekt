package org.yusufteker.konekt.plugins

import io.ktor.server.application.*
import io.ktor.server.request.*

val ApplicationCall.language: String
    get() = request.acceptLanguageItems()
        .firstOrNull()
        ?.value
        ?: "en"