package org.yusufteker.konekt.plugins

import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabase() {

    val dotenv = dotenv {
        directory = "./server"
        filename = ".env"
        ignoreIfMalformed = true
        ignoreIfMissing = false
    }
    val dbUrl = dotenv["DB_URL"] ?: error("DB_URL bulunamadı")
    val dbUser = dotenv["DB_USER"] ?: error("DB_USER bulunamadı")
    val dbPassword = dotenv["DB_PASSWORD"] ?: error("DB_PASSWORD bulunamadı")
    println("DB_URL=$dbUrl, DB_USER=$dbUser")

    Database.connect( // PostgreSQL bağlantısı
        url = dbUrl, // localhost:5432 içindeki konekt db ye bağlanmak istediğimizi belirtiriyorz
        driver = "org.postgresql.Driver",
        user = dbUser,
        password = dbPassword
    )
}
// exposed bundan sonraki table”, “insert”, “select” işlemlerinde bu bağlantıyı kullanacak