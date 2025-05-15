package linus.test.api.event.user

import linus.test.core.auth.domain.auth.AuthenticationRepository
import linus.test.core.auth.domain.auth.getActiveByUserId
import linus.test.core.notification.EmailNotifier
import linus.test.core.user.domain.event.UserCreatedEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Service
class UserEventHandler(
    private val emailNotifier: EmailNotifier,
    private val authenticationRepository: AuthenticationRepository,
) {

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleUserCreatedEvent(event: UserCreatedEvent) {
        val authentication = this.authenticationRepository.getActiveByUserId(userId = event.userId)

        this.emailNotifier.notify(
            to = authentication.email,
            subject = "Welcome to our service, ${event.nickname}!",
            content = "Hello ${event.nickname}, welcome to our service!"
        )
    }
}
