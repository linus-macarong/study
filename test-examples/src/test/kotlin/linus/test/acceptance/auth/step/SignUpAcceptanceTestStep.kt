package linus.test.acceptance.auth.step

import io.kotest.matchers.shouldBe
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import org.springframework.http.HttpStatus

fun `회원가입이 되어있다`(
    email: String = "email@test.com",
    password: String = "p@ssw0rd",
    nickname: String = "tester",
): Response {
    val `회원가입 응답` = `회원가입 요청을 한다`(email = email, password = password, nickname = nickname)
    `회원가입 응답`.`회원가입이 성공한다`()
    return `회원가입 응답`
}

fun `회원가입 요청을 한다`(
    email: String = "email@test.com",
    password: String = "p@ssw0rd",
    nickname: String = "tester",
): Response {
    return Given {
        body(
            mapOf(
                "email" to email,
                "password" to password,
                "nickname" to nickname,
            )
        )
    } When {
        post("/auth/sign-up")
    }
}

fun Response.`회원가입이 성공한다`() {
    this.statusCode shouldBe HttpStatus.CREATED.value()
}

fun Response.`기존 회원과 별명이 겹쳐 회원가입이 실패한다`() {
    this.statusCode shouldBe HttpStatus.INTERNAL_SERVER_ERROR.value()
}

fun Response.`기존 회원과 이메일 계정이 겹쳐 회원가입이 실패한다`() {
    this.statusCode shouldBe HttpStatus.INTERNAL_SERVER_ERROR.value()
}
