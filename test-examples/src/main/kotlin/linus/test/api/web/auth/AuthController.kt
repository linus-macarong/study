package linus.test.api.web.auth

import linus.test.api.web.auth.request.LoginRequest
import linus.test.api.web.auth.request.SignUpRequest
import linus.test.api.web.auth.response.LoginResponse
import linus.test.api.web.auth.response.SignUpResponse
import linus.test.config.auth.LoginUser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authFacade: AuthFacade,
) {

    @PostMapping("/sign-up")
    fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<SignUpResponse> {
        val response = this.authFacade.signUp(request)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response)
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        val response = this.authFacade.login(request)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response)
    }

    @DeleteMapping("/withdraw")
    fun withdraw(loginUser: LoginUser): ResponseEntity<Unit> {
        this.authFacade.withdraw(userId = loginUser.userId)
        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }
}
