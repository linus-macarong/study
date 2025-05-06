package linus.test.config.auth

import org.springframework.stereotype.Component
import org.springframework.web.context.request.NativeWebRequest

@Component
class AccessTokenHeaderExtractor {
    fun extract(webRequest: NativeWebRequest): String {
        val authorizationHeaderValue = webRequest.getHeader(AUTHORIZATION_HEADER_NAME)
            ?: throw IllegalStateException("Authorization header is missing")

        if (authorizationHeaderValue.startsWith(AUTHORIZATION_TYPE_PREFIX).not()) {
            throw IllegalStateException("Invalid authorization header")
        }

        return authorizationHeaderValue.removePrefix(AUTHORIZATION_TYPE_PREFIX)
    }

    companion object {
        const val AUTHORIZATION_HEADER_NAME = "Authorization"
        const val AUTHORIZATION_TYPE_PREFIX = "Bearer "
    }
}
