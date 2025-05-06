package linus.test.core.auth.domain.access_token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.ZoneOffset

@Component
class AccessTokenClaimsEncoder(
    @Value("\${app.auth.access-token.secret}") private val secret: String,
) {
    private val KST_OFFSET = ZoneOffset.ofHours(9)
    private val ALGORITHM = Algorithm.HMAC256(this.secret)
    private val VERIFIER = JWT.require(ALGORITHM)
        .acceptExpiresAt(Instant.MAX.epochSecond) // do not check if token is expired
        .build()

    fun encode(claims: AccessTokenClaims): AccessToken {
        val accessTokenValue = JWT.create()
            .withExpiresAt(claims.expiresAt.toInstant(KST_OFFSET))
            .withIssuedAt(claims.issuedAt.toInstant(KST_OFFSET))
            .withClaim("uid", claims.userId)
            .sign(ALGORITHM)

        return AccessToken(value = accessTokenValue)
    }

    fun decode(accessToken: AccessToken): AccessTokenClaims {
        val decoded = try {
            VERIFIER.verify(accessToken.value)
        } catch (e: JWTVerificationException) {
            throw IllegalArgumentException("Invalid access token", e)
        }

        return AccessTokenClaims(
            issuedAt = decoded.issuedAtAsInstant.atOffset(KST_OFFSET).toLocalDateTime(),
            expiresAt = decoded.expiresAtAsInstant.atOffset(KST_OFFSET).toLocalDateTime(),
            userId = decoded.getClaim("uid").asLong()
        )
    }
}
