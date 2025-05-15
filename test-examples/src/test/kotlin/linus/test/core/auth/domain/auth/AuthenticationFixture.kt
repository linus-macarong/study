package linus.test.core.auth.domain.auth

import java.time.LocalDateTime

object AuthenticationFixture {
    fun active(
        userId: Long = 1L,
        email: String = "test@test.om",
        hashedPassword: String = PasswordHasher().hash("p@ssw0rd"),
    ): Authentication {
        val authentication = Authentication(
            userId = userId,
            email = email,
            hashedPassword = hashedPassword,
        )
        require(authentication.isActive())
        return authentication
    }

    fun deactivated(
        userId: Long = 1L,
        email: String = "test@test.om",
        hashedPassword: String = PasswordHasher().hash("p@ssw0rd"),
        deactivatedAt: LocalDateTime = LocalDateTime.now()
    ): Authentication {
        val authentication = active(userId = userId, email = email, hashedPassword = hashedPassword)
        authentication.deactivate(deactivatedAt)
        require(authentication.deactivated)
        return authentication
    }
}
