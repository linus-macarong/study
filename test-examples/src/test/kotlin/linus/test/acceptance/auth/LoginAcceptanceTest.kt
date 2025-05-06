package linus.test.acceptance.auth

import linus.test.acceptance.AcceptanceTest
import linus.test.acceptance.auth.step.`로그인 실패로 인해 계정이 잠겨있어 로그인이 실패한다`
import linus.test.acceptance.auth.step.`로그인 요청을 한다`
import linus.test.acceptance.auth.step.`로그인이 성공한다`
import linus.test.acceptance.auth.step.`비밀번호가 틀려 로그인이 실패한다`
import linus.test.acceptance.auth.step.`회원가입이 되어있다`

class LoginAcceptanceTest : AcceptanceTest({
    test("로그인 인수테스트") {
        `회원가입이 되어있다`(email = "test@test.com", password = "p@ssw0rd")

        val `로그인 응답` = `로그인 요청을 한다`(email = "test@test.com", password = "p@ssw0rd")
        `로그인 응답`.`로그인이 성공한다`()
    }

    test("로그인 연속 실패 인수테스트") {
        `회원가입이 되어있다`(email = "test@test.com", password = "p@ssw0rd")

        repeat(5) {
            val `로그인 응답` = `로그인 요청을 한다`(email = "test@test.com", password = "wrong_password")
            `로그인 응답`.`비밀번호가 틀려 로그인이 실패한다`()
        }

        val `로그인 응답` = `로그인 요청을 한다`(email = "test@test.com", password = "wrong_password")
        `로그인 응답`.`로그인 실패로 인해 계정이 잠겨있어 로그인이 실패한다`()
    }
})
