package linus.test.acceptance.user

import linus.test.acceptance.AcceptanceTest
import linus.test.acceptance.auth.step.`로그인이 되어있다`
import linus.test.acceptance.auth.step.`회원가입이 되어있다`
import linus.test.acceptance.user.step.`내 정보 조회 요청을 한다`
import linus.test.acceptance.user.step.`내 정보 조회가 성공한다`

class UserAcceptanceTest : AcceptanceTest({
    test("내 정보 조회 인수테스트") {
        `회원가입이 되어있다`(email = "test@test.com", password = "p@ssw0rd", nickname = "tester")
        `로그인이 되어있다`(email = "test@test.com", password = "p@ssw0rd")

        val `내 정보 조회 응답` = `내 정보 조회 요청을 한다`()
        `내 정보 조회 응답`.`내 정보 조회가 성공한다`()
    }
})
