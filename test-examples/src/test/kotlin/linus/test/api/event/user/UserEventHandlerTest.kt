package linus.test.api.event.user

import io.kotest.core.spec.style.FunSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import linus.test.core.auth.domain.auth.AuthenticationFixture
import linus.test.core.auth.domain.auth.AuthenticationRepository
import linus.test.core.notification.EmailNotifier
import linus.test.core.user.domain.event.UserCreatedEvent
import org.awaitility.Awaitility.await

class UserEventHandlerTest : FunSpec({
    val mockEmailNotifier = mockk<EmailNotifier>(relaxed = true)
    val mockAuthenticationRepository = mockk<AuthenticationRepository>(relaxed = true)
    val sut = UserEventHandler(
        emailNotifier = mockEmailNotifier,
        authenticationRepository = mockAuthenticationRepository,
    )

    context("handleUserCreatedEvent 메서드") {
        test("회원가입 환영 메일을 보낸다") {
            // given
            val authentication = AuthenticationFixture.active(userId = 1L, email = "test@test.com")
            every {
                mockAuthenticationRepository.findByUserIdAndDeactivated(userId = any(), deactivated = any())
            } returns authentication

            // when
            Thread.startVirtualThread {
                Thread.sleep(1000)
                sut.handleUserCreatedEvent(UserCreatedEvent(userId = 1L, nickname = "tester"))
            }

            // then
            // 아래 await가 없으면 테스트가 1초가 넘지 않음
            await().untilAsserted {
                verify {
                    mockEmailNotifier.notify(
                        to = "test@test.com",
                        subject = "Welcome to our service, tester!",
                        content = "Hello tester, welcome to our service!",
                    )
                }
            }
        }
    }
})
