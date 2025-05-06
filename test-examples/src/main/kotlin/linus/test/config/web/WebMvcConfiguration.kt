package linus.test.config.web

import linus.test.config.auth.LoginUserHandlerMethodArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfiguration(
    private val loginUserHandlerMethodArgumentResolver: LoginUserHandlerMethodArgumentResolver,
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(loginUserHandlerMethodArgumentResolver)
    }
}
