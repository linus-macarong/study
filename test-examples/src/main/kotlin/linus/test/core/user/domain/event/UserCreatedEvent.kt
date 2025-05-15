package linus.test.core.user.domain.event

data class UserCreatedEvent(
    val userId: Long,
    val nickname: String,
)
