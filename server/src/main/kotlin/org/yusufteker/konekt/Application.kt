package org.yusufteker.konekt

import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}
fun Application.module() {
    configureRouting()
    configureSerialization()
}
