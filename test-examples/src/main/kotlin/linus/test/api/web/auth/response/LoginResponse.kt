package linus.test.api.web.auth.response

import linus.test.core.auth.domain.access_token.AccessToken

data class LoginResponse(
    val accessToken: String,
) {
    companion object {
        fun of(accessToken: AccessToken): LoginResponse {
            return LoginResponse(
                accessToken = accessToken.value,
            )
        }
    }
}
