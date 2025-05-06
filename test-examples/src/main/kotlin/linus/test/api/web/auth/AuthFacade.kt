package linus.test.api.web.auth

import linus.test.api.web.auth.request.LoginRequest
import linus.test.api.web.auth.request.SignUpRequest
import linus.test.api.web.auth.response.LoginResponse
import linus.test.api.web.auth.response.SignUpResponse
import linus.test.core.auth.application.AddAuthenticationService
import linus.test.core.auth.application.AuthenticateService
import linus.test.core.auth.application.DeactivateAuthenticationService
import linus.test.core.auth.application.GenerateAccessTokenService
import linus.test.core.user.application.CreateUserService
import linus.test.core.user.application.WithdrawUserService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component
class AuthFacade(
    private val createUserService: CreateUserService,
    private val addAuthenticationService: AddAuthenticationService,
    private val authenticateService: AuthenticateService,
    private val generateAccessTokenService: GenerateAccessTokenService,
    private val withdrawUserService: WithdrawUserService,
    private val deactivateAuthenticationService: DeactivateAuthenticationService,
) {

    @Transactional
    fun signUp(request: SignUpRequest): SignUpResponse {
        val user = this.createUserService.create(request.toCreateUserCommand())
        this.addAuthenticationService.add(request.toAddAuthenticationCommand(userId = user.getId()))
        return SignUpResponse.of(user)
    }

    @Transactional
    fun login(request: LoginRequest): LoginResponse {
        val now = LocalDateTime.now()
        val authentication = this.authenticateService.authenticate(request.toAuthenticateCommand(now))
        val accessToken = this.generateAccessTokenService.generate(
            GenerateAccessTokenService.Command(userId = authentication.userId, now = now)
        )
        return LoginResponse.of(accessToken)
    }

    @Transactional
    fun withdraw(userId: Long) {
        val now = LocalDateTime.now()
        this.withdrawUserService.withdraw(
            WithdrawUserService.Command(id = userId, now = now)
        )
        this.deactivateAuthenticationService.deactivate(
            DeactivateAuthenticationService.Command(userId = userId, now = now)
        )
    }
}
