plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    application
}

group = "org.yusufteker.konekt"
version = "1.0.0"
application {
    mainClass.set("org.yusufteker.konekt.ApplicationKt")
    
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {

    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlin.testJunit)
    implementation(projects.shared)
    implementation(libs.kotlinx.datetime)
    implementation(libs.logback)

    implementation(libs.bundles.ktor.server)
    implementation(libs.bundles.exposed)
    implementation(libs.postgresql)
}