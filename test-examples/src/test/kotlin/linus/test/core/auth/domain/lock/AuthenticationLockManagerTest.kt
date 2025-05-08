package linus.test.core.auth.domain.lock

import io.kotest.matchers.shouldBe
import linus.test.test.IntegrationTestFunSpec

class AuthenticationLockManagerTest(
    private val sut: AuthenticationLockManager,
) : IntegrationTestFunSpec({
    context("isLocked 메서드") {
        test("integration test example") {
            sut.isLocked("test@test.com") shouldBe false

            sut.lock(email = "test@test.com")
            sut.isLocked("test@test.com") shouldBe true

            sut.unlock("test@test.com")
            sut.isLocked("test@test.com") shouldBe false
        }
    }
})
