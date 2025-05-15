package linus.test.core.user.application

import linus.test.core.user.domain.User
import linus.test.core.user.domain.UserRepository
import linus.test.core.user.domain.event.UserEventPublisher
import linus.test.core.user.domain.existsActiveByNickname
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateUserService(
    private val userRepository: UserRepository,
    private val userEventPublisher: UserEventPublisher,
) {

    @Transactional
    fun create(command: Command): User {
        if (this.userRepository.existsActiveByNickname(nickname = command.nickname)) {
            throw IllegalStateException("User already exists (nickname=${command.nickname})")
        }

        val user = this.userRepository.save(command.toEntity())
        this.userEventPublisher.publishUserCreatedEvent(user = user)
        return user
    }

    data class Command(
        val nickname: String,
    ) {
        fun toEntity(): User {
            return User(
                nickname = this.nickname,
            )
        }
    }
}
