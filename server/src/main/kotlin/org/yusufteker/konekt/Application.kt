package org.yusufteker.konekt

import io.ktor.server.application.*
import org.yusufteker.konekt.data.DatabaseFactory
import org.yusufteker.konekt.plugins.configureAuthentication
import org.yusufteker.konekt.plugins.configureDatabase
import org.yusufteker.konekt.plugins.configureResources
import org.yusufteker.konekt.plugins.configureRouting
import org.yusufteker.konekt.plugins.configureSerialization

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}
fun Application.module() {
    DatabaseFactory.init()

    configureResources()
    configureRouting()

    configureSerialization()
    configureDatabase()
    configureAuthentication() // JWT authentication

    //configureAuthRoutes() // Auth endpoints

}
