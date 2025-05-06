package linus.test.api.web.auth.request

import linus.test.core.auth.application.AddAuthenticationService
import linus.test.core.user.application.CreateUserService

data class SignUpRequest(
    val email: String,
    val password: String,
    val nickname: String,
) {
    fun toCreateUserCommand(): CreateUserService.Command {
        return CreateUserService.Command(
            nickname = this.nickname,
        )
    }

    fun toAddAuthenticationCommand(userId: Long): AddAuthenticationService.Command {
        return AddAuthenticationService.Command(
            userId = userId,
            email = this.email,
            password = this.password,
        )
    }
}
