package linus.test.core.user.domain

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun existsByNicknameAndWithdrawn(nickname: String, withdrawn: Boolean): Boolean

    fun findByIdAndWithdrawn(id: Long, withdrawn: Boolean): User?
}

fun UserRepository.existsActiveByNickname(nickname: String): Boolean {
    return this.existsByNicknameAndWithdrawn(nickname = nickname, withdrawn = false)
}

fun UserRepository.getActiveById(id: Long): User {
    val user = this.findByIdAndWithdrawn(id = id, withdrawn = false)
        ?: throw IllegalArgumentException("User not found (id=$id)")
    require(user.isActive())
    return user
}
