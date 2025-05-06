package linus.test.api.web.auth.request

import linus.test.core.auth.application.AuthenticateService
import java.time.LocalDateTime

data class LoginRequest(
    val email: String,
    val password: String,
) {
    fun toAuthenticateCommand(now: LocalDateTime): AuthenticateService.Command {
        return AuthenticateService.Command(
            email = email,
            password = password,
            now = now,
        )
    }
}
