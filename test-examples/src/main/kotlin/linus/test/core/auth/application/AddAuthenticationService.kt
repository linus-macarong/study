package linus.test.core.auth.application

import linus.test.core.auth.domain.auth.Authentication
import linus.test.core.auth.domain.auth.AuthenticationRepository
import linus.test.core.auth.domain.auth.PasswordHasher
import linus.test.core.auth.domain.auth.existsActiveByEmail
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AddAuthenticationService(
    private val authenticationRepository: AuthenticationRepository,
    private val passwordHasher: PasswordHasher,
) {

    @Transactional
    fun add(command: Command): Authentication {
        if (this.authenticationRepository.existsActiveByEmail(email = command.email)) {
            throw IllegalStateException("Authentication already exists")
        }

        return this.authenticationRepository.save(
            command.toEntity(passwordHasher = this.passwordHasher)
        )
    }

    data class Command(
        val userId: Long,
        val email: String,
        val password: String,
    ) {
        fun toEntity(passwordHasher: PasswordHasher): Authentication {
            return Authentication(
                userId = this.userId,
                email = this.email,
                hashedPassword = passwordHasher.hash(password = this.password),
            )
        }
    }
}
