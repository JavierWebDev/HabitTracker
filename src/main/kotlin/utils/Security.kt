package com.example.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.routing.SecurityConfig
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {
    val jwtAudience = System.getenv(SecurityConfig.JWT_AUDIENCE)
        ?: error("Missing environment variable: ${SecurityConfig.JWT_AUDIENCE}")
    val jwtRealm = System.getenv(SecurityConfig.JWT_REALM)
        ?: error("Missing environment variable: ${SecurityConfig.JWT_REALM}")
    val jwtSecret = System.getenv(SecurityConfig.JWT_SECRET)
        ?: error("Missing environment variable: ${SecurityConfig.JWT_SECRET}")
    val jwtIssuer = System.getenv(SecurityConfig.JWT_ISSUER)
        ?: error("Missing environment variable: ${SecurityConfig.JWT_ISSUER}")
    authentication {
        jwt {
            realm = jwtRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtIssuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
            }
        }
    }
}
