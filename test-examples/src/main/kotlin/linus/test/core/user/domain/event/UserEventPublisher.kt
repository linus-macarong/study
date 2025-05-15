package linus.test.core.user.domain.event

import linus.test.core.user.domain.User
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class UserEventPublisher(
    private val eventPublisher: ApplicationEventPublisher,
) {
    fun publishUserCreatedEvent(user: User) {
        this.eventPublisher.publishEvent(
            UserCreatedEvent(
                userId = user.getId(),
                nickname = user.nickname,
            )
        )
    }
}
