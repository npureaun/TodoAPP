package com.teamsparta.todoapp.infra.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtUtil(
    @Value("\${auth.jwt.issuer}") private val issuer: String,
    @Value("\${auth.jwt.secret}") private val secret: String,
    @Value("\${auth.jwt.accessTokenExpirationHour}") private val expiration: Long,
) {

    fun validateToken(token:String): Result<Jws<Claims>>{
       return kotlin.runCatching {
           val key=Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
           Jwts.parser().verifyWith(key).build().parseSignedClaims(token)
       }
    }

    fun generateAccessToken(subject:String, role:String): String {
        return generateToken(subject, role, Duration.ofHours(expiration))
    }

    private fun generateToken(subject:String,  role:String, expirationPeriod:Duration): String {
        val claims=Jwts.claims()
            .add(mapOf("role" to role))
            .build()

        val key: SecretKey =Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
        val now=Instant.now()

        return Jwts.builder()
            .subject(subject)
            .issuer(issuer)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expirationPeriod)))
            .claims(claims)
            .signWith(key)
            .compact()
    }
}