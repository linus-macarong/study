package linus.test.core.auth.domain.auth

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordHasher {
    private val passwordEncoder = BCryptPasswordEncoder()

    fun hash(password: String): String {
        return this.passwordEncoder.encode(password)
    }

    fun matches(password: String, hashedPassword: String): Boolean {
        return this.passwordEncoder.matches(password, hashedPassword)
    }
}
