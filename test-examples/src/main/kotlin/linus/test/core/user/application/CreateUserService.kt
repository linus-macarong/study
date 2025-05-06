package linus.test.core.user.application

import linus.test.core.user.domain.User
import linus.test.core.user.domain.UserRepository
import linus.test.core.user.domain.existsActiveByNickname
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateUserService(
    private val userRepository: UserRepository,
) {

    @Transactional
    fun create(command: Command): User {
        if (this.userRepository.existsActiveByNickname(nickname = command.nickname)) {
            throw IllegalStateException("User already exists (nickname=${command.nickname})")
        }

        return this.userRepository.save(command.toEntity())
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
