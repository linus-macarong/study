package linus.test.acceptance.auth.step

import io.kotest.matchers.shouldBe
import io.restassured.RestAssured
import io.restassured.http.Header
import io.restassured.internal.RequestSpecificationImpl
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import linus.test.api.web.auth.response.LoginResponse
import org.springframework.http.HttpStatus

fun `로그인이 되어있다`(
    email: String = "email@test.com",
    password: String = "p@ssw0rd",
) {
    val `로그인 응답` = `로그인 요청을 한다`(email = email, password = password)
    `로그인 응답`.`로그인이 성공한다`()

    val `인증 토큰` = `로그인 응답`.`인증 토큰을 가져온다`()
    `인증 토큰을 저장한다`(`인증 토큰`)
}

fun `로그인 요청을 한다`(
    email: String = "email@test.com",
    password: String = "p@ssw0rd",
): Response {
    return Given {
        body(
            mapOf(
                "email" to email,
                "password" to password,
            )
        )
    } When {
        post("/auth/login")
    }
}

fun Response.`로그인이 성공한다`() {
    this.statusCode shouldBe HttpStatus.OK.value()
}

fun Response.`가입한 이메일이 아니라 로그인이 실패한다`() {
    this.statusCode shouldBe HttpStatus.INTERNAL_SERVER_ERROR.value()
}

fun Response.`비밀번호가 틀려 로그인이 실패한다`() {
    this.statusCode shouldBe HttpStatus.INTERNAL_SERVER_ERROR.value()
}

fun Response.`로그인 실패로 인해 계정이 잠겨있어 로그인이 실패한다`() {
    this.statusCode shouldBe HttpStatus.INTERNAL_SERVER_ERROR.value()
}

fun Response.`인증 토큰을 가져온다`(): String {
    val response = this.body.`as`(LoginResponse::class.java) as LoginResponse
    return response.accessToken
}

// 단일 actor의 서비스만 사용 가능
fun `인증 토큰을 저장한다`(accessToken: String) {
    (RestAssured.requestSpecification as RequestSpecificationImpl).removeHeader("Authorization")
    RestAssured.requestSpecification.header(Header("Authorization", "Bearer $accessToken"))
}
