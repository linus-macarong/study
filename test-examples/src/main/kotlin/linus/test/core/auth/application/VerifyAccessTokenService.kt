package linus.test.core.auth.application

import linus.test.core.auth.domain.access_token.AccessToken
import linus.test.core.auth.domain.access_token.AccessTokenClaims
import linus.test.core.auth.domain.access_token.AccessTokenClaimsEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class VerifyAccessTokenService(
    private val accessTokenClaimsEncoder: AccessTokenClaimsEncoder,
) {
    fun verify(command: Command): AccessTokenClaims {
        val accessTokenClaims = this.accessTokenClaimsEncoder.decode(accessToken = command.accessToken)

        if (accessTokenClaims.isExpired(now = command.now)) {
            throw IllegalStateException("Access token is expired")
        }

        return accessTokenClaims
    }

    data class Command(
        val accessToken: AccessToken,
        val now: LocalDateTime,
    )
}
