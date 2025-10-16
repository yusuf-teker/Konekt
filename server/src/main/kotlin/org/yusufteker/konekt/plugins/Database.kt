package org.yusufteker.konekt.plugins

import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabase() {
    Database.connect( // PostgreSQL bağlantısı
        url = "jdbc:postgresql://localhost:5432/konekt", // localhost:5432 içindeki konekt db ye bağlanmak istediğimizi belirtiriyorz
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "password"
    )
}
// exposed bundan sonraki table”, “insert”, “select” işlemlerinde bu bağlantıyı kullanacak