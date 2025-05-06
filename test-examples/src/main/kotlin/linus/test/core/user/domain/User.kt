package linus.test.core.user.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table
import linus.test.common.entity.LongIdEntity
import java.time.LocalDateTime

@Entity
@Table(
    name = "users",
    indexes = [
        Index(name = "idx_users_nickname_withdrawn", columnList = "nickname, withdrawn", unique = false),
    ]
)
class User : LongIdEntity {
    @Column(name = "nickname")
    val nickname: String

    @Column(name = "withdrawn")
    var withdrawn: Boolean
        protected set

    @Column(name = "withdrew_at")
    var withdrewAt: LocalDateTime?
        protected set

    constructor(nickname: String) {
        if (nickname.isBlank()) {
            throw IllegalArgumentException("Nickname cannot be blank")
        }

        this.nickname = nickname
        this.withdrawn = false
        this.withdrewAt = null
    }

    fun isActive(): Boolean {
        return this.withdrawn.not()
    }

    fun withdraw(now: LocalDateTime) {
        this.withdrawn = true
        this.withdrewAt = now
    }
}
