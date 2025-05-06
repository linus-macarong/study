package linus.test.acceptance.auth

import linus.test.acceptance.AcceptanceTest
import linus.test.acceptance.auth.step.`기존 회원과 별명이 겹쳐 회원가입이 실패한다`
import linus.test.acceptance.auth.step.`기존 회원과 이메일 계정이 겹쳐 회원가입이 실패한다`
import linus.test.acceptance.auth.step.`회원가입 요청을 한다`
import linus.test.acceptance.auth.step.`회원가입이 되어있다`
import linus.test.acceptance.auth.step.`회원가입이 성공한다`

class SignUpAcceptanceTest : AcceptanceTest({
    test("회원가입 인수테스트") {
        val `회원가입 응답` = `회원가입 요청을 한다`(
            email = "test@test.com",
            password = "p@ssw0rd",
            nickname = "tester",
        )
        `회원가입 응답`.`회원가입이 성공한다`()
    }

    test("회원가입 실패 인수테스트") {
        `회원가입이 되어있다`(email = "test@test.com", password = "p@ssw0rd")

        var `회원가입 응답` = `회원가입 요청을 한다`(
            email = "another_test@test.com",
            password = "p@ssw0rd",
            nickname = "tester",
        )
        `회원가입 응답`.`기존 회원과 별명이 겹쳐 회원가입이 실패한다`()

        `회원가입 응답` = `회원가입 요청을 한다`(
            email = "test@test.com",
            password = "p@ssw0rd",
            nickname = "another_tester",
        )
        `회원가입 응답`.`기존 회원과 이메일 계정이 겹쳐 회원가입이 실패한다`()
    }
})
