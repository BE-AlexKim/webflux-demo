package com.example.webflux.application.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*

@Component
class JwtTokenProvider(
    @Value("\${jwt.secret}")
    private val secretKey: String,
    
    @Value("\${jwt.expiration-time}")
    private val expirationTime: Long
) {
    private val key = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))

    fun createToken(email: String): String {
        val claims = Jwts.claims().setSubject(email)
        val now = Date()
        val validity = Date(now.time + expirationTime)

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
            !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }

    fun getEmailFromToken(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
            .subject
    }
} 