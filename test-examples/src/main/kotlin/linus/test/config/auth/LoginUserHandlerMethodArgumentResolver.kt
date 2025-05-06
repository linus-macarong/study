package linus.test.config.auth

import linus.test.config.auth.AccessTokenHeaderExtractor
import linus.test.core.auth.application.VerifyAccessTokenService
import linus.test.core.auth.domain.access_token.AccessToken
import linus.test.core.auth.domain.access_token.AccessTokenClaims
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import java.time.LocalDateTime

@Component
class LoginUserHandlerMethodArgumentResolver(
    private val accessTokenHeaderExtractor: AccessTokenHeaderExtractor,
    private val verifyAccessTokenService: VerifyAccessTokenService,
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType == LoginUser::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Any {
        val accessToken = this.accessTokenHeaderExtractor.extract(webRequest)
        val now = LocalDateTime.now()

        val accessTokenClaims = this.verifyAccessTokenService.verify(
            VerifyAccessTokenService.Command(accessToken = AccessToken(accessToken), now = now)
        )
        return accessTokenClaims.toLoginUser()
    }
}

private fun AccessTokenClaims.toLoginUser(): LoginUser {
    return LoginUser(userId = this.userId)
}
