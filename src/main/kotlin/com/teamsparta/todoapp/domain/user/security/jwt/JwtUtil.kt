package com.teamsparta.todoapp.domain.user.security.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import javax.crypto.SecretKey

object JwtUtil {
    private const val ONE_MIN = 100L * 60L * 60L * 1L
    const val EXPIRATION_TIME = ONE_MIN
    private val signingKey :SecretKey=Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun generateToken(userId: String, expirationInMillisecond: Long = EXPIRATION_TIME)
    :String{
        val now=Date()
        val expiration = Date(now.time + expirationInMillisecond)
        val claims= generateClaims(now,expiration)
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userId)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(signingKey,SignatureAlgorithm.HS256)
            .compact()
    }

    fun generateClaims(now:Date, expiration:Date): Map<String,String>{
        val nowLocalDateTime= LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault())
        val expirationLocalDateTime= LocalDateTime.ofInstant(expiration.toInstant(), ZoneId.systemDefault())
        val mapper = ObjectMapper()
        mapper.registerModules(JavaTimeModule())
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false)
        return mapOf(
            "issuedAt" to mapper.writeValueAsString(nowLocalDateTime),
            "expiredAt" to mapper.writeValueAsString(expirationLocalDateTime),
        )
    }

    fun getUserIdFromToken(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }
}