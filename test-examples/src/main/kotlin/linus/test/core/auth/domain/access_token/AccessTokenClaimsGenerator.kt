package linus.test.core.auth.domain.access_token

import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class AccessTokenClaimsGenerator {
    fun generate(userId: Long, now: LocalDateTime): AccessTokenClaims {
        return AccessTokenClaims(
            issuedAt = now,
            expiresAt = now.plusMinutes(5),
            userId = userId,
        )
    }
}
