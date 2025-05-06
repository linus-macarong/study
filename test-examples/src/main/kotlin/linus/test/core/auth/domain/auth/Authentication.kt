package linus.test.core.auth.domain.auth

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table
import linus.test.common.entity.LongIdEntity
import java.time.LocalDateTime

@Entity
@Table(
    name = "authentications",
    indexes = [
        Index(name = "idx_authentications_user_id", columnList = "user_id", unique = false),
        Index(name = "idx_authentications_email_deactivated", columnList = "email, deactivated", unique = false),
    ]
)
class Authentication : LongIdEntity {
    @Column(name = "user_id")
    val userId: Long

    @Column(name = "email")
    val email: String

    @Column(name = "hashed_password")
    val hashedPassword: String

    @Column(name = "latest_authenticated_at")
    var latestAuthenticatedAt: LocalDateTime?
        protected set

    @Column(name = "deactivated")
    var deactivated: Boolean
        protected set

    @Column(name = "deactivated_at")
    var deactivatedAt: LocalDateTime?
        protected set

    constructor(userId: Long, email: String, hashedPassword: String) {
        this.userId = userId
        this.email = email
        this.hashedPassword = hashedPassword
        this.latestAuthenticatedAt = null
        this.deactivated = false
        this.deactivatedAt = null
    }

    fun updateLatestAuthenticatedAt(now: LocalDateTime) {
        this.latestAuthenticatedAt = now
    }

    fun isActive(): Boolean {
        return this.deactivated.not()
    }

    fun deactivate(now: LocalDateTime) {
        this.deactivated = true
        this.deactivatedAt = now
    }
}
