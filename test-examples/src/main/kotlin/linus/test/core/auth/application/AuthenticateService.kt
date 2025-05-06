package linus.test.core.auth.application

import jakarta.transaction.Transactional
import linus.test.core.auth.domain.auth.Authentication
import linus.test.core.auth.domain.auth.AuthenticationRepository
import linus.test.core.auth.domain.auth.PasswordHasher
import linus.test.core.auth.domain.auth.getActiveByEmail
import linus.test.core.auth.domain.lock.AuthenticationLockManager
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AuthenticateService(
    private val authenticationRepository: AuthenticationRepository,
    private val passwordHasher: PasswordHasher,
    private val authenticationLockManager: AuthenticationLockManager,
) {

    @Transactional
    fun authenticate(command: Command): Authentication {
        if (this.authenticationLockManager.isLocked(email = command.email)) {
            throw IllegalStateException("Authentication is locked")
        }

        val authentication = this.authenticationRepository.getActiveByEmail(email = command.email)
        val passwordMatched =
            this.passwordHasher.matches(password = command.password, hashedPassword = authentication.hashedPassword)

        if (passwordMatched.not()) {
            this.onPasswordNotMatched(command = command)
        }

        return this.onAuthenticationSuccess(authentication = authentication, command = command)
    }

    private fun onPasswordNotMatched(command: Command): Nothing {
        val trialCount = this.authenticationLockManager.increaseTrialCount(email = command.email)

        if (this.authenticationLockManager.shouldLock(trialCount)) {
            this.authenticationLockManager.lock(email = command.email)
        }

        throw IllegalArgumentException("Authentication failed")
    }

    private fun onAuthenticationSuccess(authentication: Authentication, command: Command): Authentication {
        this.authenticationLockManager.resetTrialCount(email = authentication.email)
        authentication.updateLatestAuthenticatedAt(now = command.now)
        this.authenticationRepository.save(authentication)
        return authentication
    }

    data class Command(
        val email: String,
        val password: String,
        val now: LocalDateTime,
    )
}
