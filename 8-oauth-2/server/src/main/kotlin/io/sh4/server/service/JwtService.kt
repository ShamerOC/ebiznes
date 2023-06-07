package io.sh4.server.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.sh4.server.model.user.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import java.util.function.Function


@Service
class JwtService {
    fun extractUsername(token: String?): String =
        extractClaim(token) { obj: Claims -> obj.subject }

    fun <T> extractClaim(token: String?, claimsResolver: Function<Claims, T>): T =
        claimsResolver.apply(extractAllClaims(token))


    fun generateToken(user: User): String =
        generateToken(user, mapOf(ROLE to user.role))

    fun generateToken(
        userDetails: UserDetails,
        extraClaims: Map<String, Any>
    ): String =
        Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 24))
            .signWith(signInKey, SignatureAlgorithm.HS256)
            .compact()

    fun isTokenValid(token: String): Boolean =
        !isTokenExpired(token)

    private fun isTokenExpired(token: String): Boolean =
        extractExpiration(token).before(Date())

    private fun extractExpiration(token: String): Date =
        extractClaim(token) { obj: Claims -> obj.expiration }

    private fun extractAllClaims(token: String?): Claims =
        Jwts
            .parserBuilder()
            .setSigningKey(signInKey)
            .build()
            .parseClaimsJws(token)
            .body

    private val signInKey: Key
        get() = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY))


    companion object {
        const val ROLE = "role"
        private const val SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970"
    }
}
