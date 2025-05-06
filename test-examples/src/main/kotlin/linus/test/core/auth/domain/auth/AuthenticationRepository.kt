package linus.test.core.auth.domain.auth

import org.springframework.data.jpa.repository.JpaRepository

interface AuthenticationRepository : JpaRepository<Authentication, Long> {
    fun existsByEmailAndDeactivated(email: String, deactivated: Boolean): Boolean

    fun findByEmailAndDeactivated(email: String, deactivated: Boolean): Authentication?

    fun findByUserIdAndDeactivated(userId: Long, deactivated: Boolean): Authentication?
}

fun AuthenticationRepository.existsActiveByEmail(email: String): Boolean {
    return this.existsByEmailAndDeactivated(email = email, deactivated = false)
}

fun AuthenticationRepository.getActiveByEmail(email: String): Authentication {
    val authentication = this.findByEmailAndDeactivated(email = email, deactivated = false)
        ?: throw IllegalArgumentException("Authentication not found (email=$email)")
    require(authentication.isActive())
    return authentication
}

fun AuthenticationRepository.getActiveByUserId(userId: Long): Authentication {
    val authentication = this.findByUserIdAndDeactivated(userId = userId, deactivated = false)
        ?: throw IllegalArgumentException("Authentication not found (userId=$userId)")
    require(authentication.isActive())
    return authentication
}
