package linus.test.api.web.user.response

import linus.test.core.user.domain.User

data class UserResponse(
    val id: Long,
    val nickname: String,
) {
    companion object {
        fun of(user: User): UserResponse {
            return UserResponse(
                id = user.getId(),
                nickname = user.nickname,
            )
        }
    }
}
