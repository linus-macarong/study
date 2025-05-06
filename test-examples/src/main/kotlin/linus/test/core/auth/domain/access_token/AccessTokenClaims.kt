package linus.test.core.auth.domain.access_token

import java.time.LocalDateTime

/**
 * https://auth0.com/docs/secure/tokens/json-web-tokens/json-web-token-claims
 */
data class AccessTokenClaims(
    // registered claims
    val issuedAt: LocalDateTime, // iat
    val expiresAt: LocalDateTime, // exp
    // custom claims
    val userId: Long,
) {
    init {
        require(0 < userId)
        require(issuedAt < expiresAt)
    }

    fun isExpired(now: LocalDateTime): Boolean {
        return this.expiresAt < now
    }
}
