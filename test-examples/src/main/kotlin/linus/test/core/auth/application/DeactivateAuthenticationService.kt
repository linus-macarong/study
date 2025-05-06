package linus.test.core.auth.application

import linus.test.core.auth.domain.auth.Authentication
import linus.test.core.auth.domain.auth.AuthenticationRepository
import linus.test.core.auth.domain.auth.getActiveByUserId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class DeactivateAuthenticationService(
    private val authenticationRepository: AuthenticationRepository,
) {

    @Transactional
    fun deactivate(command: Command): Authentication {
        val authentication = this.authenticationRepository.getActiveByUserId(userId = command.userId)
        authentication.deactivate(now = command.now)
        return this.authenticationRepository.save(authentication)
    }

    data class Command(
        val userId: Long,
        val now: LocalDateTime,
    )
}
