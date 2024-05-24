package com.teamsparta.todoapp.domain.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.Date
import javax.crypto.SecretKey

object JwtUtil {
    private const val ISSUER="team.sparta.com"
    private const val SECRET="eyJhbGciOiJIUzI1NiJ9." +
            "eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOi" +
            "JJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJ" +
            "blVzZSIsImV4cCI6MTcxNjUzNjAxMiwiaW" +
            "F0IjoxNzE2NTM2MDEyfQ.7F62KfLxg2jY" +
            "xWRPgyQGRzM22KhbE1KEQYeUHHfnsTY"
    private const val ACCESS_TOKEN_EXPIRATION_HOUR:Long=168

    fun validateToken(token:String): Boolean{
       return kotlin.runCatching {
           val key=Keys.hmacShaKeyFor(SECRET.toByteArray(StandardCharsets.UTF_8))
           Jwts.parser().verifyWith(key).build().parseSignedClaims(token)
       }.isSuccess
    }

    fun getUserIdFromToken(token: String): String? {
        val key = Keys.hmacShaKeyFor(SECRET.toByteArray(StandardCharsets.UTF_8))
        val claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token)
        return claims.payload["userId"].toString()
    }

    fun generateAccessToken(subject:String, userId: String): String {
        return generateToken(subject,userId,Duration.ofHours(ACCESS_TOKEN_EXPIRATION_HOUR))
    }

    private fun generateToken(subject:String, userId: String, expirationPeriod:Duration?): String {
        val claims=Jwts.claims()
            .add(mapOf("userId" to userId))
            .build()

        val key: SecretKey =Keys.hmacShaKeyFor(SECRET.toByteArray(StandardCharsets.UTF_8))
        val now=Instant.now()

        return Jwts.builder()
            .subject(subject)
            .issuer(ISSUER)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expirationPeriod)))
            .claims(claims)
            .signWith(key)
            .compact()
    }
}