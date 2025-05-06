package linus.test.core.auth.domain.access_token

import io.github.oshai.kotlinlogging.KotlinLogging
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDateTime

class AccessTokenClaimsEncoderTest : FunSpec({
    val logger = KotlinLogging.logger { }

    val sut = AccessTokenClaimsEncoder(secret = "test")

    test("token claims를 access token으로 인코딩하고 access token을 token claims로 디코딩한다") {
        val accessToken = sut.encode(
            AccessTokenClaims(
                issuedAt = LocalDateTime.of(2025, 1, 1, 0, 0),
                expiresAt = LocalDateTime.of(2025, 1, 1, 0, 10),
                userId = 1L
            )
        )
        logger.info { "AccessToken:\n${accessToken.value}" }

        val decoded = sut.decode(accessToken)
        decoded.issuedAt shouldBe LocalDateTime.of(2025, 1, 1, 0, 0)
        decoded.expiresAt shouldBe LocalDateTime.of(2025, 1, 1, 0, 10)
        decoded.userId shouldBe 1L
    }
})
