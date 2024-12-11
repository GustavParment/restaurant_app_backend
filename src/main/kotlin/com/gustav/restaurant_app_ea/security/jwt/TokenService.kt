package com.gustav.restaurant_app_ea.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Service
class TokenService(
    @Value("\${jwt.secret}") private val secret: String = "",
    private val userDetailsService: UserDetailsService,

) {
    private val signingKey: SecretKeySpec
        get() {
            val keyBytes: ByteArray = Base64.getDecoder().decode(secret)
            return SecretKeySpec(keyBytes, 0, keyBytes.size, "HmacSHA256")
        }

    fun generateToken(subject: String,
                      expiration: Date,
                      additionalClaims: Map<String, Any> = emptyMap()
    ): String
    {
//        val claims = additionalClaims.toMutableMap()
//        claims["roles"] = userDetailsService
//            .loadUserByUsername(subject)
//            .authorities.map {
//                it.authority
//            }
        return Jwts.builder()
            .setClaims(additionalClaims)
            .setSubject(subject)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(expiration)
            .signWith(signingKey)
            .compact()
    }

    fun extractUsername(token: String): String {
        return extractAllClaims(token).subject
    }

    fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun isTokenValid(
        token: String,
        userDetails: UserDetails,
        ): Boolean
    {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        val expirationDate: Date = extractAllClaims(token).expiration
        return expirationDate.before(Date())
    }
}
