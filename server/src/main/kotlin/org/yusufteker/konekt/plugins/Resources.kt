package org.yusufteker.konekt.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.resources.*

fun Application.configureResources(){
    install(Resources)
}