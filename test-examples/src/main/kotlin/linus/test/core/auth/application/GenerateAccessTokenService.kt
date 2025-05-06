package linus.test.core.auth.application

import linus.test.core.auth.domain.access_token.AccessToken
import linus.test.core.auth.domain.access_token.AccessTokenClaimsEncoder
import linus.test.core.auth.domain.access_token.AccessTokenClaimsGenerator
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class GenerateAccessTokenService(
    private val accessTokenClaimsGenerator: AccessTokenClaimsGenerator,
    private val accessTokenClaimsEncoder: AccessTokenClaimsEncoder,
) {
    fun generate(command: Command): AccessToken {
        val accessTokenClaims = this.accessTokenClaimsGenerator.generate(userId = command.userId, now = command.now)
        return this.accessTokenClaimsEncoder.encode(accessTokenClaims)
    }

    data class Command(
        val userId: Long,
        val now: LocalDateTime,
    )
}
