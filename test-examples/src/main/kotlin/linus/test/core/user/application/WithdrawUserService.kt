package linus.test.core.user.application

import linus.test.core.user.domain.User
import linus.test.core.user.domain.UserRepository
import linus.test.core.user.domain.getActiveById
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class WithdrawUserService(
    private val userRepository: UserRepository,
) {
    @Transactional
    fun withdraw(command: Command): User {
        val user = this.userRepository.getActiveById(id = command.id)
        user.withdraw(now = command.now)
        return this.userRepository.save(user)
    }

    data class Command(
        val id: Long,
        val now: LocalDateTime,
    )
}
