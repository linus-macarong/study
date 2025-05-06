package linus.test.api.web.auth.response

import linus.test.core.user.domain.User

data class SignUpResponse(
    val id: Long,
    val nickname: String,
) {
    companion object {
        fun of(user: User): SignUpResponse {
            return SignUpResponse(
                id = user.getId(),
                nickname = user.nickname,
            )
        }
    }
}
