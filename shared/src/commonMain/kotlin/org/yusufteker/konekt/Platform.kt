package org.yusufteker.konekt

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform