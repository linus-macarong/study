package linus.test.acceptance.user.step

import io.kotest.matchers.shouldBe
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import org.springframework.http.HttpStatus

fun `내 정보 조회 요청을 한다`(): Response {
    return When {
        get("/users/my")
    }
}

fun Response.`내 정보 조회가 성공한다`() {
    this.statusCode shouldBe HttpStatus.OK.value()
}
