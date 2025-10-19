package org.yusufteker.konekt.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.yusufteker.konekt.plugins.JwtConfig
import java.util.Date

fun JwtConfig.generateToken(userId: String, expireMillis: Long = 24*60*60*1000): String {
    val now = System.currentTimeMillis()
    return JWT.create()
        .withAudience(this.audience)
        .withIssuer(this.issuer)
        .withClaim("userId", userId)
        .withExpiresAt(Date(now + expireMillis))
        .sign(Algorithm.HMAC256(this.secret))
}
