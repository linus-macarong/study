package linus.test.api.web.user

import linus.test.api.web.user.response.UserResponse
import linus.test.config.auth.LoginUser
import linus.test.core.user.application.GetUserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val getUserService: GetUserService,
) {
    @GetMapping("/my")
    fun get(loginUser: LoginUser): ResponseEntity<UserResponse> {
        val user = this.getUserService.getActiveById(loginUser.userId)
        return ResponseEntity
            .ok(UserResponse.of(user))
    }
}
