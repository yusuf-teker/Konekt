package org.yusufteker.konekt

import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.application.*
import org.yusufteker.konekt.data.DatabaseFactory
import org.yusufteker.konekt.plugins.JwtConfig
import org.yusufteker.konekt.plugins.configureAuthentication
import org.yusufteker.konekt.plugins.configureDatabase
import org.yusufteker.konekt.plugins.configureMonitoring
import org.yusufteker.konekt.plugins.configureResources
import org.yusufteker.konekt.plugins.configureRouting
import org.yusufteker.konekt.plugins.configureSerialization

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}
fun Application.module() {


    val jwtConfig = createJwtConfig()

    configureSerialization()
    configureResources()
    configureDatabase()
    DatabaseFactory.init()
    configureMonitoring()
    configureAuthentication(jwtConfig)
    configureRouting(jwtConfig)        // sonra route'lar tanımlanır
}

fun createJwtConfig(): JwtConfig{
    val env = dotenv {
        directory = "./server"
        filename = ".env"
        ignoreIfMalformed = true
        ignoreIfMissing = false
    }

    // 2️⃣ JwtConfig oluştur
    val jwtConfig = JwtConfig(
        secret = env["JWT_SECRET"] ?: error("JWT_SECRET bulunamadı"),
        issuer = env["JWT_ISSUER"] ?: "konekt-server",
        audience = env["JWT_AUDIENCE"] ?: "konekt-client",
        realm = env["JWT_REALM"] ?: "KonektRealm"
    )
    return jwtConfig
}
