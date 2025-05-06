package linus.test.core.user.application

import linus.test.core.user.domain.User
import linus.test.core.user.domain.UserRepository
import linus.test.core.user.domain.getActiveById
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
class GetUserService(
    private val userRepository: UserRepository,
) {
    fun getActiveById(id: Long): User {
        return this.userRepository.getActiveById(id=id)
    }
}
