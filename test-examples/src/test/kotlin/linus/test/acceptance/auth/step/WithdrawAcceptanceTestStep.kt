package linus.test.acceptance.auth.step

import io.kotest.matchers.shouldBe
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import org.springframework.http.HttpStatus

fun `회원탈퇴 요청을 한다`(): Response {
    return When {
        delete("/auth/withdraw")
    }
}

fun Response.`회원탈퇴가 성공한다`() {
    this.statusCode shouldBe HttpStatus.NO_CONTENT.value()
}
