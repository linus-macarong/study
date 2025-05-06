package linus.test.acceptance.auth

import linus.test.acceptance.AcceptanceTest
import linus.test.acceptance.auth.step.`가입한 이메일이 아니라 로그인이 실패한다`
import linus.test.acceptance.auth.step.`로그인 요청을 한다`
import linus.test.acceptance.auth.step.`로그인이 성공한다`
import linus.test.acceptance.auth.step.`인증 토큰을 가져온다`
import linus.test.acceptance.auth.step.`인증 토큰을 저장한다`
import linus.test.acceptance.auth.step.`회원가입 요청을 한다`
import linus.test.acceptance.auth.step.`회원가입이 성공한다`
import linus.test.acceptance.auth.step.`회원탈퇴 요청을 한다`
import linus.test.acceptance.auth.step.`회원탈퇴가 성공한다`

class AuthLifecycleAcceptanceTest : AcceptanceTest({
    test("회원가입, 로그인, 회원탈퇴 인수테스트") {
        // 1. 회원가입 전
        var `로그인 응답` = `로그인 요청을 한다`()
        `로그인 응답`.`가입한 이메일이 아니라 로그인이 실패한다`()

        val `회원가입 응답` = `회원가입 요청을 한다`()
        `회원가입 응답`.`회원가입이 성공한다`()

        // 2. 회원가입 후
        `로그인 응답` = `로그인 요청을 한다`()
        `로그인 응답`.`로그인이 성공한다`()
        val `인증 토큰` = `로그인 응답`.`인증 토큰을 가져온다`()
        `인증 토큰을 저장한다`(`인증 토큰`)

        val `회원탈퇴 응답` = `회원탈퇴 요청을 한다`()
        `회원탈퇴 응답`.`회원탈퇴가 성공한다`()

        // 3. 회원탈퇴 후
        `로그인 응답` = `로그인 요청을 한다`()
        `로그인 응답`.`가입한 이메일이 아니라 로그인이 실패한다`()
    }
})
